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

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.db.common.expander.BlancoDbAbstractClass;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.expander.query.Finalize;
import blanco.db.expander.query.GetQueryMethod;
import blanco.db.expander.query.GetStatementMethod;
import blanco.db.expander.query.PrepareStatementMethod;
import blanco.db.expander.query.PrepareStatementMethod2;
import blanco.db.expander.query.QueryConstructor;
import blanco.db.expander.query.field.*;
import blanco.db.expander.query.iterator.SetInputParameterMethod;
import blanco.db.resourcebundle.BlancoDbResourceBundle;

/**
 * 個別のクラスを展開するためのクラス。
 *
 * @author Yasuo Nakanishi
 */
public class QueryInvokerClass extends BlancoDbAbstractClass {
    private final BlancoDbResourceBundle fBundle = new BlancoDbResourceBundle();

    public QueryInvokerClass(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory) {
        super(argDbSetting, argSqlInfo, argCgFactory);
    }

    public BlancoCgSourceFile expand() {
        final String className = BlancoNameAdjuster.toClassName(fSqlInfo
                .getName()) + "Invoker";

        fCgSourceFile = fCgFactory.createSourceFile(
                BlancoDbUtil.getBasePackage(fSqlInfo, fDbSetting) + ".query",
                "This code is generated by blanco Framework.");
        fCgClass = fCgFactory.createClass(className, "[" + fSqlInfo.getName()
                + "] " + fSqlInfo.getDescription() + " (QueryInvoker)");
        fCgSourceFile.getClassList().add(fCgClass);

        if (fDbSetting.getUseRuntime()) {
            // (2013/01/08 一旦登録解除) fCgClass.getExtendClassList().add(fCgFactory.createType("java.io.Closeable"));
            fCgClass.getImplementInterfaceList().add(fCgFactory.createType("blanco.db.runtime.BlancoDbQuery"));

            // アノテーションを付与します。
            fCgClass.getAnnotationList().add("BlancoGeneratedBy(name = \"blancoDb\")");
            fCgSourceFile.getImportList().add("blanco.fw.BlancoGeneratedBy");
        }

        fCgClass.getLangDoc().getDescriptionList()
                .add("実行型SQL文をラッピングして各種アクセサを提供します。<br>");
        if (fSqlInfo.getSingle()) {
            fCgClass.getLangDoc().getDescriptionList()
                    .add("シングル属性: 有効 (期待する処理件数は1件)<br>");
        }

        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting)
                        + ".exception.IntegrityConstraintException");

        // BlancoDbUtilは常にインポートします。
        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting)
                        + ".util.BlancoDbUtil");

        /*
         * DynamicClauseが定義されている場合はインポートします。
         */
        if (fSqlInfo.getDynamicConditionList().size() > 0) {
            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util.BlancoDbDynamicClause"
            );
            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util.BlancoDbDynamicParameter"
            );
        }

        new MapDynamicClauseField(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile, fCgClass).expand();
        new ConnectionField(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile,
                fCgClass).expand();
        new StatementField(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile,
                fCgClass, false).expand();

        new QueryConstructor(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile,
                fCgClass).expand();
        new GetQueryMethod(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile,
                fCgClass).expand();

        new PrepareStatementMethod(fDbSetting, fSqlInfo, fCgFactory,
                fCgSourceFile, fCgClass).expand();
        // t.iga
        new PrepareStatementMethod2(fDbSetting, fSqlInfo, fCgFactory,
                fCgSourceFile, fCgClass).expand();

        // 入力パラメータがある場合にのみbindします。
        if (fSqlInfo.getInParameterList().size() > 0 ||
                fSqlInfo.getDynamicConditionList().size() > 0) {
            new SetInputParameterMethod(fDbSetting, fSqlInfo, fCgFactory,
                    fCgSourceFile, fCgClass, false).expand();
        }
        new ExecuteUpdateMethod(fDbSetting, fSqlInfo, fCgFactory,
                fCgSourceFile, fCgClass).expand();
        if (fSqlInfo.getSingle()) {
            new ExecuteSingleUpdateMethod(fDbSetting, fSqlInfo, fCgFactory,
                    fCgSourceFile, fCgClass).expand();
        }

        if (fBundle.getExpanderDisableGetStatement().equals("true") == false) {
            // 1.6.8以前と互換性を持たせる必要がある場合にのみ getStatementを生成しません。
            new GetStatementMethod(fDbSetting, fSqlInfo, fCgFactory,
                    fCgSourceFile, fCgClass, false).expand();
        }

        new CloseMethod(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile,
                fCgClass).expand();

        new Finalize(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile, fCgClass)
                .expand();

		if (fDbSetting.getLoggingsql()) {
			// 標準出力に出力。
			new LogSqlInParamField(fDbSetting, fSqlInfo, fCgFactory,
					fCgSourceFile, fCgClass).expand();
			if (fSqlInfo.getDynamicSql()) {
				new LogSqlDynamicSqlField(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile, fCgClass).expand();
			}
		}

        if (fDbSetting.getLogging()) {
            fCgSourceFile.getImportList().add(
                    "org.apache.commons.logging.LogFactory");
            new LogField(fDbSetting, fSqlInfo, fCgFactory, fCgSourceFile,
                    fCgClass).expand();
        }

        return fCgSourceFile;
    }
}
