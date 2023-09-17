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
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.db.common.expander.BlancoDbAbstractField;
import blanco.db.common.valueobject.BlancoDbDynamicConditionFunctionStructure;
import blanco.db.common.valueobject.BlancoDbDynamicConditionStructure;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * The fMapDynamicClause field of the Query class.
 *
 * @author tueda
 */
public class MapDynamicClauseField extends BlancoDbAbstractField {
    /**
     * A constructor for fMapDynamicClause field of Query class.
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
        plainTextList.add(""); // It will leave one line open for now.
        plainTextList.add("protected Map<String, BlancoDbDynamicClause> fMapDynamicClause = new HashMap<String, BlancoDbDynamicClause>() ");
        plainTextList.add("{");
        plainTextList.add("{");

        /*
         * The consistency of conditionStructure has been checked at parse time.
         */
        for (BlancoDbDynamicConditionStructure conditionStructure : conditions) {
            String strCondition = conditionStructure.getCondition();
            String strItem = conditionStructure.getItem();
            Boolean isFunctionTypeCondition = conditionStructure.getFunction() != null;
            if (isFunctionTypeCondition) {
                /* Always set true to doTest flag because of making inputParameters */
                BlancoDbDynamicConditionFunctionStructure functionStructure = conditionStructure.getFunction();
                functionStructure.setDoTest(true);
                /* Replace function tag with function literal */
                strItem = functionStructure.getFunction();
            }
            StringBuffer sb = new StringBuffer();
            sb.append("put(\"" +
                    conditionStructure.getKey() +
                    "\", new BlancoDbDynamicClause(\"" +
                    conditionStructure.getTag() +
                    "\", \"" + strCondition + "\"");
            sb.append(", \"" +
                    BlancoJavaSourceUtil.escapeStringAsJavaSource(strItem) +
                    "\"");
            if (!"ORDERBY".equals(strCondition) && !"LITERAL".equals(strCondition) && !"FUNCTION".equals(strCondition)) {
                sb.append(", \"" + conditionStructure.getLogical() + "\"");
                sb.append(", \"" + conditionStructure.getType() + "\"");
                fCgSourceFile.getImportList().add(conditionStructure.getType());
            }
            if ("COMPARE".equals(strCondition)) {
                sb.append(", \"" + conditionStructure.getComparison() + "\"");
            }
            if ("ORDERBY".equals(strCondition)) {
                sb.append(", false");
            } else if ("LITERAL".equals(strCondition) || "FUNCTION".equals(strCondition)) {
                sb.append(", true");
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
