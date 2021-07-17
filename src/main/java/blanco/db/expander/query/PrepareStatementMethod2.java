/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.stringgroup.BlancoDbSqlInfoScrollStringGroup;
import blanco.db.common.stringgroup.BlancoDbSqlInfoTypeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * A class for expanding individual methods.
 * 
 * @author tosiki iga
 */
public class PrepareStatementMethod2 extends BlancoDbAbstractMethod {
    public PrepareStatementMethod2(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                "prepareStatement", "Precompiles with the given SQL statement (dynamic SQL).");
        fCgClass.getMethodList().add(cgMethod);

        if (fSqlInfo.getDynamicSql() == false) {
            // If the dynamic SQL usage flag is OFF, this method for dynamic SQL will be protected.
            cgMethod.setAccess("protected");
        }

        cgMethod.getParameterList()
                .add(fCgFactory
                        .createParameter("query", "java.lang.String",
                                "The SQL statement that you want to have precompiled. In the case of dynamic SQL, this argument is the executable SQL statement after it has been processed."));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();

        listDesc.add("This method should only be used when you need to execute SQL that dynamically changes its contents.<br>");
        if (fSqlInfo.getDynamicSql() == false) {
            listDesc.add("If you need to use dynamic SQL, please change \"Dynamic SQL\" to \"Use\" in the SQL definition document. After the change, it will be available externally.<br>");
        } else {
            listDesc.add("\"Dynamic SQL\" is set to \"Use\" in the SQL definition document.<br>");
        }
        listDesc.add("Internally calls the JDBC driver's Connection.prepareStatement.<br>");

        if (fSqlInfo.getType() == BlancoDbSqlInfoTypeStringGroup.ITERATOR) {
            // Outputs only for search type.

            // TODO: 
            // In the case of BlancoDbSqlInfoScrollStringGroup.NOT_DEFINED, nothing should be output, but for compatibility with 1.6.4, the scroll direction is output to LangDoc.

            if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY
                    && fSqlInfo.getUpdatable() == false) {
                // If the cursor is in the forward direction and the updatable attribute is OFF, nothing will be output to LangDoc.
            } else {
                listDesc.add("scroll attribute: "
                        + new BlancoDbSqlInfoScrollStringGroup()
                                .convertToString(fSqlInfo.getScroll()));
                if (fSqlInfo.getUpdatable()) {
                    listDesc.add("Update possible properties: Effective");
                }
            }
        }

        final List<String> listLine = cgMethod.getLineList();

        if(fDbSetting.getLoggingsql()) {
        	// Output to stdout.
			listLine.add("fLogSqlInParam = \"\";");

			if (fSqlInfo.getDynamicSql()) {
				listLine.add("fLogSqlDynamicSql = query;");
			}
		}
        
        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                listLine.add("fLog.debug(\"" + cgMethod.getName()
                        + ": query = \" + query);");
                listLine.add("}");
                break;
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
                listLine.add("fLog.info(\"" + fSqlInfo.getName()
                        + "execution SQL\\n\" + query);");
                break;
            }
            listLine.add("");
        }

        listLine.add("close();");

        // TODO: If the scroll direction is unspecified, we also tried to specify no scroll direction about JDBC API, but with that specification, the behavior is different from 1.6.4.
        // TODO: For compatibility with 1.6.4, removes the condition for no scroll direction specification.

        if (fSqlInfo.getType() == BlancoDbSqlInfoTypeStringGroup.INVOKER
                || fSqlInfo.getType() == BlancoDbSqlInfoTypeStringGroup.CALLER) {
            // In the case of executable and call types, simply calls prepareStatement.
            listLine.add("fStatement = fConnection.prepareStatement(query);");
        } else if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY
                && fSqlInfo.getUpdatable() == false) {
            // Among the search types, if the parameter variation is simple, simply calls prepareStatement.
            listLine.add("fStatement = fConnection.prepareStatement(query);");
        } else {
            // Generates arguments according to the content of the variation.
            // Note that the search type BlancoDbSqlInfoScrollStringGroup.NOT_DEFINED also passes through here.
            // This is necessary for compatibility with 1.6.4.

            String resultSetType = "ResultSet.TYPE_FORWARD_ONLY";
            String resultSetConcurrency = "ResultSet.CONCUR_READ_ONLY";
            if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_SCROLL_INSENSITIVE) {
                resultSetType = "ResultSet.TYPE_SCROLL_INSENSITIVE";
            } else if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_SCROLL_SENSITIVE) {
                resultSetType = "ResultSet.TYPE_SCROLL_SENSITIVE";
            }
            if (fSqlInfo.getUpdatable()) {
                resultSetConcurrency = "ResultSet.CONCUR_UPDATABLE";
            }
            listLine.add("fStatement = fConnection.prepareStatement(query, "
                    + resultSetType + ", " + resultSetConcurrency + ");");
        }

        if (fDbSetting.getStatementTimeout() >= 0) {
            listLine.add("// Sets the default value for the statement timeout value.");
            listLine.add("fStatement.setQueryTimeout("
                    + fDbSetting.getStatementTimeout() + ");");
        }
    }
}