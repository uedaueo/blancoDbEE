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
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.expander.query.BlancoPerfomanceCommonUtil;

import java.util.List;

/**
 * blancoDbが共通的に利用するユーティリティクラス。
 *
 * このクラスが生成するクラスはblancoDbが生成したソースコードで利用されます
 *
 * @since 2020.09.04
 * @author tueda
 */
public class BlancoDbDynamicClauseClassJava {
    /**
     * このクラス自身のクラス名
     */
    public static final String CLASS_NAME = "BlancoDbDynamicClause";

    /**
     * blancoCg オブジェクトファクトリ。
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * このクラスが含まれるソースコード。
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    private BlancoDbSetting fDbSetting = null;

    public BlancoDbDynamicClauseClassJava(final BlancoCgObjectFactory cgFactory,
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

            listDesc.add("動的条件句を定義するクラス。");
            listDesc.add("");
            listDesc.add("このクラスはblancoDbが生成したソースコードで利用されます <br>");
            listDesc
                    .add("このクラスは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。");
            listDesc.add("");
            listDesc.add("@since 2020.09.01");
            listDesc.add("@author blanco Framework");
        }

        {
            /* fields */
            cgClass.getFieldList().add(
                    buildField("tag", "タグ名")
            );
            cgClass.getFieldList().add(
                    buildField("condition", "条件句タイプ")
            );
            cgClass.getFieldList().add(
                    buildField("item", "対象Item")
            );
            cgClass.getFieldList().add(
                    buildField("comparison", "比較演算子")
            );
            cgClass.getFieldList().add(
                    buildField("logical", "論理演算子（先導）")
            );
            cgClass.getFieldList().add(
                    buildField("type", "値の型")
            );
        }

        {
            /* constructors */
            cgClass.getMethodList().add(
                    buildConstructor("DEFAULT")
            );
            cgClass.getMethodList().add(
                    buildConstructor("ITEMONLY")
            );
            cgClass.getMethodList().add(
                    buildConstructor("NOTCOMPARE")
            );
            cgClass.getMethodList().add(
                    buildConstructor("COMPARE")
            );
        }

        {
            /* Getter/Setter */
            cgClass.getMethodList().add(
                    buildMethodSet("tag", "タグ名")
            );
            cgClass.getMethodList().add(
                    buildMethodGet("tag", "タグ名")
            );

            cgClass.getMethodList().add(
                    buildMethodSet("condition", "条件句タイプ")
            );
            cgClass.getMethodList().add(
                    buildMethodGet("condition", "条件句タイプ")
            );

            cgClass.getMethodList().add(
                    buildMethodSet("item", "対象Item")
            );
            cgClass.getMethodList().add(
                    buildMethodGet("item", "対象Item")
            );

            cgClass.getMethodList().add(
                    buildMethodSet("comparison", "比較演算子")
            );
            cgClass.getMethodList().add(
                    buildMethodGet("comparison", "比較演算子")
            );

            cgClass.getMethodList().add(
                    buildMethodSet("logical", "論理演算子（先導）")
            );
            cgClass.getMethodList().add(
                    buildMethodGet("logical", "論理演算子（先導）")
            );

            cgClass.getMethodList().add(
                    buildMethodSet("type", "値の型")
            );
            cgClass.getMethodList().add(
                    buildMethodGet("type", "値の型")
            );
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

    private BlancoCgField buildField(
            final String name,
            final String desc
    ) {
        final BlancoCgField cgField = fCgFactory.createField(name, "java.lang.String", desc);
        cgField.setDefault("null");
        cgField.setAccess("private");
        return cgField;
    }

    private BlancoCgMethod buildConstructor(String type) {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(CLASS_NAME, "コンストラクタ");
        cgMethod.setConstructor(true);

        if ("DEFAULT".equals(type)) {
            return cgMethod;
        }

        final List<String> lineList = cgMethod.getLineList();

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("argTag", "java.lang.String", "タグ名"));
        lineList.add("this.tag = argTag;");

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("argCondition", "java.lang.String", "条件句タイプ"));
        lineList.add("this.condition = argCondition;");

        if ("ITEMONLY".equals(type)) {
            return cgMethod;
        }

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("argItem", "java.lang.String", "対象Item"));
        lineList.add("this.item = argItem;");

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("argLogical", "java.lang.String", "論理演算子（先導）"));
        lineList.add("this.logical = argLogical;");

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("argType", "java.lang.String", "値の型"));
        lineList.add("this.type = argType;");

        if (!"COMPARE".equals(type)) {
            return cgMethod;
        }

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("argComparison", "java.lang.String", "比較演算子"));
        lineList.add("this.comparison = argComparison;");

        return cgMethod;
    }

    private BlancoCgMethod buildMethodSet(
            final String name,
            final String desc) {
        // おのおののフィールドに対するセッターメソッドを生成します。
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("set" + BlancoNameAdjuster.toClassName(name), desc);

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("arg" + BlancoNameAdjuster.toClassName(name),
                        "java.lang.String", desc)
        );

        // メソッドの実装
        cgMethod.getLineList().add(
                "this." + name + " = " + "arg" + BlancoNameAdjuster.toClassName(name) + ";"
        );
        return cgMethod;
    }

    private BlancoCgMethod buildMethodGet(
            final String name,
            final String desc) {
        // おのおののフィールドに対するゲッターメソッドを生成します。
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("get" + BlancoNameAdjuster.toClassName(name), desc);

        cgMethod.setReturn(fCgFactory.createReturn("java.lang.String", desc));

        // メソッドの実装
        cgMethod.getLineList().add(
                "return this." + name + ";");

        return cgMethod;
    }
}