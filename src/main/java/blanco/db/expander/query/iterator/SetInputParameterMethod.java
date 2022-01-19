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

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbQueryParserUtil;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbDynamicConditionFunctionStructure;
import blanco.db.common.valueobject.BlancoDbDynamicConditionStructure;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;
import blanco.db.util.BlancoDbMappingUtilJava;
import blanco.dbmetadata.BlancoDbMetaDataUtil;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for expanding individual methods.
 *
 * @author Tosiki Iga
 */
public class SetInputParameterMethod extends BlancoDbAbstractMethod {
    private boolean fIsCallableStatement = false;

    public SetInputParameterMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass, final boolean isCallableStatement) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
        fIsCallableStatement = isCallableStatement;
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                "setInputParameter", "Sets the SQL input parameters to be given to the SQL statement.");
        fCgClass.getMethodList().add(cgMethod);

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        /*
         * First, creates the parameters in the order in which the static input parameters are defined.
         */
        for (BlancoDbMetaDataColumnStructure columnStructure : fSqlInfo.getInParameterList()) {
            this.createStaticParameter(cgMethod, columnStructure);
        }

        // Imports utilities unconditionally.
        String dbUtilClass = BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util.BlancoDbUtil";
        String dynamicClauseClass = BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util.BlancoDbDynamicClause";
        String dynamicParameterClass = BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util.BlancoDbDynamicParameter";
        String dynamicOrderByClass = BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util.BlancoDbDynamicOrderBy";
        String dynamicLiteralClass = BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util.BlancoDbDynamicLiteral";

        fCgSourceFile.getImportList().add(dbUtilClass);
        fCgSourceFile.getImportList().add(dynamicClauseClass);
        fCgSourceFile.getImportList().add(dynamicParameterClass);
        fCgSourceFile.getImportList().add(dynamicOrderByClass);
        fCgSourceFile.getImportList().add(dynamicLiteralClass);

        /*
         * Next, creates the parameters in the order of the dynamic conditional clause definition.
         * There can be duplicate tags, so checks and omits them.
         */
        Map<String, Boolean> paramDone = new HashMap<>();
        for (BlancoDbDynamicConditionStructure conditionStructure : fSqlInfo.getDynamicConditionList()) {
            if (paramDone.get(conditionStructure.getTag()) == null || !paramDone.get(conditionStructure.getTag())) {
                this.createDynamicParameter(cgMethod, conditionStructure, dynamicParameterClass);
                paramDone.put(conditionStructure.getTag(), true);
            }
        }

        if (fIsCallableStatement == false) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "Internally, the PreparedStatement is set with SQL input parameters.");
        } else {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "Internally, the CallableStatement is set with SQL input parameters.");
        }

        /*
         * create timeout value parameter if required.
         */
        if (fSqlInfo.getUseTimeoutHintMySQL()) {
            BlancoCgParameter param = fCgFactory.createParameter(
                    "argTimeout",
                    "java.lang.Long",
                    "Timeout value in milli-seconds.");
            param.setFinal(true);
            cgMethod.getParameterList().add(param);
        }

        /*
         * create timeout value parameter if required.
         */
        if (fSqlInfo.getUseTimeoutHintMySQL()) {
            BlancoCgParameter param = fCgFactory.createParameter(
                    "argTimeout",
                    "java.lang.Long",
                    "Timeout value in milli-seconds.");
            param.setFinal(true);
            cgMethod.getParameterList().add(param);
        }

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                String strLine = "fLog.debug(\"" + cgMethod.getName() + ": ";
                boolean isFirst = true;
                for (int index = 0; index < fSqlInfo.getInParameterList()
                        .size(); index++) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        strLine += " + \", ";
                    }

                    final BlancoDbMetaDataColumnStructure parameter = (BlancoDbMetaDataColumnStructure) fSqlInfo
                            .getInParameterList().get(index);

                    strLine += parameter.getName() + " = \" + "
                            + parameter.getName();
                }
                strLine += ");";

                listLine.add(strLine);
                listLine.add("}");

                listLine.add("");
                break;
            }
        }

        /*
         * If the dynamic conditional clause is set, inserts the tag replacement code.
         */
        if (this.fSqlInfo.getDynamicConditionList().size() > 0 || this.fSqlInfo.getUseTimeoutHintMySQL()) {
            this.createTagConversion(listLine, this.fSqlInfo.getDynamicConditionList(), this.fSqlInfo.getUseTimeoutHintMySQL());
            listLine.add("");
            listLine.add("/* Always recreates the statement. */");
            listLine.add("prepareStatement(query);");
        } else {
            // If statement is not yet allocated, it will forcibly call prepareStatement.
            listLine.add("if (fStatement == null) {");
            if (fIsCallableStatement == false) {
                listLine.add("prepareStatement();");
            } else {
                listLine.add("prepareCall();");
            }
            listLine.add("}");
        }

        listLine.add("");
        listLine.add("int index = 1;");

		if (fDbSetting.getLoggingsql()) {
			// Point: If you don't remember it after prepareStatement() or prepareCall(), it will disappear. That's why we put them here.
			String strLine = "fLogSqlInParam = \"";
			boolean isFirst = true;
			for (int index = 0; index < fSqlInfo.getInParameterList().size(); index++) {
				if (isFirst) {
					isFirst = false;
				} else {
					strLine += ",";
				}

				final BlancoDbMetaDataColumnStructure parameter = (BlancoDbMetaDataColumnStructure) fSqlInfo
						.getInParameterList().get(index);

				strLine += parameter.getName() + "=[\" + "
						+ parameter.getName() + " + \"]";
			}
			strLine += "\";";

			listLine.add(strLine);
		}

        /*
         * Creates a utility.
         */
        final BlancoDbQueryParserUtil parserUtil = new BlancoDbQueryParserUtil(fSqlInfo);

        /*
         * Gets an ordered list of parameters in SQL.
         */
        List<BlancoDbMetaDataColumnStructure> parameterList = parserUtil.convertSqlInParameter2NativeParameter(fSqlInfo);

        /*
         * It will set the parameters.
         * Dynamic conditional clauses may have duplicate tag definitions, so checks and omits them.
         * ORDERBY and LITERAL do not pass any parameters.
         */
        Map<String, Boolean> inputDone = new HashMap<>();
        for (BlancoDbMetaDataColumnStructure columnStructure : parameterList) {
            /*
             * Make sure that the parameters are tied together.
             */
            final List<Integer> listCol = parserUtil.getSqlParameters(columnStructure.getName());
            if (listCol == null) {
                throw new IllegalArgumentException("SQL inpute parameter ["
                        + columnStructure.getName() +"] of SQL definition ID ["
                        + fSqlInfo.getName() + "] is not connected.");
            }

            BlancoDbDynamicConditionStructure conditionStructure = parserUtil.getConditionStructureByTag(columnStructure.getName());
            if (conditionStructure == null) {
                // Static input parameters.
                this.createStaticInput(listLine, columnStructure);
            } else {
                // Dynamic conditional clause parameters.
                if ((inputDone.get(conditionStructure.getTag()) == null || !inputDone.get(conditionStructure.getTag())) && !"ORDERBY".equals(conditionStructure.getCondition()) && !"LITERAL".equals(conditionStructure.getCondition())) {
                    this.createDynamicInput(listLine, conditionStructure);
                    inputDone.put(conditionStructure.getTag(), true);
                }
            }
        }
    }

    /**
     * Generates static parameters.
     * @param cgMethod
     * @param columnStructure
     */
    private void createStaticParameter(
            final BlancoCgMethod cgMethod,
            final BlancoDbMetaDataColumnStructure columnStructure
            ) {

        cgMethod.getParameterList().add(
                fCgFactory.createParameter(columnStructure.getName(),
                        BlancoDbMappingUtilJava
                                .getFullClassName(columnStructure), "Value in '"
                                + columnStructure.getName() + "' column"));

        switch (columnStructure.getDataType()) {
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
            case Types.LONGVARCHAR:
            case Types.CLOB:
                cgMethod.getParameterList().add(
                        fCgFactory.createParameter(columnStructure.getName()
                                + "StreamLength", "int", null));
                break;
        }
    }

    /**
     * Generates parameters corresponding to dynamic conditional clauses.
     * @param cgMethod
     * @param conditionStructure
     */
    private void createDynamicParameter(
            final BlancoCgMethod cgMethod,
            final BlancoDbDynamicConditionStructure conditionStructure,
            final String dynamicParameterClass
    ) {
        BlancoDbMetaDataColumnStructure columnStructure = conditionStructure.getDbColumn();
        BlancoCgParameter param = fCgFactory.createParameter(
                conditionStructure.getTag(),
                dynamicParameterClass,
                "Value in '" + conditionStructure.getTag() + "' column");
        cgMethod.getParameterList().add(param);
        if ("LITERAL".equals(conditionStructure.getCondition())) {
            param.getType().setGenerics("BlancoDbDynamicLiteral");
        } else if ("FUNCTION".equals(conditionStructure.getCondition())) {
            String inputClass = BlancoNameAdjuster.toClassName(fSqlInfo.getName()) + BlancoNameAdjuster.toClassName(conditionStructure.getTag()) + "Input";
            param.getType().setGenerics(inputClass);
        } else if ("ORDERBY".equals(conditionStructure.getCondition())) {
            param.getType().setGenerics("BlancoDbDynamicOrderBy");
        } else {
            param.getType().setGenerics(conditionStructure.getType());
        }
        /*
         * The dynamic conditional clause does not support BINARY types for the time being.
         */
        int loop = 1;
        BlancoDbDynamicConditionFunctionStructure functionStructure = conditionStructure.getFunction();
        if ("FUNCTION".equals(conditionStructure.getCondition())) {
            loop = functionStructure.getParamNum();
        }
        for (int i = 0; i < loop; i++) {
            int dataType = columnStructure.getDataType();
            if ("FUNCTION".equals(conditionStructure.getCondition())) {
                dataType = functionStructure.getDbColumnList().get(i).getDataType();
            }
            switch (dataType) {
                case Types.BINARY:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                case Types.BLOB:
                case Types.LONGVARCHAR:
                case Types.CLOB:
                    throw new IllegalArgumentException("Dynamic conditional clauses will not support BINARY types for the time being");
            }
        }
    }

    /**
     * Generates a routine to dynamically transform Tag in dynamic conditional clauses.
     * @param listLine
     * @param conditionStructureList
     */
    private void createTagConversion(
            final List<String> listLine,
            final List<BlancoDbDynamicConditionStructure> conditionStructureList,
            final boolean useTimeoutHintMySQL
    ) {
        listLine.add("/* Replace tags  */");
        listLine.add("String query = this.getQuery();");
        if (useTimeoutHintMySQL) {
            listLine.add("query = BlancoDbUtil.createTimeoutHintMySQL(argTimeout, query);");
        }
        Map<String, Boolean> convDone = new HashMap<>();
        for (BlancoDbDynamicConditionStructure conditionStructure : conditionStructureList) {
            if (convDone.get(conditionStructure.getTag()) == null || !convDone.get(conditionStructure.getTag())) {
                listLine.add("query = BlancoDbUtil.createDynamicClause(fMapDynamicClause, " +
                        conditionStructure.getTag() + ", query, \"" + conditionStructure.getTag() + "\");");
                convDone.put(conditionStructure.getTag(), true);
            }
        }
    }

    private void createStaticInput(
            final List<String> listLine,
            final BlancoDbMetaDataColumnStructure columnStructure
    ) {
        if (BlancoDbMappingUtilJava.getPrimitiveAndNullable(columnStructure)) {
            listLine.add("if (" + columnStructure.getName()
                    + " == null) {");

            // Previously, there was code here to emulate a bug in a previous version (1.6.4).
            // This feature has been abandoned.
            final int jdbcDataType = columnStructure.getDataType();

            listLine.add("fStatement.setNull(index"
                    + ", "
                    + "java.sql.Types."
                    + BlancoDbMetaDataUtil
                    .convertJdbcDataTypeToString(jdbcDataType)
                    + ");");
            listLine.add("} else {");
        }

        final String type = BlancoDbMappingUtilJava.getSetterMethodNameForPreparedStatement(columnStructure);
        switch (columnStructure.getDataType()) {
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                fCgSourceFile.getImportList().add("java.sql.Timestamp");
                break;
        }

        String stmtLine = "fStatement."
                + type
                + "(index"
                + ", "
                + BlancoDbMappingUtilJava.mapWrapperClassIntoPrimitive(columnStructure, columnStructure.getName());

        switch (columnStructure.getDataType()) {
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
            case Types.LONGVARCHAR:
            case Types.CLOB:
                stmtLine += ", " + columnStructure.getName() + "StreamLength";
                break;
        }

        stmtLine += ")";
        listLine.add(stmtLine + ";");

        if (BlancoDbMappingUtilJava.getPrimitiveAndNullable(columnStructure)) {
            listLine.add("}");
        }
        listLine.add("index++;");
        listLine.add("");

        switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
                // Log output of SQL input parameter values with numbers.
                // The number is one quire for each.
                String strLineInfo = "fLog.info(\"" + fSqlInfo.getName() +
                        ": SQL input parameters \" + index + \": [\" + " +
                        columnStructure.getName() + " + \"]\");";
                listLine.add(strLineInfo);
                break;
        }
    }

    private void createDynamicInput(
            final List<String> listLine,
            final BlancoDbDynamicConditionStructure conditionStructure
    ) {
        Boolean isFunctionLiteral = "FUNCTION".equals(conditionStructure.getCondition());
        String tag = conditionStructure.getTag();
        String type = conditionStructure.getType();
        listLine.add("if (" + tag + " != null) {");
        if (!isFunctionLiteral) {
            listLine.add("java.util.List<" + type + "> values = " + tag + ".getValues();");
            listLine.add("index = BlancoDbUtil.setInputParameter(fStatement, values, index);");
        } else {
            BlancoDbDynamicConditionFunctionStructure functionStructure = conditionStructure.getFunction();
            String inputClass = BlancoNameAdjuster.toClassName(fSqlInfo.getName()) + BlancoNameAdjuster.toClassName(conditionStructure.getTag()) + "Input";
            listLine.add(inputClass + " input = " + tag + ".getValues().get(0);");
            Class<? extends BlancoDbDynamicConditionFunctionStructure> clazz = functionStructure.getClass();
            for (int i = 1; i <= functionStructure.getParamNum(); i++) {
                String tagParamType = String.format("paramType%02d", i);
                String strMethod = "get" + BlancoNameAdjuster.toClassName(tagParamType);
                try {
                    Method method = clazz.getMethod(strMethod);
                    type = (String) method.invoke(functionStructure);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new IllegalArgumentException("Fail to get " + tagParamType, e);
                }
                String tagParam = String.format("param%02d", i);
                listLine.add("java.util.List<" + type + "> " + tagParam + " = new java.util.ArrayList<>();");
                listLine.add(tagParam + ".add((" + type + ") input.getParam(" + i + "));");
                listLine.add("index = BlancoDbUtil.setInputParameter(fStatement, " + tagParam + ", index);");
            }
        }
        listLine.add("}");

        listLine.add("");

        switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
                // Log output of SQL input parameter values with numbers.
                // The number is one quire for each.
                String strLineInfo = "fLog.info(\"" + fSqlInfo.getName() +
                        ": SQL input parameters \" + index + \": [\" + " +
                        conditionStructure.getTag() + " + \"]\");";
                listLine.add(strLineInfo);
                break;
        }
    }
}
