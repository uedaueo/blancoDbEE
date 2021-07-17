/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.iterator;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbSqlInfoScrollStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * A class for expanding individual methods.
 * 
 * @author Tosiki Iga
 */
public class GetListMethod extends BlancoDbAbstractMethod {
    public GetListMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getList",
                "Gets the search results in the form of a list.");
        fCgClass.getMethodList().add(cgMethod);

        // Gets the type name of a row object.
        final String rowObjectType = BlancoNameAdjuster.toClassName(fSqlInfo
                .getName()) + "Row";

        cgMethod.setReturn(fCgFactory.createReturn("java.util.List<"
                + rowObjectType + ">", fSqlInfo.getName()
                + "Class List, which will return an empty list if the search results are zero."));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        fCgSourceFile.getImportList().add("java.util.ArrayList");

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();
        listDesc.add("The list will contain the " + fSqlInfo.getName() + " class.<br>");
        listDesc.add("This can be used when the number of search results is known in advance and the number is small.<br>");
        listDesc.add("If you have a large number of search results, it is recommended that you do not use this method, but use the next() method instead.<br>");
        if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY) {
            listDesc.add("This QueryIterator is FORWARD_ONLY (forward cursor). If you know that you will be working with a large amount of data, avoid using this getList method as much as possible or regenerate the source code as a scrolling cursor.");
        } else {
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("absoluteStartPoint", "int",
                            "The line to start reading. Specify 1 if you want to read from the first line."));
        }

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("size", "int", "The number of lines to read."));

        final List<String> listLine = cgMethod.getLineList();

        listLine.add("List<" + rowObjectType + "> result = new ArrayList<"
                + rowObjectType + ">(8192);");
        if (fSqlInfo.getScroll() != BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY) {
            listLine.add("if (absolute(absoluteStartPoint) == false) {");
            listLine.add("return result;");
            listLine.add("}");
        }
        listLine.add("for (int count = 1; count <= size; count++) {");
        if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY) {
            listLine.add("if (next() == false) {");
            listLine.add("break;");
            listLine.add("}");
        } else {
            listLine.add("if (count != 1) {");
            listLine.add("if (next() == false) {");
            listLine.add("break;");
            listLine.add("}");
            listLine.add("}");
        }
        listLine.add("result.add(getRow());");
        listLine.add("}");
        listLine.add("return result;");
    }
}