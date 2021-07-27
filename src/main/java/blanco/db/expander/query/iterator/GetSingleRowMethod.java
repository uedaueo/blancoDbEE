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
import blanco.db.expander.exception.NoRowFoundExceptionClass;
import blanco.db.expander.exception.TooManyRowsFoundExceptionClass;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * A class for expanding individual methods.
 * 
 * This class will be used only if the single attribute is true.
 * 
 * @author Tosiki Iga
 */
public class GetSingleRowMethod extends BlancoDbAbstractMethod {
    public GetSingleRowMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getSingleRow",
                "Gets the data of the current row as an object.");
        fCgClass.getMethodList().add(cgMethod);

        // Gets the type name of a row object.
        final String rowObjectType = BlancoDbUtil.getBasePackage(fSqlInfo,
                fDbSetting)
                + ".row."
                + BlancoNameAdjuster.toClassName(fSqlInfo.getName())
                + "Row";

        cgMethod.setReturn(fCgFactory.createReturn(rowObjectType, "The row object."));

        cgMethod.getThrowList().add(
                fCgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(fDbSetting)
                        + ".exception.NoRowFoundException",
                        "If no rows of data were retrieved as a result of the database processing."));
        cgMethod.getThrowList().add(
                fCgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(fDbSetting)
                        + ".exception.TooManyRowsFoundException",
                        "If more than one row of data has been retrieved as a result of database processing."));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();

        listDesc.add("Verifies that the result of the SQL statement execution is a single row. If the result is not a single row, it will raise an exception.<br>");
        listDesc.add("Since the single attribute is valid, it will be generated.<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".exception."
                        + NoRowFoundExceptionClass.CLASS_NAME);

        listLine.add("if (next() == false) {");
        listLine
                .add("throw new NoRowFoundException(\"No rows of data were retrieved as a result of the database processing.\");");
        listLine.add("}");
        listLine.add("");

        listLine.add(BlancoNameUtil.trimJavaPackage(rowObjectType)
                + " result = getRow();");
        listLine.add("");

        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".exception."
                        + TooManyRowsFoundExceptionClass.CLASS_NAME);

        // Checks if changes were made beyond one line.
        listLine.add("if (next()) {");
        listLine
                .add("throw new TooManyRowsFoundException(\"More than one row of data has been retrieved as a result of database processing.\");");
        listLine.add("}");
        listLine.add("");

        listLine.add("return result;");
    }
}