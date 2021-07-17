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
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

/**
 * A class for expanding individual methods.
 * 
 * @author Yasuo Nakanishi
 */
public class QueryConstructor extends BlancoDbAbstractMethod {
    public QueryConstructor(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    fCgClass.getName(), fCgClass.getName() + "Constructor for the class.");
            fCgClass.getMethodList().add(cgMethod);

            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("conn",
                            "java.sql.Connection", "Database connection"));

            cgMethod.getLangDoc().getDescriptionList()
                    .add("Creates a query class with a database connection object as an argument.<br>");
            cgMethod.getLangDoc().getDescriptionList()
                    .add("After using this class, you must call the close() method.<br>");

            cgMethod.setConstructor(true);

            final List<String> listLine = cgMethod.getLineList();
            listLine.add("fConnection = conn;");
        }

        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    fCgClass.getName(), fCgClass.getName() + "Constructor for the class.");
            fCgClass.getMethodList().add(cgMethod);

            cgMethod.getLangDoc().getDescriptionList()
                    .add("Creates a query class without giving a database connection object.<br>");
            cgMethod.getAnnotationList().add("Deprecated");

            cgMethod.setConstructor(true);
        }
        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    "setConnection", fCgClass.getName() + "Sets a database connection to the class.");
            fCgClass.getMethodList().add(cgMethod);

            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("conn",
                            "java.sql.Connection", "Database connection"));

            cgMethod.getAnnotationList().add("Deprecated");

            final List<String> listLine = cgMethod.getLineList();
            listLine.add("fConnection = conn;");
        }
    }
}