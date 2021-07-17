/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;

public class BlancoPerfomanceCommonUtil {
    public static void addPerfomanceFieldMethod(
            final BlancoCgObjectFactory cgFactory,
            final BlancoCgSourceFile cgSourceFile, final BlancoCgClass cgClass) {
        {
            final BlancoCgField cgField = cgFactory.createField(
                    "fPerfomanceNumberFormat", "java.text.NumberFormat",
                    "A numeric formatter used only for performance measurements.");
            cgClass.getFieldList().add(cgField);
            cgField.setFinal(true);
            cgField.setStatic(true);
            cgField.setDefault("NumberFormat.getInstance()");
        }

        {
            final BlancoCgMethod cgMethod = cgFactory.createMethod(
                    "getUsedMemory", "Method to get memory consumption used only for performance measurements.");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStatic(true);
            cgMethod.getParameterList().add(
                    cgFactory.createParameter("runtime", "java.lang.Runtime",
                            "An instance of runtime."));
            cgMethod.setReturn(cgFactory.createReturn("long", "Memory consumption."));
            cgMethod.getLineList().add(
                    "return runtime.totalMemory() - runtime.freeMemory();");
        }

        {
            final BlancoCgMethod cgMethod = cgFactory.createMethod(
                    "getMemorySizeString", "Method to get memory size string used only for performance measurements.");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStatic(true);
            cgMethod.getParameterList().add(
                    cgFactory.createParameter("memorySize", "long", "Memory size."));
            cgMethod.setReturn(cgFactory.createReturn("java.lang.String",
                    "String representation of memory size."));
            cgMethod.getLineList().add(
                    "final StringBuffer result = new StringBuffer();");
            cgMethod.getLineList().add(
                    "synchronized (fPerfomanceNumberFormat) {");
            cgMethod
                    .getLineList()
                    .add(
                            "result.append(fPerfomanceNumberFormat.format(memorySize / 1024));");
            cgMethod.getLineList().add("}");
            cgMethod.getLineList().add("result.append(\"(KB)\");");
            cgMethod.getLineList().add("return result.toString();");
        }

        {
            final BlancoCgMethod cgMethod = cgFactory.createMethod(
                    "getTimeString", "Method to get the consumption millisecond string used only for performance measurements.");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStatic(true);
            cgMethod.getParameterList().add(
                    cgFactory.createParameter("time", "long", "Consumption milliseconds."));
            cgMethod.setReturn(cgFactory.createReturn("java.lang.String",
                    "String representation of consumption milliseconds."));
            cgMethod.getLineList().add(
                    "final StringBuffer result = new StringBuffer();");
            cgMethod.getLineList().add("result.append(time);");
            cgMethod.getLineList().add("result.append(\"(ms)\");");
            cgMethod.getLineList().add("return result.toString();");
        }
    }
}
