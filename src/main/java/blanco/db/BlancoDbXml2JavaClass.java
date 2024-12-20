/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgTransformer;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
import blanco.db.common.BlancoDbXml2SqlInfo;
import blanco.db.common.IBlancoDbProgress;
import blanco.db.common.stringgroup.BlancoDbDriverNameStringGroup;
import blanco.db.common.stringgroup.BlancoDbSqlInfoTypeStringGroup;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbDynamicConditionStructure;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.expander.exception.DeadlockExceptionClass;
import blanco.db.expander.exception.IntegrityConstraintExceptionClass;
import blanco.db.expander.exception.LockTimeoutExceptionClass;
import blanco.db.expander.exception.NoRowFoundExceptionClass;
import blanco.db.expander.exception.NoRowModifiedExceptionClass;
import blanco.db.expander.exception.NotSingleRowExceptionClass;
import blanco.db.expander.exception.TimeoutExceptionClass;
import blanco.db.expander.exception.TooManyRowsFoundExceptionClass;
import blanco.db.expander.exception.TooManyRowsModifiedExceptionClass;
import blanco.db.expander.query.caller.QueryCallerClass;
import blanco.db.expander.query.input.FunctionLiteralInputClass;
import blanco.db.expander.query.invoker.QueryInvokerClass;
import blanco.db.expander.query.iterator.QueryIteratorClass;
import blanco.db.util.BlancoDbDynamicClauseClassJava;
import blanco.db.util.BlancoDbDynamicLiteralClassJava;
import blanco.db.util.BlancoDbDynamicOrderByClassJava;
import blanco.db.util.BlancoDbDynamicParameterClassJava;
import blanco.db.util.BlancoDbMappingUtilJava;
import blanco.db.util.BlancoDbUtilClassJava;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;
import blanco.valueobject.BlancoValueObjectXml2JavaClass;
import blanco.valueobject.valueobject.BlancoValueObjectClassStructure;
import blanco.valueobject.valueobject.BlancoValueObjectFieldStructure;

/**
 * Generates source code from intermediate XML files.
 */
public abstract class BlancoDbXml2JavaClass implements IBlancoDbProgress {
    private BlancoDbSetting fDbSetting = null;

