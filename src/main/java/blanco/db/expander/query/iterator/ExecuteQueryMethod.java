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
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * A class for expanding individual methods.
 * 
 * @author Yasuo Nakanishi
 */
public class ExecuteQueryMethod extends BlancoDbAbstractMethod {
    public ExecuteQueryMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("executeQuery",
                null);
        fCgClass.getMethodList().add(cgMethod);

        BlancoDbCgUtilJava.addExceptionToMethodDeadlockTimeoutException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        cgMethod.getLangDoc().getDescriptionList().add("Executes a search-type query.<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        // If the statement is not allocated, it will force an prepareStatement call.
        listLine.add("if (fStatement == null) {");
        listLine
                .add("// Since PreparedStatement has not yet been obtained, it is obtained by calling the prepareStatement() method prior to executing PreparedStatement.executeQuery().");
        listLine.add("prepareStatement();");
        listLine.add("}");

        // If the resultSet is open, closes it first.
        listLine.add("if (fResultSet != null) {");
        listLine.add("// Since the previous result set (ResultSet) is still there, releases it.");
        listLine.add("fResultSet.close();");
        listLine.add("fResultSet = null;");
        listLine.add("}");

        listLine.add("");

        if(fDbSetting.getLoggingsql()) {
        	// Outputs to stdout.
			listLine.add("System.out.println(\"SQL: ["
					+ fSqlInfo.getName()
					+ "](Iterator) "
					+ BlancoJavaSourceUtil
							.escapeStringAsJavaSource(BlancoStringUtil
									.null2Blank(fSqlInfo.getDescription()))
					+ ": \" + fLogSqlInParam + \": ["
					+ (fSqlInfo.getDynamicSql() == false ? BlancoJavaSourceUtil.escapeStringAsJavaSource(fSqlInfo
							.getQuery().replace('\n', ' ')) : "\" + (\"\" + fLogSqlDynamicSql).replace('\\n', ' ') + \"") + "]\");");
		}

		if (fDbSetting.getLogging()) {
			switch (fDbSetting.getLoggingMode()) {
			case BlancoDbLoggingModeStringGroup.PERFORMANCE:
			case BlancoDbLoggingModeStringGroup.SQLID:
				listLine.add("final Runtime runtime = Runtime.getRuntime();");
				listLine.add("final long usedMemoryStart = BlancoDbUtil.getUsedMemory(runtime);");
				listLine.add("final long startTime = System.currentTimeMillis();");
				listLine.add("fLog.info(\"" + fSqlInfo.getName() + " start\");");
				listLine.add("");
				break;
			}
		}

		listLine.add("try {");
		listLine.add("fResultSet = fStatement.executeQuery();");
		listLine.add("} catch (SQLException ex) {");
		listLine.add("throw BlancoDbUtil.convertToBlancoException(ex);");

		if (fDbSetting.getLogging()) {
			switch (fDbSetting.getLoggingMode()) {
			case BlancoDbLoggingModeStringGroup.PERFORMANCE:
			case BlancoDbLoggingModeStringGroup.SQLID:
				listLine.add("} finally {");
				listLine.add("final long endTime = System.currentTimeMillis();");
				listLine.add("final long usedMemoryEnd = BlancoDbUtil.getUsedMemory(runtime);");
				listLine.add("fLog.info(\""
						+ fSqlInfo.getName()
						+ "End Required time: \" + BlancoDbUtil.getTimeString(endTime - startTime) + \" memory used at end: \" + BlancoDbUtil.getMemorySizeString(usedMemoryEnd) + \" Difference in memory used: \" + BlancoDbUtil.getMemorySizeString(usedMemoryEnd - usedMemoryStart));");
				break;
			}
		}

		listLine.add("}");
	}
}