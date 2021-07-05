/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.util;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.*;
import blanco.db.common.stringgroup.BlancoDbDriverNameStringGroup;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.expander.query.BlancoPerfomanceCommonUtil;

import java.util.List;

/**
 * blancoDbが共通的に利用するユーティリティクラス。
 *
 * このクラスが生成するクラスはblancoDbが生成したソースコードで利用されます
 *
 * @since 2006.03.02
 * @author IGA Tosiki
 */
public class BlancoDbUtilClassJava {
    /**
     * このクラス自身のクラス名
     */
    public static final String CLASS_NAME = "BlancoDbUtil";

    /**
     * blancoCg オブジェクトファクトリ。
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * このクラスが含まれるソースコード。
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    private BlancoDbSetting fDbSetting = null;

    public BlancoDbUtilClassJava(final BlancoCgObjectFactory cgFactory,
            final String argPackage, final BlancoDbSetting argDbSetting) {
        fCgFactory = cgFactory;
        fCgSourceFile = fCgFactory.createSourceFile(argPackage,
                "This code is generated by blanco Framework.");

        this.fDbSetting = argDbSetting;
    }

    public BlancoCgSourceFile expand() {
        final BlancoCgClass cgClass = fCgFactory.createClass(CLASS_NAME, null);
        fCgSourceFile.getClassList().add(cgClass);

        {
            final List<String> listDesc = cgClass.getLangDoc()
                    .getDescriptionList();

            listDesc.add("blancoDbが共通的に利用するユーティリティクラス。");
            listDesc.add("");
            listDesc.add("このクラスはblancoDbが生成したソースコードで利用されます <br>");
            listDesc
                    .add("このクラスは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。");
            listDesc.add("");
            listDesc.add("@since 2006.03.02");
            listDesc.add("@author blanco Framework");
        }

        {
            boolean fIsSQLServer = false;
            final boolean IS_DEBUG = false;

            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting)
                            + ".exception.IntegrityConstraintException");
            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting)
                            + ".exception.DeadlockException");
            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting)
                            + ".exception.TimeoutException");
            fCgSourceFile.getImportList().add("java.sql.SQLException");

            switch (fDbSetting.getDriverName()) {
            case BlancoDbDriverNameStringGroup.SQLSERVER_2000:
            case BlancoDbDriverNameStringGroup.SQLSERVER_2005:
                fIsSQLServer = true;
                if (IS_DEBUG) {
                    System.out.println("TRACE: SQL Serverです。");
                }
                break;
            default:
                if (IS_DEBUG) {
                    System.out.println("TRACE: 非SQL Serverです。ドライバ値("
                            + fDbSetting.getDriverName() + "]");
                }
                break;
            }

            {
                /* 動的条件句で使用する比較演算子記号をSQLに変換するためのMapを用意します。 */
                List<String> plainText = cgClass.getPlainTextList();
                plainText.add("");
                plainText.add("static final private Map<String, String> mapComparison = new HashMap<String, String>() {");
                plainText.add("{");
                plainText.add("put(\"EQ\", \"=\");");
                plainText.add("put(\"NE\", \"<>\");");
                plainText.add("put(\"GT\", \">\");");
                plainText.add("put(\"LT\", \"<\");");
                plainText.add("put(\"GE\", \">=\");");
                plainText.add("put(\"LE\", \"<=\");");
                plainText.add("put(\"LIKE\", \"LIKE\");");
                plainText.add("put(\"NOT LIKE\", \"NOT LIKE\");");
                plainText.add("}");
                plainText.add("};");
                fCgSourceFile.getImportList().add("java.util.Map");
                fCgSourceFile.getImportList().add("java.util.HashMap");
            }

            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    "convertToBlancoException", null);
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setStatic(true);

            final List<String> listDesc = cgMethod.getLangDoc()
                    .getDescriptionList();

            listDesc.add("SQL例外をblanco Framework例外オブジェクトに変換します。<br>");
            listDesc.add("");

            if (fIsSQLServer) {
                listDesc
                        .add("※Microsoft SQL Server 2000/2005用の判定を追加して生成されています。<br>");
            }
            listDesc
                    .add("SQL例外のなかで、blanco Frameworkの例外オブジェクトに変換すべきものについて変換します。<br>");
            listDesc.add("変換すべき先が無い場合には、そのまま元のオブジェクトを返却します。");

            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("ex", "java.sql.SQLException",
                            "JDBCから返却された例外オブジェクト。"));
            cgMethod
                    .setReturn(fCgFactory
                            .createReturn(
                                    "java.sql.SQLException",
                                    "変換後のSQL例外オブジェクト。SQLExceptionまたはその継承クラスである IntegrityConstraintException, DeadlockException, TimeoutExceptionが戻ります。"));

            final List<String> listLine = cgMethod.getLineList();

            listLine.add("if (ex.getSQLState() != null) {");
            listLine.add("if (ex.getSQLState().startsWith(\"23\")) {");
            listLine
                    .add("final IntegrityConstraintException exBlanco = new IntegrityConstraintException(\"データベース制約違反により変更が失敗しました。\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
            listLine.add("exBlanco.initCause(ex);");
            listLine.add("return exBlanco;");
            listLine.add("} else if (ex.getSQLState().equals(\"40001\")) {");
            listLine
                    .add("final DeadlockException exBlanco = new DeadlockException(\"データベースデッドロックにより変更が失敗しました。\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
            listLine.add("exBlanco.initCause(ex);");
            listLine.add("return exBlanco;");
            listLine.add("} else if (ex.getSQLState().equals(\"HYT00\")) {");
            listLine
                    .add("final TimeoutException exBlanco = new TimeoutException(\"データベースタイムアウトにより変更が失敗しました。\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
            listLine.add("exBlanco.initCause(ex);");
            listLine.add("return exBlanco;");

            if (fIsSQLServer) {
                // ロックタイムアウト固有の判定。
                // この処理は SQL Server 2000/2005においてのみ有効です。
                // SQL Server 2000/2005の場合にのみ、LockTimeoutExceptionが発生されます。
                fCgSourceFile.getImportList().add(
                        BlancoDbUtil.getRuntimePackage(fDbSetting)
                                + ".exception.LockTimeoutException");
                listLine
                        .add("} else if (ex.getSQLState().equals(\"HY000\") && ex.getErrorCode() == 1222) {");
                listLine.add("// SQL Server固有のロックタイムアウト例外コードの判定を行います。");
                listLine
                        .add("final LockTimeoutException exBlanco = new LockTimeoutException(\"データベースロックタイムアウトにより変更が失敗しました。\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
                listLine.add("exBlanco.initCause(ex);");
                listLine.add("return exBlanco;");
            }
            listLine.add("}");
            listLine.add("}");
            listLine.add("return ex;");
        }

        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    "convertTimestampToDate", "JDBCのTimestampをDate型に変換します。");
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setStatic(true);
            cgMethod.setFinal(true);
            cgMethod.getLangDoc().getDescriptionList().add(
                    "java.sql.Timestamp型からjava.util.Date型へと変換します。<br>");
            cgMethod.getLangDoc().getDescriptionList().add(
                    "このメソッドは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。");

            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("argTimestamp",
                            "java.sql.Timestamp", "JDBCのTimestamp型を与えます。"));
            cgMethod.setReturn(fCgFactory.createReturn("java.util.Date",
                    "変換後のjava.util.Date型を戻します。"));

            final List<String> listLine = cgMethod.getLineList();

            listLine.add("if (argTimestamp == null) {");
            listLine.add("return null;");
            listLine.add("}");
            listLine.add("return new Date(argTimestamp.getTime());");
        }

        {
            /*
             * 入力パラメータから動的条件句を自動生成します。
             */
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    "createDynamicClause", "入力パラメータから動的条件句を自動生成します。");
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setStatic(true);
            cgMethod.setFinal(true);
            cgMethod.getThrowList().add(
                    fCgFactory.createException(
                    "java.sql.SQLException",
                    "SQL例外を投げる可能性があります。")
            );

            cgMethod.getLangDoc().getDescriptionList().add(
                    "Excel定義書を元に生成されたMapと、実行時に与えられたパラメータから動的SQLを生成します。<br>");
            cgMethod.getLangDoc().getDescriptionList().add(
                    "このメソッドは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。");

            /* 第一引数：動的条件式定義のMapクラス */
            BlancoCgParameter paramMap = fCgFactory.createParameter("argMapClause", "java.util.Map", "動的条件式定義のMapを指定します。");
            cgMethod.getParameterList().add(paramMap);
            paramMap.getType().setGenerics("String, BlancoDbDynamicClause");
            /* BlancoDbDynamicClause は BlancoDbUtil と同じパッケージに置かれる前提だが、念のため import リストに入れておく。 */
            fCgSourceFile.getImportList().add(fCgSourceFile.getPackage() + ".BlancoDbDynamicClause");
            paramMap.setFinal(true);

            /* 第二引数：動的条件式のパラメータクラス */
            String dynParam = fCgSourceFile.getPackage() + ".BlancoDbDynamicParameter";
            BlancoCgParameter paramParam = fCgFactory.createParameter("argParameter", dynParam, "動的条件式を選択するためのパラメータを指定します。");
            cgMethod.getParameterList().add(paramParam);
            paramParam.getType().setGenerics("T");
            /* BlancoDbDynamicParameter は BlancoDbUtil と同じパッケージに置かれる前提だが、念のため import リストに入れておく。 */
            fCgSourceFile.getImportList().add(dynParam);
            paramParam.setFinal(true);

            /* 第三引数：動的条件式のパラメータクラス */
            BlancoCgParameter paramQuery = fCgFactory.createParameter("argQuery", "java.lang.String", "動的条件式を選択するためのパラメータを指定します。");
            cgMethod.getParameterList().add(paramQuery);
            paramQuery.setFinal(true);

            /* 第四引数：パラメータとして null が渡された場合に削除するtag */
            BlancoCgParameter paramExpectedTag = fCgFactory.createParameter("argExpectedTag", "java.lang.String", "パラメータとして null が渡された場合に削除するtagを指定します。");
            cgMethod.getParameterList().add(paramExpectedTag);
            paramQuery.setFinal(true);

            /* 戻り値の定義 */
            cgMethod.setReturn(fCgFactory.createReturn("java.lang.String",
                    "Tag置換後のqueryを戻します。"));

            // define virtual parameter generic for LangDoc.
            BlancoCgVirtualParameter cgVirtualParameter = fCgFactory.createVirtualParameter("typeT", "T", "Virtual parameter for BlancoDbDynamicParameter.");
            cgMethod.getVirtualParameterList().add(cgVirtualParameter);
