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
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * A class for expanding individual methods.
 * 
 * This method will be created when the cursor attribute is true.
 * 
 * @author Tosiki Iga
 */
public class AbsoluteMethod extends BlancoDbAbstractMethod {
    public AbsoluteMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("absolute",
                "Moves the cursor to the specified line of the result set.");
        fCgClass.getMethodList().add(cgMethod);

        /*
         * If the single attribute is valid, it is protected.
         */
        if (fSqlInfo.getSingle()) {
            cgMethod.setAccess("protected");
        }

        cgMethod.setReturn(fCgFactory.createReturn("boolean",
                "True if the new current row is valid, false if there are no more rows."));

        BlancoDbCgUtilJava.addExceptionToMethodDeadlockTimeoutException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        cgMethod
                .getParameterList()
                .add(
                        fCgFactory
                                .createParameter("rows", "int",
                                        "Specifies the line number to which the cursor will move. If it is a positive number, it counts from the beginning of the result set. If the number is negative, it counts from the end of the result set."));

        if (fSqlInfo.getSingle()) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "Since the single attribute is valid, the scope is set to protected.<br>");
        }
        cgMethod.getLangDoc().getDescriptionList().add(
                "absolute(1) is the same as calling first().<br>");
        cgMethod.getLangDoc().getDescriptionList().add(
                "absolute(-1) is the same as calling last().<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                listLine.add("fLog.debug(\"" + cgMethod.getName()
                        + ": rows = \" + rows);");
                listLine.add("}");
                listLine.add("");
                break;
            }
        }

        // If the resultSet is not allocated, it will force an executeQuery call.
        listLine.add("if (fResultSet == null) {");
        listLine.add("executeQuery();");
        listLine.add("}");

        listLine.add("");
        listLine.add("try {");
        listLine.add("return fResultSet.absolute(rows);");
        listLine.add("} catch (SQLException ex) {");
        listLine.add("throw BlancoDbUtil.convertToBlancoException(ex);");
        listLine.add("}");
    }
}