    /**
     * Generates source code from XML files.
     *
     * @param argDbSetting
     * @param blancoSqlDirectory
     * @throws SQLException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws ClassNotFoundException
     * @throws TransformerException
     */
    public void process(final BlancoDbSetting argDbSetting,
            final File blancoSqlDirectory) throws SQLException, SAXException,
            IOException, ParserConfigurationException, ClassNotFoundException,
            TransformerException {
        System.out.println(BlancoDbConstants.PRODUCT_NAME + " ("
                + BlancoDbConstants.VERSION + ") Source code generation: start..");

        fDbSetting = argDbSetting;

        if (BlancoStringUtil.null2Blank(fDbSetting.getRuntimePackage()).trim()
                .length() == 0) {
            fDbSetting.setRuntimePackage(null);
        }

        Connection conn = null;
        try {
            conn = BlancoDbUtil.connect(fDbSetting);
            BlancoDbUtil.getDatabaseVersionInfo(conn, fDbSetting);

            if (blancoSqlDirectory != null) {
                // Processes the directory containing the SQL definition file only if specified.

                // Creates a directory to store the ValueObject information.
                new File(blancoSqlDirectory.getAbsolutePath() + "/valueobject")
                        .mkdirs();

                final File[] fileSettingXml = blancoSqlDirectory.listFiles();
                for (int index = 0; index < fileSettingXml.length; index++) {
                    if (fileSettingXml[index].getName().endsWith(".xml") == false) {
                        // Only files with xml extension will be processed.
                        continue;
                    }
                    if (progress(index + 1, fileSettingXml.length,
                            fileSettingXml[index].getName()) == false) {
                        break;
                    }

                    try {
                        // Generation is done on a file-by-file basis.
                        processEveryFile(conn, fileSettingXml[index], new File(
                                blancoSqlDirectory.getAbsolutePath()
                                        + "/valueobject"));
                    } catch (IllegalArgumentException ex) {
                        if (argDbSetting.getFailonerror()) {
                            // Aborts processing because an exception occurred in the processing of the SQL definition document.
                            throw ex;
                        } else {
                            // Displays the error message on the stderr and continues processing.
                            System.err.println("SQL definition document exception: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }
            }

        } finally {
            BlancoDbUtil.close(conn);
            conn = null;
            System.out.println("Source code generation: finish.");
        }
    }

    /**
     * Processes individual XML files.
     *
     * @param conn
     * @param fileSqlForm
     * @param outputDirectory
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     * @throws SQLException
     * @throws ParserConfigurationException
     */
    private void processEveryFile(final Connection conn,
            final File fileSqlForm, final File outputDirectory)
            throws IOException, SAXException, TransformerException,
            SQLException, ParserConfigurationException {

        System.out.println("Processes the file [" + fileSqlForm.getAbsolutePath() + "].");

        final BlancoDbXml2SqlInfo collector = new BlancoDbXml2SqlInfo();
        final List<BlancoDbSqlInfoStructure> definition = collector.process(
                conn, fDbSetting, fileSqlForm);

        final String packageNameException = BlancoDbUtil
                .getRuntimePackage(fDbSetting) + ".exception";

        /*
         * Determines the newline code.
         */
        String LF = "\n";
        String CR = "\r";
        String CRLF = CR + LF;
        String lineSeparatorMark = fDbSetting.getLineSeparator();
        String lineSeparator = "";
        if ("LF".equals(lineSeparatorMark)) {
            lineSeparator = LF;
        } else if ("CR".equals(lineSeparatorMark)) {
            lineSeparator = CR;
        } else if ("CRLF".equals(lineSeparatorMark)) {
            lineSeparator = CRLF;
        }
        if (lineSeparator.length() != 0) {
            System.setProperty("line.separator", lineSeparator);
        }

        /*
         * Processes targetdir and targetStyle.
         * Sets the storage location for the generated code.
         * targetstyle = blanco:
         *  Creates a main directory under targetdir.
         * targetstyle = maven:
         *  Creates a main/java directory under targetdir.
         * targetstyle = free:
         *  Creates a directory using targetdir as is.
         *  However, the default string (blanco) is used if targetdir is empty.
         * by tueda, 2019/08/30
         */
        String strTarget = fDbSetting.getTargetDir();
        String style = fDbSetting.getTargetStyle();
        // Always true when passing through here.
        boolean isTargetStyleAdvanced = true;
        if (style != null && BlancoDbConstants.TARGET_STYLE_MAVEN.equals(style)) {
            strTarget = strTarget + "/" + BlancoDbConstants.TARGET_DIR_SUFFIX_MAVEN;
        } else if (style == null ||
                !BlancoDbConstants.TARGET_STYLE_FREE.equals(style)) {
            strTarget = strTarget + "/" + BlancoDbConstants.TARGET_DIR_SUFFIX_BLANCO;
        }
        /* Uses targetdir as is if style is free. */
        System.out.println("/* tueda */ TARGETDIR = " + strTarget);

        final File fileBlancoMain = new File(strTarget);

        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        final BlancoCgTransformer transformer = BlancoCgTransformerFactory
                .getJavaSourceTransformer();

        // about exception
        transformer.transform(adjust(new DeadlockExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new IntegrityConstraintExceptionClass(
                cgFactory, packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new NoRowFoundExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new NoRowModifiedExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new NotSingleRowExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new TimeoutExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new TooManyRowsFoundExceptionClass(
                cgFactory, packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new TooManyRowsModifiedExceptionClass(
                cgFactory, packageNameException).expand()), fileBlancoMain);

        switch (fDbSetting.getDriverName()) {
        case BlancoDbDriverNameStringGroup.SQLSERVER_2000:
        case BlancoDbDriverNameStringGroup.SQLSERVER_2005:
            // Generates the LockTimeoutException class only for SQL Server 2000/2005.
            transformer.transform(adjust(new LockTimeoutExceptionClass(
                    cgFactory, packageNameException).expand()), fileBlancoMain);
            break;
        default:
            break;
        }

        // about util
        transformer.transform(adjust(new  BlancoDbUtilClassJava(cgFactory,
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util",
                fDbSetting).expand()), fileBlancoMain);

        // about dynamic SQL
        transformer.transform(adjust(new BlancoDbDynamicClauseClassJava(cgFactory,
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util",
                fDbSetting).expand()), fileBlancoMain);
        transformer.transform(adjust(new BlancoDbDynamicParameterClassJava(cgFactory,
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util",
                fDbSetting).expand()), fileBlancoMain);
        transformer.transform(adjust(new BlancoDbDynamicOrderByClassJava(cgFactory,
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util",
                fDbSetting).expand()), fileBlancoMain);
        transformer.transform(adjust(new BlancoDbDynamicLiteralClassJava(cgFactory,
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util",
                fDbSetting).expand()), fileBlancoMain);

        // iterator, invoker, caller, functionLiteralInput
        for (int index = 0; index < definition.size(); index++) {
            final BlancoDbSqlInfoStructure sqlInfo = definition.get(index);
            // Dynamic condition function input class
            for (BlancoDbDynamicConditionStructure conditionStructure : sqlInfo.getDynamicConditionList()) {
                if (conditionStructure.getFunction() != null) {
                    transformer.transform(adjust(new FunctionLiteralInputClass(
                            fDbSetting,
                            sqlInfo,
                            conditionStructure,
                            cgFactory).expand()), fileBlancoMain);
                }
            }

            switch (sqlInfo.getType()) {
            case BlancoDbSqlInfoTypeStringGroup.ITERATOR:
                createRowObjectClass(
                        BlancoDbUtil.getBasePackage(sqlInfo, fDbSetting),
                        sqlInfo, outputDirectory, fDbSetting, fileBlancoMain);

                transformer.transform(adjust(new QueryIteratorClass(fDbSetting,
                        sqlInfo, cgFactory).expand()), fileBlancoMain);
                break;
            case BlancoDbSqlInfoTypeStringGroup.INVOKER:
                transformer.transform(adjust(new QueryInvokerClass(fDbSetting,
                        sqlInfo, cgFactory).expand()), fileBlancoMain);
                break;
            case BlancoDbSqlInfoTypeStringGroup.CALLER:
                transformer.transform(adjust(new QueryCallerClass(fDbSetting,
                        sqlInfo, cgFactory).expand()), fileBlancoMain);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected error. Unknown query object was given." + sqlInfo.toString());
            }
        }
    }

    /**
     * Creates a row object.
     *
     * @param rootPackage
     * @param sqlInfo
     * @param outputDirectory
     * @param dbSetting
     * @param fileBlancoMain
     * @throws SAXException
     * @throws IOException
     * @throws TransformerException
     */
    public static void createRowObjectClass(final String rootPackage,
            final BlancoDbSqlInfoStructure sqlInfo, final File outputDirectory, final BlancoDbSetting dbSetting, final File fileBlancoMain)
            throws SAXException, IOException, TransformerException {
        final String packageName = rootPackage + ".row";
        final String className = BlancoNameAdjuster.toClassName(sqlInfo
                .getName()) + "Row";

        final List<String[]> listFieldTypes = new ArrayList<String[]>();
        for (int index = 0; index < sqlInfo.getResultSetColumnList().size(); index++) {
            final BlancoDbMetaDataColumnStructure columnStructure = sqlInfo
                    .getResultSetColumnList().get(index);

            try {
                listFieldTypes.add(new String[] {
                        columnStructure.getName(),
                        BlancoDbMappingUtilJava
                                .getFullClassName(columnStructure) });
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Cannot process SQL definition [" + sqlInfo.getName()
                        + "] item name [" + columnStructure.getName()
                        + "] data source dependent type name [" + columnStructure.getTypeName()
                        + "].:" + ex.toString(), ex);
            }
        }

        final BlancoValueObjectClassStructure voClass = new BlancoValueObjectClassStructure();

        voClass.setName(className);
        voClass.setPackage(packageName);
        voClass.setDescription("A row class created from SQL definition (blancoDb).");
        voClass.getDescriptionList().add("'" + className + "' row is represented.");
        for (int index = 0; index < listFieldTypes.size(); index++) {
            final String[] columnTypes = listFieldTypes.get(index);
            final String columnName = columnTypes[0];
            final String columnType = columnTypes[1];

            voClass.getDescriptionList().add(
                    "(" + String.valueOf(index + 1) + ") '" + columnName
                            + "' column type:" + columnType);
        }

        for (int index = 0; index < listFieldTypes.size(); index++) {
            final String[] columnTypes = listFieldTypes.get(index);
            final String columnName = columnTypes[0];
            final String columnType = columnTypes[1];

            final BlancoValueObjectFieldStructure voField = new BlancoValueObjectFieldStructure();
            voField.setName(columnName);
            voField.setType(columnType);
            voField.setDescription("Field [" + columnName + "].");
            voClass.getFieldList().add(voField);
        }

        if (dbSetting.getAddIntrospected()) {
            voClass.getAnnotationList().add("io.micronaut.core.annotation.Introspected");
        }

        final BlancoValueObjectXml2JavaClass xml2javaclass = new BlancoValueObjectXml2JavaClass();
        xml2javaclass.setEncoding(dbSetting.getEncoding());
        xml2javaclass.setTargetStyleAdvanced(true);
        if (dbSetting.getTargetDir() == null) {
            throw new IllegalArgumentException(
                    "BlancoDbGenerator: The blanco output destination folder is not set (null).");
        }
        xml2javaclass.structure2Source(voClass, fileBlancoMain);
    }

    /**
     * Adjusts the contents of the source object.
     *
     * <UL>
     * <LI>Sets the encoding of the source code.
     * </UL>
     *
     * @param arg
     * @return
     */
    private BlancoCgSourceFile adjust(final BlancoCgSourceFile arg) {
        if (BlancoStringUtil.null2Blank(fDbSetting.getEncoding()).length() > 0) {
            arg.setEncoding(fDbSetting.getEncoding());
        }
        return arg;
    }
}
