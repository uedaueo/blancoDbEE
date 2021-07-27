/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.caller;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbQueryParserUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;
import blanco.dbmetadata.BlancoDbMetaDataUtil;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

/**
 * A class for expanding individual methods.
 *
 * @author tosiki iga
 */
public class PrepareCallMethod2 extends BlancoDbAbstractMethod {
    public PrepareCallMethod2(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("prepareCall",
                "Precompiles with the given SQL statement (dynamic SQL).");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod
                .getParameterList()
                .add(
                        fCgFactory
                                .createParameter("query", "java.lang.String",
                                        "The SQL statement that you want to have precompiled. In the case of dynamic SQL, this argument is the executable SQL statement after it has been processed."));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();
        listDesc.add("Uses this method only when you need to execute SQL that dynamically changes its contents.<br>");
        listDesc
                .add("If not, please avoid using this method and call the prepareCall() method (no arguments).<br>");
        listDesc
                .add("This is because, while this method allows a high degree of freedom in giving the SQL statement itself as a parameter, it also causes the possibility of a security hole called SQL injection.<br>");
        listDesc.add("Calls Connection.prepareCall internally.<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                listLine.add("fLog.debug(\"" + cgMethod.getName()
                        + ": query = \" + query);");
                listLine.add("}");
                break;
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
                listLine.add("fLog.info(\"" + cgMethod.getName()
                        + ": query = \" + query);");
                break;
            }
            listLine.add("");
        }

        listLine.add("close();");

        listLine.add("fStatement = fConnection.prepareCall(query);");

        final BlancoDbQueryParserUtil query = new BlancoDbQueryParserUtil(
                fSqlInfo);

        for (int indexParameter = 0; indexParameter < fSqlInfo
                .getOutParameterList().size(); indexParameter++) {
            // Currently, the assumption that they will appear in a given order has been added for now.
            final BlancoDbMetaDataColumnStructure columnStructure = (BlancoDbMetaDataColumnStructure) fSqlInfo
                    .getOutParameterList().get(indexParameter);

            final List<Integer> listCol = query.getSqlParameters(columnStructure
                    .getName());
            if (listCol == null) {
                System.out.println("The SQL output parameter ["
                        + columnStructure.getName() + "] of [" + fSqlInfo.getName() + "] is not connected.");
                continue;
            }
            for (int iteSame = 0; iteSame < listCol.size(); iteSame++) {
                final int index = listCol.get(iteSame);

                String stmtLine = "fStatement.registerOutParameter("
                        + index
                        + ", java.sql.Types."
                        + BlancoDbMetaDataUtil
                                .convertJdbcDataTypeToString(columnStructure
                                        .getDataType());
                stmtLine += ");";
                listLine.add(stmtLine);
            }
        }
    }
}
