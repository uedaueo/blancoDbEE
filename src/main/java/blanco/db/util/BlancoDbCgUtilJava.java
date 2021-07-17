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

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;

/**
 * In blancoDb, a class that collects utilities related to blancoCg.
 * 
 * Particularly common combinations are handled in bulk here.
 * 
 * @author ToshikiIga
 */
public class BlancoDbCgUtilJava {
    /**
     * Adds an SQL exception throw to the method.
     * 
     * @param cgFactory
     * @param cgMethod
     */
    public static void addExceptionToMethodSqlException(
            final BlancoCgObjectFactory cgFactory, final BlancoCgMethod cgMethod) {
        cgMethod.getThrowList().add(
                cgFactory.createException("java.sql.SQLException",
                        "If an SQL exception occurs."));
    }

    /**
     * Adds deadlock and timeout throw to the method.
     * 
     * @param cgFactory
     * @param cgMethod
     * @param storage
     */
    public static void addExceptionToMethodDeadlockTimeoutException(
            final BlancoCgObjectFactory cgFactory,
            final BlancoCgMethod cgMethod, final BlancoDbSetting storage) {
        cgMethod.getThrowList().add(
                cgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(storage)
                        + ".exception.DeadlockException",
                        "If a database deadlock occurs."));
        cgMethod.getThrowList().add(
                cgFactory
                        .createException(BlancoDbUtil
                                .getRuntimePackage(storage)
                                + ".exception.TimeoutException",
                                "If a database timeout occurs."));
    }

    /**
     * Adds IntegrityConstraintException throw to the method.
     * 
     * @param cgFactory
     * @param cgMethod
     * @param storage
     */
    public static void addExceptionToMethodIntegrityConstraintException(
            final BlancoCgObjectFactory cgFactory,
            final BlancoCgMethod cgMethod, final BlancoDbSetting storage) {
        cgMethod.getThrowList().add(
                cgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(storage)
                        + ".exception.IntegrityConstraintException",
                        "If a database constarint violation occurs."));
    }

    /**
     * Adds typical logging of method start to the method.
     * 
     * For logs that are not typical, do not use this method, but implement it separately.
     * 
     * @param cgMethod
     */
    public static void addBeginLogToMethod(final BlancoCgMethod cgMethod) {
        final List<String> listLine = cgMethod.getLineList();

        listLine.add("if (fLog.isDebugEnabled()) {");
        listLine.add("fLog.debug(\"" + cgMethod.getName() + "\");");
        listLine.add("}");
        listLine.add("");
    }
}
