/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.field;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractField;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbDynamicConditionStructure;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * QueryクラスのfMapDynamicClauseフィールドです。
 *
 * @author tueda
 */
public class MapDynamicClauseField extends BlancoDbAbstractField {
    /**
     * QueryクラスのfMapDynamicClauseフィールドのコンストラクタです。
     *
     * @author IGA Tosiki
     */
    public MapDynamicClauseField(final BlancoDbSetting argDbSetting,
                                 final BlancoDbSqlInfoStructure argSqlInfo,
                                 final BlancoCgObjectFactory argCgFactory,
                                 final BlancoCgSourceFile argCgSourceFile,
                                 final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {

        List<BlancoDbDynamicConditionStructure> conditions = fSqlInfo.getDynamicConditionList();

        if (fDbSetting.getVerbose()) {
            System.out.println("Dynamic!!! " + conditions.size());
        }

        if (conditions == null || conditions.size() == 0) {
            if (fDbSetting.getVerbose()) {
                System.out.println("No conditions. Skip to generate Dynamic conditions mapping.");
            }
            return;
        }

        List<String> plainTextList = new ArrayList<>();
        plainTextList.add(""); // とりあえず１行あけておく。
        plainTextList.add("protected Map<String, BlancoDbDynamicClause> fMapDynamicClause = new HashMap<String, BlancoDbDynamicClause>() ");
        plainTextList.add("{");
        plainTextList.add("{");

        /*
         * conditionStructure の整合性は parse 時にチェック済み
         */
        for (BlancoDbDynamicConditionStructure conditionStructure : conditions) {
            StringBuffer sb = new StringBuffer();
            sb.append("put(\"" +
                    conditionStructure.getKey() +
                    "\", new BlancoDbDynamicClause(\"" +
                    conditionStructure.getTag() +
                    "\", \"" + conditionStructure.getCondition() + "\"");
            if (!"ITEMONLY".equals(conditionStructure.getCondition())) {
                sb.append(", \"" + conditionStructure.getItem() + "\"");
                sb.append(", \"" + conditionStructure.getLogical() + "\"");
                sb.append(", \"" + conditionStructure.getType() + "\"");
                fCgSourceFile.getImportList().add(conditionStructure.getType());
            }
            if ("COMPARE".equals(conditionStructure.getCondition())) {
                sb.append(", \"" + conditionStructure.getComparison() + "\"");
            }
            sb.append("));");
            plainTextList.add(sb.toString());
        }

        plainTextList.add("}");
        plainTextList.add("};");

        fCgClass.getPlainTextList().addAll(plainTextList);
        fCgSourceFile.getImportList().add("java.util.Map");
        fCgSourceFile.getImportList().add("java.util.HashMap");
    }
}
