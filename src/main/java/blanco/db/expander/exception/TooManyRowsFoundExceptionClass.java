/*
 * blancoDb Enterprise Edition
 * Copyright (C) 2004-2005 Tosiki Iga
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.exception;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Exception class to indicate that more than one row of data has been retrieved as a result of database processing.
 * 
 * The class generated by this class will be used in the source code generated by blancoDb.
 * 
 * @since 2005.05.12
 * @author IGA Tosiki
 */
public class TooManyRowsFoundExceptionClass {
    /**
     * Class name of the exception class
     */
    public static final String CLASS_NAME = "TooManyRowsFoundException";

    /**
     * JavaDoc description shown in the constructor
     */
    private static final String CONSTRUCTOR_JAVADOC = "Creates an instance of the exception class to indicate that more than one row of data has been retrieved as a result of database processing.";

    /**
     * A blancoCg object factory
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * The source code that contains this class
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    private String fPackage = null;

    public TooManyRowsFoundExceptionClass(
            final BlancoCgObjectFactory cgFactory, final String argPackage) {
        fCgFactory = cgFactory;
        fCgSourceFile = fCgFactory.createSourceFile(argPackage,
                "This code is generated by blanco Framework.");

        fPackage = argPackage;
    }

    public BlancoCgSourceFile expand() {
        final BlancoCgClass cgClass = fCgFactory.createClass(CLASS_NAME, null);
        fCgSourceFile.getClassList().add(cgClass);

        cgClass.getExtendClassList().add(
                fCgFactory.createType(fPackage + ".NotSingleRowException"));

        {
            final List<String> listDesc = cgClass.getLangDoc()
                    .getDescriptionList();

            listDesc.add("Exception class to indicate that more than one row of data has been retrieved as a result of database processing <br>");
            listDesc.add("This class is used in the source code generated by blancoDb <br>");
            listDesc.add("Note: This class will be used as a file after automatic source code generation.");
            listDesc.add("");
            listDesc.add("@since 2005.05.12");
            listDesc.add("@author blanco Framework");
        }

        {
            final BlancoCgField cgField = fCgFactory.createField(
                    "SQLSTATE_TOOMANYROWSFOUND", "java.lang.String", null);
            cgClass.getFieldList().add(cgField);
            cgField.setAccess("protected");
            cgField.setStatic(true);
            cgField.setFinal(true);
            cgField.setDefault("\"00111\"");
            cgField.getLangDoc().getDescriptionList().add(
                    "SQLState code that represents this class.<br>");
            cgField
                    .getLangDoc()
                    .getDescriptionList()
                    .add(
                            "Note: When using this class, do not rely on SQLState, but use the type of the exception class to determine the state.");
        }

        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(CLASS_NAME,
                    CONSTRUCTOR_JAVADOC);
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setConstructor(true);
            cgMethod.getLangDoc().getDescriptionList().add(
                    "@deprecated It is recommended to use a different constructor that can store the reason.");

            cgMethod
                    .getLineList()
                    .add(
                            "super(\"Too many rows found exception has occured.\", SQLSTATE_TOOMANYROWSFOUND);");
        }

        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(CLASS_NAME,
                    CONSTRUCTOR_JAVADOC);
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setConstructor(true);
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("reason", "java.lang.String",
                            "Description of the exception"));

            cgMethod.getLineList().add(
                    "super(reason, SQLSTATE_TOOMANYROWSFOUND);");
        }

        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(CLASS_NAME,
                    CONSTRUCTOR_JAVADOC);
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setConstructor(true);
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("reason", "java.lang.String",
                            "Description of the exception"));
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("SQLState", "java.lang.String",
                            "XOPEN code or SQL 99 code that identifies the exception"));

            cgMethod.getLineList().add("super(reason, SQLState);");
        }

        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(CLASS_NAME,
                    CONSTRUCTOR_JAVADOC);
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setConstructor(true);
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("reason", "java.lang.String",
                            "Description of the exception"));
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("SQLState", "java.lang.String",
                            "XOPEN code or SQL 99 code that identifies the exception"));
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("vendorCode", "int",
                            "Specific exception codes defined by the database vendor"));

            cgMethod.getLineList().add("super(reason, SQLState, vendorCode);");
        }

        return fCgSourceFile;
    }
}