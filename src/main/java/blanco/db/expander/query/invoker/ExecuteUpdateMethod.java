/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.invoker;

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
public class ExecuteUpdateMethod extends BlancoDbAbstractMethod {
    public ExecuteUpdateMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                "executeUpdate", "Executes the SQL statement.");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod.setReturn(fCgFactory.createReturn("int", "The number of processed lines"));

        /*
         * If the single attribute is valid, it is protected.
         */
        if (fSqlInfo.getSingle()) {
            cgMethod.setAccess("protected");
        } else {
            // It remains public.
        }

        BlancoDbCgUtilJava.addExceptionToMethodIntegrityConstraintException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodDeadlockTimeoutException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        if (fSqlInfo.getSingle()) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "Since the single attribute is valid, the scope is set to protected.<br>");
            cgMethod.getLangDoc().getDescriptionList().add(
                    "Use the executeSingleUpdate method instead of this method.<br>");
        }

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        // If statement is not yet allocated, it will forcibly call prepareStatement.
        listLine.add("if (fStatement == null) {");
        listLine
                .add("// Since PreparedStatement has not been obtained yet, obtains by calling prepareStatement() method prior to executing PreparedStatement.executeUpdate().");
        listLine.add("prepareStatement();");
        listLine.add("}");

        listLine.add("");

        if(fDbSetting.getLoggingsql()) {
        	// Outputs to stdout.
			listLine.add("System.out.println(\"SQL: ["
					+ fSqlInfo.getName()
					+ "](Invoker) "
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
                listLine
                        .add("final long usedMemoryStart = BlancoDbUtil.getUsedMemory(runtime);");
                listLine
                        .add("final long startTime = System.currentTimeMillis();");
                listLine.add("fLog.info(\"" + fSqlInfo.getName() + "start\");");
                break;
            }
            listLine.add("");
        }

        // Expands including exception handling.
        listLine.add("try {");
        listLine.add("return fStatement.executeUpdate();");
        listLine.add("} catch (SQLException ex) {");
        listLine.add("throw BlancoDbUtil.convertToBlancoException(ex);");

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
            case BlancoDbLoggingModeStringGroup.SQLID:
                listLine.add("} finally {");
                listLine
                        .add("final long endTime = System.currentTimeMillis();");
                listLine
                        .add("final long usedMemoryEnd = BlancoDbUtil.getUsedMemory(runtime);");
                listLine
                        .add("fLog.info(\""
                                + fSqlInfo.getName()
                                + "End Required time: \" + BlancoDbUtil.getTimeString(endTime - startTime) + \" memory used at end: \" + BlancoDbUtil.getMemorySizeString(usedMemoryEnd) + \" Difference in memory used: \" + BlancoDbUtil.getMemorySizeString(usedMemoryEnd - usedMemoryStart));");
                break;
            }
        }

        listLine.add("}");
    }
}