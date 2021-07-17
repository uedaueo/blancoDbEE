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
 * The fStatement field of the Query class.
 * 
 * @author IGA Tosiki
 */
public class StatementField extends BlancoDbAbstractField {
    private boolean fIsCallableStatement = false;

    /**
     * A constructor for the fStatement field of the Query class.
     * 
     * @param className
     *            The actual class name of the statement. It may be java.sql.PreparedStatement class or java.sql.CallableStatement class.
     * @author IGA Tosiki
     */
    public StatementField(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass, final boolean argIsCallableStatement) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
        fIsCallableStatement = argIsCallableStatement;
    }

    public void expand() {
        String statementClassName = "java.sql.PreparedStatement";
        if (fIsCallableStatement) {
            statementClassName = "java.sql.CallableStatement";
        }

        final BlancoCgField cgField = fCgFactory.createField("fStatement",
                statementClassName, "Statement object used internally by this class.");
        fCgClass.getFieldList().add(cgField);

        cgField.getLangDoc().getDescriptionList().add(
                "This object is generated from the database connection object and used internally.<br>");
        cgField.getLangDoc().getDescriptionList().add(
                "Closes this object when the close method is called.");

        /*
         * For the purpose of making the generation gap design pattern available, the scope is set to protected.
         */
        cgField.setAccess("protected");
    }
}
