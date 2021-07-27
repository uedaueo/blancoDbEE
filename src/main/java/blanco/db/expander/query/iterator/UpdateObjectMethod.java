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
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
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
public class UpdateObjectMethod extends BlancoDbAbstractMethod {
    private BlancoDbMetaDataColumnStructure fColumnStructure = null;

    public UpdateObjectMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass,
            final BlancoDbMetaDataColumnStructure columnStructure) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
        fColumnStructure = columnStructure;
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("update"
                + BlancoNameAdjuster.toClassName(fColumnStructure.getName()),
                "Updates the '" + fColumnStructure.getName() + "' column of the row where the cursor is currently located.");
        fCgClass.getMethodList().add(cgMethod);

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("arg"
                        + BlancoNameAdjuster.toClassName(fColumnStructure
                                .getName()), BlancoDbMappingUtilJava
                        .getFullClassName(fColumnStructure), "Value to be set in column " + fColumnStructure
                        .getName()));

        switch (fColumnStructure.getDataType()) {
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
        case Types.LONGVARCHAR:
        case Types.CLOB:
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("sourceLength", "int", null));
            break;
        }

        cgMethod.getLangDoc().getDescriptionList().add(
                "The actual update is done when the updateRow method is called.<br>");
        cgMethod.getLangDoc().getDescriptionList().add(
                "It is generated since the updatable attribute is enabled.<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                listLine.add("fLog.debug(\""
                        + cgMethod.getName()
                        + ": arg"
                        + BlancoNameAdjuster.toClassName(fColumnStructure
                                .getName())
                        + " = \" + arg"
                        + BlancoNameAdjuster.toClassName(fColumnStructure
                                .getName()) + ");");
                listLine.add("}");
                listLine.add("");
            }
        }

        if (BlancoDbMappingUtilJava.getPrimitiveAndNullable(fColumnStructure)) {
            listLine.add("if (arg"
                    + BlancoNameAdjuster
                            .toClassName(fColumnStructure.getName())
                    + " == null) {");
            listLine.add("fResultSet.updateNull(\""
                    + fColumnStructure.getName() + "\");");
            listLine.add("} else {");
        }

        String optionParam = "";

        switch (fColumnStructure.getDataType()) {
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
        case Types.LONGVARCHAR:
        case Types.CLOB:
            optionParam = ", sourceLength";
            break;
        }

        listLine.add("fResultSet."
                + BlancoDbMappingUtilJava
                        .getUpdateMethodNameForResultSet(fColumnStructure)
                + "(\""
                + fColumnStructure.getName()
                + "\", "
                + BlancoDbMappingUtilJava
                        .mapWrapperClassIntoPrimitive(fColumnStructure,
                                "arg"
                                        + BlancoNameAdjuster
                                                .toClassName(fColumnStructure
                                                        .getName()))
                + optionParam + ");");

        if (BlancoDbMappingUtilJava.getClassName(fColumnStructure).equals(
                "Date")) {
            // Timestamp type is required in the BlancoDbMappingUtil.mapWrapperClassIntoPrimitive method.
            fCgSourceFile.getImportList().add("java.sql.Timestamp");
        }

        if (BlancoDbMappingUtilJava.getPrimitiveAndNullable(fColumnStructure)) {
            listLine.add("}");
        }
    }
}