//            cgMethod.setVirtualParameterDefinition("<T>");

            /* メソッド本文 */
            final List<String> listLine = cgMethod.getLineList();

            listLine.add("String query = argQuery;");
            listLine.add("if (argParameter != null) {");
            listLine.add("String key = argParameter.getKey();");
            listLine.add("");
            listLine.add("List<T> values = argParameter.getValues();");
            listLine.add("if (key != null) {");
            listLine.add("BlancoDbDynamicClause dynamicClause = argMapClause.get(key);");
            listLine.add("if (dynamicClause != null) {"); //80
            listLine.add("/* 動的条件句Mapは自動生成されるので、不正な値は存在しない前提とする */");
            listLine.add("StringBuffer sb = new StringBuffer();");
            listLine.add("String tag = dynamicClause.getTag();");
            listLine.add("String condition = dynamicClause.getCondition();");
            listLine.add("");
            listLine.add("if (\"LITERAL\".equals(condition)) {");
            listLine.add("if (values != null && values.size() == 1) {");
            listLine.add("BlancoDbDynamicLiteral literal = (BlancoDbDynamicLiteral) values.get(0);");
            listLine.add("if (literal != null && !literal.getInvalid() && dynamicClause.getItems() != null) {");
            listLine.add("sb.append(\" \");");
            listLine.add("int count = 0;");
            listLine.add("for (String item : dynamicClause.getItems()) {");
            listLine.add("if (count > 0) {");
            listLine.add("sb.append(\",\");");
            listLine.add("}");
            listLine.add("sb.append(item);");
            listLine.add("count++;");
            listLine.add("}");
            listLine.add("sb.append(\" \");");
            listLine.add("}");
            listLine.add("}");
            listLine.add("} else if (\"FUNCTION\".equals(condition)) {");
            listLine.add("if (values != null && values.size() == 1) {");
            listLine.add("sb.append(\" \");");
            listLine.add("sb.append(dynamicClause.getItems().get(0));");
            listLine.add("sb.append(\" \");");
            listLine.add("}");
            listLine.add("} else if (\"ORDERBY\".equals(condition)) {");
            listLine.add("if (values != null && values.size() > 0) {");
            listLine.add("sb.append(\"ORDER BY \");");
            listLine.add("int count = 0;");
            listLine.add("for (T value : values) {");
            listLine.add("BlancoDbDynamicOrderBy orderBy = (BlancoDbDynamicOrderBy) value;");
            listLine.add("if (count > 0) {");
            listLine.add("sb.append(\", \");");
            listLine.add("}");
            listLine.add("String column = dynamicClause.getItem(orderBy.getColumn());");
            listLine.add("if (column == null) {");
            listLine.add("throw new SQLException(\"入力に指定された[ \" + orderBy.getColumn() + \" ]は定義書で未定義です。\", \"42S22\", 9999);");
            listLine.add("}");
            listLine.add("sb.append(column);");
            listLine.add("if (!\"ASC\".equals(orderBy.getOrder())) {");
            listLine.add("sb.append(\" DESC\");");
            listLine.add("} else {");
            listLine.add("sb.append(\" ASC\");");
            listLine.add("}");
            listLine.add("count++;");
            listLine.add("}");
            listLine.add("}");
            listLine.add("} else if (\"BETWEEN\".equals(condition)) {");
            listLine.add("if (values != null && values.size() == 2) {");
            listLine.add("sb.append(\" \" + dynamicClause.getLogical() + \" ( \" + dynamicClause.getItems().get(0) + \" BETWEEN ? AND ? )\");");
            listLine.add("}"); // 100
            listLine.add("} else if (\"NOT BETWEEN\".equals(condition)) {");
            listLine.add("if (values != null && values.size() == 2) {");
            listLine.add("sb.append(\" \" + dynamicClause.getLogical() + \" ( \" + dynamicClause.getItems().get(0) + \" NOT BETWEEN ? AND ? )\");");
            listLine.add("}"); // 100
            listLine.add("} else if (\"IN\".equals(condition)) {");
            listLine.add("if (values != null && values.size() > 0) {");
            listLine.add("sb.append(\" \" + dynamicClause.getLogical() + \" ( \" + dynamicClause.getItems().get(0) + \" IN ( \");");
            listLine.add("int count = 0;");
            listLine.add("for (T value : values) {");
            listLine.add("if (count > 0) {");
            listLine.add("sb.append(\", \");");
            listLine.add("}");
            listLine.add("sb.append(\"?\");");
            listLine.add("count++;"); // 110
            listLine.add("}");
            listLine.add("sb.append(\" )\");");
            listLine.add("sb.append(\" )\");");
            listLine.add("}");
            listLine.add("} else if (\"NOT IN\".equals(condition)) {");
            listLine.add("if (values != null && values.size() > 0) {");
            listLine.add("sb.append(\" \" + dynamicClause.getLogical() + \" ( \" + dynamicClause.getItems().get(0) + \" NOT IN ( \");");
            listLine.add("int count = 0;");
            listLine.add("for (T value : values) {");
            listLine.add("if (count > 0) {");
            listLine.add("sb.append(\", \");");
            listLine.add("}");
            listLine.add("sb.append(\"?\");");
            listLine.add("count++;"); // 110
            listLine.add("}");
            listLine.add("sb.append(\" )\");");
            listLine.add("sb.append(\" )\");");
            listLine.add("}");
            listLine.add("} else if (\"COMPARE\".equals(condition)) {");
            listLine.add("if (values != null && values.size() > 0) {");
            listLine.add("String logicalOperator = \"OR\";");
            listLine.add("if (argParameter.getLogicalOperator() != null && argParameter.getLogicalOperator().equalsIgnoreCase(\"AND\")) {");
            listLine.add("logicalOperator = \"AND\";");
            listLine.add("}");
            listLine.add("sb.append(\" \" + dynamicClause.getLogical() + \" ( \");");
            listLine.add("for (int count = 0; count < values.size(); count++) {");
            listLine.add("if (count > 0) {");
            listLine.add("sb.append(\" \" + logicalOperator + \" \");");
            listLine.add("}");
            listLine.add("sb.append(dynamicClause.getItems().get(0) + \" \" + mapComparison.get(dynamicClause.getComparison()) + \" ?\");");
            listLine.add("}");
            listLine.add("sb.append(\" )\");");
            listLine.add("}");
            listLine.add("}");
            listLine.add("query = argQuery.replace(\"${\" + tag + \"}\", sb.toString());"); // 120
            listLine.add("}");
            listLine.add("}");
            listLine.add("} else {");
            listLine.add("query = argQuery.replace(\"${\" + argExpectedTag + \"}\", \"\");");
            listLine.add("}");
            listLine.add("return query;");
        }

        {
            /*
             * 動的SQLのためのInputパラメータ関数を生成します
             */
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    "setInputParameter", "動的SQLのためのInputパラメータ関数です。");
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setStatic(true);
            cgMethod.setFinal(true);
            cgMethod.getLangDoc().getDescriptionList().add(
                    "実行時に与えられたパラメータを動的に生成された条件句に適用します。<br>");
            cgMethod.getLangDoc().getDescriptionList().add(
                    "このメソッドは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。");

            /* 第一引数： preparedStatement */
            BlancoCgParameter paramStatement = fCgFactory.createParameter("argStatement", "java.sql.PreparedStatement", "動的に定義されたpreparedStatementです。");
            cgMethod.getParameterList().add(paramStatement);
            paramStatement.setFinal(true);

            /* 第二引数： パラメータのリスト */
            BlancoCgParameter paramList = fCgFactory.createParameter("values", "java.util.List", "動的に定義されたpreparedStatementです。");
            cgMethod.getParameterList().add(paramList);
            paramList.getType().setGenerics("T");
            paramList.setFinal(true);

            /* 第三引数： startIndex */
            BlancoCgParameter paramIndex = fCgFactory.createParameter("startIndex", "java.lang.Integer", "パラメータの開始indexです。");
            cgMethod.getParameterList().add(paramIndex);
            paramIndex.setFinal(true);

            /* 戻り値 */
            cgMethod.setReturn(fCgFactory.createReturn("int",
                    "Tag置換後のqueryを戻します。"));

            /* 例外 */
            BlancoCgType sqlExpType = new BlancoCgType();
            sqlExpType.setName("java.sql.SQLException");
            /* expnadするときにtrimされるかもしれないので、念のため import リストに入れておく。 */
            fCgSourceFile.getImportList().add("java.sql.SQLException");
            BlancoCgException sqlExp = new BlancoCgException();
            sqlExp.setType(sqlExpType);
            sqlExp.setDescription("SQLException may be thrown.");
            cgMethod.getThrowList().add(sqlExp);

            // Description for virtual parameter generics.
            BlancoCgVirtualParameter cgVirtualParameter = fCgFactory.createVirtualParameter("typeT", "T", "Virtual parameter for BlancoDbDynamicParameter.");
            cgMethod.getVirtualParameterList().add(cgVirtualParameter);

            /* メソッドの本体 */
            final List<String> listLine = cgMethod.getLineList();
            listLine.add("int index = startIndex;");
            listLine.add("for (T value : values) {");
            listLine.add("/*");
            listLine.add("* NULL の場合の面倒も見てくれそうだが、");
            listLine.add("* Database エンジンによってはエラーになるかもしれない。");
            listLine.add("* e.g. SQLServer は NG, MySQL connector は OK.");
            listLine.add("*/");
            listLine.add("argStatement.setObject(index, value);");
            listLine.add("index++;");
            listLine.add("}");
            listLine.add("return index;");
        }

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
            case BlancoDbLoggingModeStringGroup.SQLID:
                BlancoPerfomanceCommonUtil.addPerfomanceFieldMethod(fCgFactory,
                        fCgSourceFile, cgClass);
                break;
            }
        }

        return fCgSourceFile;
    }
}
