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
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.util.BlancoDbQueryParserUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

/**
 * A class for expanding individual methods.
 *
 * @author Yasuo Nakanishi
 */
public class GetQueryMethod extends BlancoDbAbstractMethod {
    public GetQueryMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getQuery",
                "Gets the SQL statement given in the SQL definition document.");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod.setReturn(fCgFactory.createReturn("java.lang.String",
                "SQL statement in the state that can be given to the JDBC driver and executed."));

        cgMethod
                .getLangDoc()
                .getDescriptionList()
                .add(
                        "If the # keyword is specified as the SQL input parameter, the SQL statement after replacing the corresponding part with ? can be obtained.");

        final List<String> listLine = cgMethod.getLineList();

        // 2005.04.15 t.iga Changed to output newlines as newlines.
        // 2005.10.12 t.iga Changed to use the blancoCommons conversion utility.
        final String escapedQuery = BlancoJavaSourceUtil
                .escapeStringAsJavaSource(fSqlInfo.getQuery());

        // Conversion of "#" parameter of query to "?".
        final String actualSql = new BlancoDbQueryParserUtil(fSqlInfo)
                .getNaturalSqlStringOnlyStatic(escapedQuery);

        listLine.add("return \"" + actualSql + "\";");
    }
}
