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
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractField;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

/**
 * The fLog field of the Query class.
 * 
 * @author IGA Tosiki
 */
public class LogSqlInParamField extends BlancoDbAbstractField {
    /**
     * A constructor for fLog field of Query class.
     * 
     * @param bindClassName
     *            The class name to be bound as a log object.
     * @author IGA Tosiki
     */
    public LogSqlInParamField(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgField cgField = fCgFactory.createField("fLogSqlInParam",
                "java.lang.String",
                "SQL input string for logging used internally by this class.");
        fCgClass.getFieldList().add(cgField);

        cgField.getLangDoc().getDescriptionList().add(
                "Stores SQL input parameters.");

        /*
         * For the purpose of making the generation gap design pattern available, the scope is set to protected.
         */
        cgField.setAccess("protected");
        cgField.setDefault("\"\"");
    }
}
