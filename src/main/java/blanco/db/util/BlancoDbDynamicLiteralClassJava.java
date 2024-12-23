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
import blanco.commons.util.BlancoNameAdjuster;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.expander.query.BlancoPerfomanceCommonUtil;

import java.util.List;

/**
 * A utility class commonly used by blancoDb.
 *
 * The classes generated by this class will be used in the source code generated by blancoDb.
 *
 * @since 2021.05.19
 * @author tueda
 */
public class BlancoDbDynamicLiteralClassJava {
    /**
     * Class name of this class itself
     */
    public static final String CLASS_NAME = "BlancoDbDynamicLiteral";

    /**
     * A blancoCg object factory
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * The source code that contains this class
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    private BlancoDbSetting fDbSetting = null;

    public BlancoDbDynamicLiteralClassJava(final BlancoCgObjectFactory cgFactory,
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

            listDesc.add("A class that defines dynamically generated literals.");
            listDesc.add("");
            listDesc.add("This class is used in the source code generated by blancoDb.<br>");
            listDesc.add("");
            listDesc.add("@since 2021.05.19");
            listDesc.add("@author blanco Framework");
        }

        {
            // constructors
            cgClass.getMethodList().add(
                    buildConstructor("DEFAULT")
            );
            cgClass.getMethodList().add(
                    buildConstructor("LITERAL")
            );
        }

        {
            /* fields */
            BlancoCgField cgField = buildField("invalid", "Disables the set literal", "java.lang.Boolean", null);
            cgField.setDefault("false");
            cgClass.getFieldList().add(cgField);
        }

        {
            /* Getter/Setter */
            cgClass.getMethodList().add(
                    buildMethodSet("invalid", "Disables the set literal", "java.lang.Boolean", null)
            );
            cgClass.getMethodList().add(
                    buildMethodGet("invalid", "Disables the set literal", "java.lang.Boolean", null)
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
            final String desc,
            final String type,
            final String generic
    ) {
        final BlancoCgField cgField = fCgFactory.createField(name, type, desc);
        cgField.setAccess("private");
        if (generic != null && generic.length() > 0) {
            cgField.getType().setGenerics(generic);
        }
        return cgField;
    }

    private BlancoCgMethod buildMethodSet(
            final String name,
            final String desc,
            final String type,
            final String generic
    ) {
        // Generates a setter method for each field.
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("set" + BlancoNameAdjuster.toClassName(name), desc);

        BlancoCgParameter parameter = fCgFactory.createParameter("arg" + BlancoNameAdjuster.toClassName(name), type, desc);
        if (generic != null && generic.length() > 0) {
            parameter.getType().setGenerics(generic);
        }
        cgMethod.getParameterList().add(parameter);

        // method implementation
        cgMethod.getLineList().add(
                "this." + name + " = " + "arg" + BlancoNameAdjuster.toClassName(name) + ";"
        );
        return cgMethod;
    }

    private BlancoCgMethod buildMethodGet(
            final String name,
            final String desc,
            final String type,
            final String generic
    ) {
        // Generates a getter method for each field.
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("get" + BlancoNameAdjuster.toClassName(name), desc);

        cgMethod.setReturn(fCgFactory.createReturn(type, desc));
        if (generic != null && generic.length() > 0) {
            cgMethod.getReturn().getType().setGenerics(generic);
        }

        // method implementation
        cgMethod.getLineList().add(
                "return this." + name + ";");

        return cgMethod;
    }

    private BlancoCgMethod buildConstructor(String type) {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(CLASS_NAME, "constructor");
        cgMethod.setConstructor(true);

        if ("DEFAULT".equals(type)) {
            return cgMethod;
        }

        final List<String> lineList = cgMethod.getLineList();

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("argInvalid", "java.lang.Boolean", "Disables the set literal"));
        lineList.add("this.invalid = argInvalid;");

        return cgMethod;
    }
}
