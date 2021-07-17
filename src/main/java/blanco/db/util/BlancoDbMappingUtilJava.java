/*
 * blancoDb Enterprise Edition Copyright (C) 2004-2005 Tosiki Iga
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 */
package blanco.db.util;

import java.sql.ResultSetMetaData;
import java.sql.Types;

import blanco.commons.util.BlancoNameUtil;
import blanco.dbmetadata.BlancoDbMetaDataUtil;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

/**
 * A class that collects methods related to type mapping of blancoDb.
 *
 * @author ToshikiIga
 */
public final class BlancoDbMappingUtilJava {
    /**
     * Gets the full class name in Java from the column structure.
     *
     * This process is different for each programming language. This is the important process of mapping Types to what types in Java.
     *
     * @param columnStructure
     * @return
     */
    public static final String getFullClassName(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        final boolean isNoNulls = (columnStructure.getNullable() == ResultSetMetaData.columnNoNulls);
        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (isNoNulls) {
                return "boolean";
            } else {
                return "java.lang.Boolean";
            }
        case Types.TINYINT:
            if (isNoNulls) {
                return "byte";
            } else {
                return "java.lang.Byte";
            }
        case Types.SMALLINT:
            if (isNoNulls) {
                return "short";
            } else {
                return "java.lang.Short";
            }
        case Types.INTEGER:
            if (isNoNulls) {
                return "int";
            } else {
                return "java.lang.Integer";
            }
        case Types.BIGINT:
            if (isNoNulls) {
                return "long";
            } else {
                return "java.lang.Long";
            }
        case Types.REAL:
            if (isNoNulls) {
                return "float";
            } else {
                return "java.lang.Float";
            }
        case Types.FLOAT:
        case Types.DOUBLE:
            if (isNoNulls) {
                return "double";
            } else {
                return "java.lang.Double";
            }
        case Types.NUMERIC:
        case Types.DECIMAL:
            return "java.math.BigDecimal";
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.NCHAR:
        case Types.NVARCHAR:
            return "java.lang.String";
        case Types.DATE:
            // It works the same way as TIMESTAMP tentatively.
        case Types.TIME:
            // It works the same way as TIMESTAMP tentatively.
        case Types.TIMESTAMP:
            return "java.util.Date";
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
            return "java.io.InputStream";
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
        case Types.CLOB:
        case Types.NCLOB:
            return "java.io.Reader";
        case Types.JAVA_OBJECT:
        case Types.DISTINCT:
        case Types.STRUCT:
        case Types.ARRAY:
        case Types.NULL:
        case Types.OTHER:
        case Types.REF:
        case Types.DATALINK:
        case -101:
            // workaround for Oracle's SYSTIMESTAMP type
            if("SYSTIMESTAMP".equals(columnStructure.getName())){
                return "java.util.Date";
            }
        default:
            throw new IllegalArgumentException("BlancoDbTableMeta2Xml: Binding of column parameter ["
                    + columnStructure.getName()
                    + "]("
                    + BlancoDbMetaDataUtil
                            .convertJdbcDataTypeToString(columnStructure
                                    .getDataType())
                    + "): An SQL type ("
                    + columnStructure.getDataType()
                    + "/"
                    + BlancoDbMetaDataUtil
                            .convertJdbcDataTypeToString(columnStructure
                                    .getDataType()) + ") that cannot be processed was specified.");
        }
    }

    /**
     * Gets the class name in Java from the column structure.
     *
     * @param columnStructure
     * @return
     */
    public static final String getClassName(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return BlancoNameUtil.trimJavaPackage(BlancoDbMappingUtilJava
                .getFullClassName(columnStructure));
    }

    /**
     * Determines if it is a primitive and should support null.
     *
     * There are some primitive types that do not support null, so it will determine if the type is one of them.
     *
     * @param columnStructure
     * @return
     */
    public static boolean getPrimitiveAndNullable(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        final boolean isNoNulls = !(columnStructure.getNullable() == ResultSetMetaData.columnNullable);

        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.TINYINT:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.SMALLINT:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.INTEGER:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.BIGINT:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.REAL:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.FLOAT:
        case Types.DOUBLE:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.NUMERIC:
        case Types.DECIMAL:
            return false;
        case Types.CHAR:
        case Types.VARCHAR:
            return false;
        case Types.DATE:
            // It has the same behavior as TIMESTAMP.
        case Types.TIME:
            // It has the same behavior as TIMESTAMP.
        case Types.TIMESTAMP:
            // Special behavior: for DATE, TIME, and TIMESTAMP, the behavior is the same as for primitive types + null tolerance. 
            return true;
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
            return false;
        case Types.LONGVARCHAR:
        case Types.CLOB:
            return false;
        case Types.JAVA_OBJECT:
        case Types.DISTINCT:
        case Types.STRUCT:
        case Types.ARRAY:
        case Types.NULL:
        case Types.OTHER:
        case Types.REF:
        case Types.DATALINK:
        default:
            return false;
        }
    }

    /**
     * Gets the name of the setter method for PreparedStatement based on the column information.
     *
     * @param columnStructure
     * @return
     */
    public static final String getSetterMethodNameForPreparedStatement(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return "set" + getGetterSetterBaseMethodName(columnStructure);
    }

    /**
     * Gets the name of the getter method for ResultSet based on the column information.
     *
     * @param columnStructure
     * @return
     */
    public static final String getGetterMethodNameForResultSet(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return "get" + getGetterSetterBaseMethodName(columnStructure);
    }

    /**
     * Gets the name of the update method for ResultSet based on the column information.
     *
     * @param columnStructure
     * @return
     */
    public static final String getUpdateMethodNameForResultSet(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return "update" + getGetterSetterBaseMethodName(columnStructure);
    }

    /**
     * Gets the base name of the getter setter method name.
     *
     * @param columnStructure
     * @return
     */
    private static final String getGetterSetterBaseMethodName(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            return "Boolean";
        case Types.TINYINT:
            return "Byte";
        case Types.SMALLINT:
            return "Short";
        case Types.INTEGER:
            return "Int";
        case Types.BIGINT:
            return "Long";
        case Types.REAL:
            return "Float";
        case Types.FLOAT:
        case Types.DOUBLE:
            return "Double";
        case Types.NUMERIC:
        case Types.DECIMAL:
            return "BigDecimal";
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.NCHAR:
        case Types.NVARCHAR:
            return "String";
        case Types.DATE:
            // It works the same way as TIMESTAMP tentatively.
        case Types.TIME:
            // It works the same way as TIMESTAMP tentatively.
        case Types.TIMESTAMP:
            return "Timestamp";
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
            return "BinaryStream";
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
        case Types.CLOB:
        case Types.NCLOB:
            return "CharacterStream";
        case Types.JAVA_OBJECT:
        case Types.DISTINCT:
        case Types.STRUCT:
        case Types.ARRAY:
        case Types.NULL:
        case Types.OTHER:
        case Types.REF:
        case Types.DATALINK:
        case -101:
            // workaround for Oracle's SYSTIMESTAMP type
            if("SYSTIMESTAMP".equals(columnStructure.getName())){
                return "Timestamp";
            }
        default:
            throw new IllegalArgumentException("Failed to parse the method name corresponding to type ["
                    + columnStructure.getDataType()
                    + "/"
                    + BlancoDbMetaDataUtil
                            .convertJdbcDataTypeToString(columnStructure
                                    .getDataType()) + "] in the process of getting the getter and setter.");
        }
    }

    /**
     * Wrapping primitive types, etc. with wrapper class only when necessary.
     *
     * Replaces the primitive type with an object of the wrapper class.<br>
     * In addition, the replacement of java.sql.Date, java.sql.Timestamp, etc. with java.util.Date.
     *
     * @param columnStructure
     * @param originalLine
     * @return
     */
    public static final String mapPrimitiveIntoWrapperClass(
            final BlancoDbMetaDataColumnStructure columnStructure,
            final String originalLine) {
        String converter1 = "";
        String converter2 = "";

        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Boolean(";
                converter2 = ")";
            }
            break;
        case Types.TINYINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Byte(";
                converter2 = ")";
            }
            break;
        case Types.SMALLINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Short(";
                converter2 = ")";
            }
            break;
        case Types.INTEGER:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Integer(";
                converter2 = ")";
            }
            break;
        case Types.BIGINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Long(";
                converter2 = ")";
            }
            break;
        case Types.REAL:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Float(";
                converter2 = ")";
            }
            break;
        case Types.FLOAT:
        case Types.DOUBLE:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Double(";
                converter2 = ")";
            }
            break;
        case Types.DATE:
        case Types.TIME:
        case Types.TIMESTAMP:
            // In the case of Date, the ResultSet will return Timestamp.
            converter1 = "BlancoDbUtil.convertTimestampToDate(";
            converter2 = ")";
            break;
        }

        return converter1 + originalLine + converter2;
    }

    /**
     * Converts a wrapper class to a primitive.<br>
     * Replaces the wrapper class object with a primitive type.<br>
     * In addition, replaces java.util.Date with java.sql.Timestamp.
     *
     * @param columnStructure
     * @param originalLine
     * @return
     */
    public static final String mapWrapperClassIntoPrimitive(
            final BlancoDbMetaDataColumnStructure columnStructure,
            final String originalLine) {
        String converter1 = "";
        String converter2 = "";

        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".booleanValue()";
            }
            break;
        case Types.TINYINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".byteValue()";
            }
            break;
        case Types.SMALLINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".shortValue()";
            }
            break;
        case Types.INTEGER:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".intValue()";
            }
            break;
        case Types.BIGINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".longValue()";
            }
            break;
        case Types.REAL:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".floatValue()";
            }
            break;
        case Types.FLOAT:
        case Types.DOUBLE:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".doubleValue()";
            }
            break;
        case Types.DATE:
        case Types.TIME:
        case Types.TIMESTAMP:
            converter1 = "new Timestamp(";
            converter2 = ".getTime())";
            break;
        }

        return converter1 + originalLine + converter2;
    }
}
