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
import java.util.Iterator;
import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbQueryParserUtil;
import blanco.db.common.valueobject.BlancoDbDynamicConditionStructure;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;
import blanco.db.util.BlancoDbMappingUtilJava;
import blanco.dbmetadata.BlancoDbMetaDataUtil;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

/**
 * 個別のメソッドを展開するためのクラス。
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
                "setInputParameter", "SQL文に与えるSQL入力パラメータをセットします。");
        fCgClass.getMethodList().add(cgMethod);

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        /*
         * まず静的インプットパラメータの定義順にパラメータを作ります。
         */
        for (BlancoDbMetaDataColumnStructure columnStructure : fSqlInfo.getInParameterList()) {
            this.createStaticParameter(cgMethod, columnStructure);
        }

        /*
         * 次に動的条件句定義の順にパラメータを作ります。
         */
        for (BlancoDbDynamicConditionStructure conditionStructure : fSqlInfo.getDynamicConditionList()) {
            this.createDynamicParameter(cgMethod, conditionStructure);
        }

        if (fIsCallableStatement == false) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "内部的には PreparedStatementにSQL入力パラメータをセットします。");
        } else {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "内部的には CallableStatementにSQL入力パラメータをセットします。");
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
         * 動的条件句が設定されている場合はタグの置換コードを挿入します。
         */
        if (this.fSqlInfo.getDynamicConditionList().size() > 0) {
            this.createTagConversion(listLine, this.fSqlInfo.getDynamicConditionList());
            listLine.add("");
            listLine.add("/* 必ず statement を作り直す */");
            listLine.add("prepareStatement(query);");
        } else {
            // statementが未確保であるばあい、強制的にprepareStatementを呼び出します。
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
			// ポイント: prepareStatement(); や prepareCall(); のあとで記憶しないのと消え去ってしまいます。そのためここに配置しています。
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
         * ユーティリティを作成します。
         */
        final BlancoDbQueryParserUtil parserUtil = new BlancoDbQueryParserUtil(fSqlInfo);

        /*
         * SQL 内のパラメータを順序通りに並べたリストを取得します。
         */
        List<BlancoDbMetaDataColumnStructure> parameterList = parserUtil.convertSqlInParameter2NativeParameter(fSqlInfo);

        /*
         * パラメータをセットしていきます。
         * 注意) BETWEEN にはパラメータリストが二重に定義されいているので
         * ひとつ省く必要があります。
         */
        for (BlancoDbMetaDataColumnStructure columnStructure : parameterList) {
            /*
             * パラメータが結びついていることを確認します。
             */
            final int[] listCol = parserUtil.getSqlParameters(columnStructure.getName());
            if (listCol == null) {
                throw new IllegalArgumentException("SQL定義ID["
                        + fSqlInfo.getName() + "]の SQL入力パラメータ["
                        + columnStructure.getName() + "]が結びついていせん.");
            }


            BlancoDbDynamicConditionStructure conditionStructure = parserUtil.getConditionStructureByTag(columnStructure.getName());
            if (conditionStructure == null) {
                // 静的パラメータです。
                this.createStaticInput(listLine, columnStructure);
            } else {
                this.createDynamicInput(listLine, conditionStructure);
            }
        }

        final Iterator<BlancoDbMetaDataColumnStructure> ite = parameterList.iterator();
        while (ite.hasNext()) {
            // SQL文からパラメータを発見しています。
            final BlancoDbMetaDataColumnStructure columnStructure = ite.next();

            final int[] listCol = parserUtil.getSqlParameters(columnStructure
                    .getName());
            if (listCol == null) {
                throw new IllegalArgumentException("SQL定義ID["
                        + fSqlInfo.getName() + "]の SQL入力パラメータ["
                        + columnStructure.getName() + "]が結びついていせん.");
            }
            for (int iteSame = 0; iteSame < listCol.length; iteSame++) {
                final int index = listCol[iteSame];
                if (BlancoDbMappingUtilJava
                        .getPrimitiveAndNullable(columnStructure)) {
                    listLine.add("if (" + columnStructure.getName()
                            + " == null) {");

                    // 以前、ここに過去のバージョン (1.6.4) のバグをエミュレートするためのコードがありました。
                    // 現在は、この過去バグエミュレート機能は破棄されています。
                    final int jdbcDataType = columnStructure.getDataType();

                    listLine.add("fStatement.setNull("
                            + index
                            + ", "
                            + "java.sql.Types."
                            + BlancoDbMetaDataUtil
                                    .convertJdbcDataTypeToString(jdbcDataType)
                            + ");");
                    listLine.add("} else {");
                }

                final String type = BlancoDbMappingUtilJava
                        .getSetterMethodNameForPreparedStatement(columnStructure);
                switch (columnStructure.getDataType()) {
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    fCgSourceFile.getImportList().add("java.sql.Timestamp");
                    break;
                }

                String stmtLine = "fStatement."
                        + type
                        + "("
                        + index
                        + ", "
                        + BlancoDbMappingUtilJava.mapWrapperClassIntoPrimitive(
                                columnStructure, columnStructure.getName());

                switch (columnStructure.getDataType()) {
                case Types.BINARY:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                case Types.BLOB:
                case Types.LONGVARCHAR:
                case Types.CLOB:
                    stmtLine += ", " + columnStructure.getName()
                            + "StreamLength";
                    break;
                }

                stmtLine += ")";
                listLine.add(stmtLine + ";");

                if (BlancoDbMappingUtilJava
                        .getPrimitiveAndNullable(columnStructure)) {
                    listLine.add("}");
                }

            }
        }
    }

    /**
     * 静的パラメータを生成します。
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
                                .getFullClassName(columnStructure), "'"
                                + columnStructure.getName() + "'列の値"));

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
     * 動的条件句に対応するパラメータを生成します。
     * @param cgMethod
     * @param conditionStructure
     */
    private void createDynamicParameter(
            final BlancoCgMethod cgMethod,
            final BlancoDbDynamicConditionStructure conditionStructure
    ) {
        BlancoDbMetaDataColumnStructure columnStructure = conditionStructure.getDbCoumn();
        BlancoCgParameter param = fCgFactory.createParameter(
                columnStructure.getName(),
                "BlancoDbDynamicParameter",
                "'" + columnStructure.getName() + "'列の値");
        cgMethod.getParameterList().add(param);
        param.getType().setGenerics(BlancoDbMappingUtilJava.getFullClassName(columnStructure));
        /*
         * 動的条件句は当面の間BINARY系の型には対応しません。
         */
        switch (columnStructure.getDataType()) {
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
            case Types.LONGVARCHAR:
            case Types.CLOB:
                throw new IllegalArgumentException("動的条件句は当面の間BINARY系の型には対応しません");
        }
    }

    /**
     * 動的条件句のTagを動的に変換するルーチンを生成します。
     * @param listLine
     * @param conditionStructureList
     */
    private void createTagConversion(
            final List<String> listLine,
            final List<BlancoDbDynamicConditionStructure> conditionStructureList
    ) {
        listLine.add("/* タグを置換する */");
        listLine.add("String query = this.getQuery();");
        for (BlancoDbDynamicConditionStructure conditionStructure : conditionStructureList) {
            listLine.add("query = BlancoDbUtil.createDynamicClause(fMapDynamicClause, " +
                    conditionStructure.getTag() + ", query");
        }
    }

    private void createStaticInput(
            final List<String> listLine,
            final BlancoDbMetaDataColumnStructure columnStructure
    ) {
        if (BlancoDbMappingUtilJava.getPrimitiveAndNullable(columnStructure)) {
            listLine.add("if (" + columnStructure.getName()
                    + " == null) {");

            // 以前、ここに過去のバージョン (1.6.4) のバグをエミュレートするためのコードがありました。
            // 現在は、この過去バグエミュレート機能は破棄されています。
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
                // SQL入力パラメータ値を番号つきでログ出力。
                // 番号は 1折人とします。
                String strLineInfo = "fLog.info(\"" + fSqlInfo.getName() +
                        ": SQL入力パラメータ \" + index + \": [\" + " +
                        columnStructure.getName() + " + \"]\");";
                listLine.add(strLineInfo);
                break;
        }
    }

    private void createDynamicInput(
            final List<String> listLine,
            final BlancoDbDynamicConditionStructure conditionStructure
    ) {
        String tag = conditionStructure.getTag();
        String type = conditionStructure.getType();
        listLine.add("if (" + tag + " != null) {");
        listLine.add("List<" + type + "> values = " + tag + ".getValues();");
        listLine.add("index = BlancoDbUtil.setInputParameter(fStatement, values, index);");
        listLine.add("}");

        listLine.add("");

        switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
                // SQL入力パラメータ値を番号つきでログ出力。
                // 番号は 1折人とします。
                String strLineInfo = "fLog.info(\"" + fSqlInfo.getName() +
                        ": SQL入力パラメータ \" + index + \": [\" + " +
                        conditionStructure.getTag() + " + \"]\");";
                listLine.add(strLineInfo);
                break;
        }
    }
}
