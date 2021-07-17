/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.iterator;

import java.sql.Types;
import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoNameUtil;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;
import blanco.db.util.BlancoDbMappingUtilJava;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

/**
 * A class for expanding individual methods.
 * 
 * @author Tosiki Iga
 */
public class GetRowMethod extends BlancoDbAbstractMethod {
    public GetRowMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getRow",
                "Gets the data of the current row as an object.");
        fCgClass.getMethodList().add(cgMethod);

        /*
         * If the single attribute is valid, it is protected.
         */
        if (fSqlInfo.getSingle()) {
            cgMethod.setAccess("protected");
        }

        // Gets the type name of a row object.
        final String rowObjectType = BlancoDbUtil.getBasePackage(fSqlInfo,
                fDbSetting)
                + ".row."
                + BlancoNameAdjuster.toClassName(fSqlInfo.getName())
                + "Row";

        cgMethod.setReturn(fCgFactory.createReturn(rowObjectType, "Row object."));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();

        if (fSqlInfo.getSingle()) {
            listDesc.add("Since the single attribute is valid, the scope is set to protected.<br>");
            listDesc.add("Uses the getSingleRow method instead of this method.<br>");
        } else {
            listDesc.add("Before calling this method, you need to call a method that manipulates the cursor, such as next().");
        }

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        listLine.add(BlancoNameUtil.trimJavaPackage(rowObjectType)
                + " result = new "
                + BlancoNameUtil.trimJavaPackage(rowObjectType) + "();");

        int indexCol = 1;
        for (int index = 0; index < fSqlInfo.getResultSetColumnList().size(); index++) {
            final BlancoDbMetaDataColumnStructure columnStructure = (BlancoDbMetaDataColumnStructure) fSqlInfo
                    .getResultSetColumnList().get(index);

            listLine
                    .add("result.set"
                            + BlancoNameAdjuster.toClassName(columnStructure
                                    .getName())
                            + "("
                    + (fDbSetting.getConvertStringToMsWindows31jUnicode()
                            && (columnStructure.getDataType() == Types.CHAR || columnStructure.getDataType() == Types.VARCHAR) ? "blanco.db.runtime.util.BlancoDbRuntimeStringUtil.convertToMsWindows31jUnicode("
                            : "")
                    + BlancoDbMappingUtilJava.mapPrimitiveIntoWrapperClass(columnStructure, "fResultSet."
                            + BlancoDbMappingUtilJava.getGetterMethodNameForResultSet(columnStructure) + "(" + indexCol
                            + ")")
                    + (fDbSetting.getConvertStringToMsWindows31jUnicode()
                            && (columnStructure.getDataType() == Types.CHAR || columnStructure.getDataType() == Types.VARCHAR) ? ")"
                            : "")                            + ");");

            if (BlancoDbMappingUtilJava
                    .getPrimitiveAndNullable(columnStructure)) {
                listLine.add("if (fResultSet.wasNull()) {");
                listLine.add("result.set"
                        + BlancoNameAdjuster.toClassName(columnStructure
                                .getName()) + "(null);");
                listLine.add("}");
            }
            indexCol++;
        }

        listLine.add("");

        listLine.add("return result;");
    }
}