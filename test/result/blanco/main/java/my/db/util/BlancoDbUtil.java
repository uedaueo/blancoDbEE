/*
 * This code is generated by blanco Framework.
 */
package my.db.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.db.exception.DeadlockException;
import my.db.exception.IntegrityConstraintException;
import my.db.exception.TimeoutException;

/**
 * blancoDbが共通的に利用するユーティリティクラス。
 * このクラスはblancoDbが生成したソースコードで利用されます <br>
 * このクラスは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。
 * @since 2006.03.02
 * @author blanco Framework
 */
public class BlancoDbUtil {
    static final private Map<String, String> mapComparison = new HashMap<String, String>() {
        {
            put("EQ", "=");
            put("NE", "<>");
            put("GT", ">");
            put("LT", "<");
            put("GE", ">=");
            put("LE", "<=");
            put("LIKE", "LIKE");
            put("NOT LIKE", "NOT LIKE");
        }
    };

    /**
     * SQL例外をblanco Framework例外オブジェクトに変換します。<br>
     * SQL例外のなかで、blanco Frameworkの例外オブジェクトに変換すべきものについて変換します。<br>
     * 変換すべき先が無い場合には、そのまま元のオブジェクトを返却します。
     *
     * @param ex JDBCから返却された例外オブジェクト。
     * @return 変換後のSQL例外オブジェクト。SQLExceptionまたはその継承クラスである IntegrityConstraintException, DeadlockException, TimeoutExceptionが戻ります。
     */
    public static SQLException convertToBlancoException(final SQLException ex) {
        if (ex.getSQLState() != null) {
            if (ex.getSQLState().startsWith("23")) {
                final IntegrityConstraintException exBlanco = new IntegrityConstraintException("データベース制約違反により変更が失敗しました。" + ex.toString(), ex.getSQLState(), ex.getErrorCode());
                exBlanco.initCause(ex);
                return exBlanco;
            } else if (ex.getSQLState().equals("40001")) {
                final DeadlockException exBlanco = new DeadlockException("データベースデッドロックにより変更が失敗しました。" + ex.toString(), ex.getSQLState(), ex.getErrorCode());
                exBlanco.initCause(ex);
                return exBlanco;
            } else if (ex.getSQLState().equals("HYT00")) {
                final TimeoutException exBlanco = new TimeoutException("データベースタイムアウトにより変更が失敗しました。" + ex.toString(), ex.getSQLState(), ex.getErrorCode());
                exBlanco.initCause(ex);
                return exBlanco;
            }
        }
        return ex;
    }

    /**
     * JDBCのTimestampをDate型に変換します。
     *
     * java.sql.Timestamp型からjava.util.Date型へと変換します。<br>
     * このメソッドは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。
     *
     * @param argTimestamp JDBCのTimestamp型を与えます。
     * @return 変換後のjava.util.Date型を戻します。
     */
    public static final Date convertTimestampToDate(final Timestamp argTimestamp) {
        if (argTimestamp == null) {
            return null;
        }
        return new Date(argTimestamp.getTime());
    }

    /**
     * 入力パラメータから動的条件句を自動生成します。
     *
     * Excel定義書を元に生成されたMapと、実行時に与えられたパラメータから動的SQLを生成します。<br>
     * このメソッドは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。
     *
     * @param argMapClause 動的条件式定義のMapを指定します。
     * @param argParameter 動的条件式を選択するためのパラメータを指定します。
     * @param argQuery 動的条件式を選択するためのパラメータを指定します。
     * @param argExpectedTag パラメータとして null が渡された場合に削除するtagを指定します。
     * @param <T>  Virtual parameter for BlancoDbDynamicParameter.
     * @return Tag置換後のqueryを戻します。
     * @throws SQLException SQL例外を投げる可能性があります。
     */
    public static final <T> String createDynamicClause(final Map<String, BlancoDbDynamicClause> argMapClause, final BlancoDbDynamicParameter<T> argParameter, final String argQuery, final String argExpectedTag) throws SQLException {
        String query = argQuery;
        if (argParameter != null) {
            String key = argParameter.getKey();

            List<T> values = argParameter.getValues();
            if (key != null) {
                BlancoDbDynamicClause dynamicClause = argMapClause.get(key);
                if (dynamicClause != null) {
                    /* 動的条件句Mapは自動生成されるので、不正な値は存在しない前提とする */
                    StringBuffer sb = new StringBuffer();
                    String tag = dynamicClause.getTag();
                    String condition = dynamicClause.getCondition();

                    if ("ORDERBY".equals(condition)) {
                        if (values != null && values.size() > 0) {
                            sb.append("ORDER BY ");
                            int count = 0;
                            for (T value : values) {
                                BlancoDbDynamicOrderBy orderBy = (BlancoDbDynamicOrderBy) value;
                                if (count > 0) {
                                    sb.append(", ");
                                }
                                String column = dynamicClause.getItem(orderBy.getColumn());
                                if (column == null) {
                                    throw new SQLException("入力に指定された[ " + orderBy.getColumn() + " ]は定義書で未定義です。", "42S22", 9999);
                                }
                                sb.append(column);
                                if (!"ASC".equals(orderBy.getOrder())) {
                                    sb.append(" DESC");
                                } else {
                                    sb.append(" ASC");
                                }
                                count++;
                            }
                        }
                    } else if ("BETWEEN".equals(condition)) {
                        if (values != null && values.size() == 2) {
                            sb.append(" " + dynamicClause.getLogical() + " ( " + dynamicClause.getItems().get(0) + " BETWEEN ? AND ? )");
                        }
                    } else if ("NOT BETWEEN".equals(condition)) {
                        if (values != null && values.size() == 2) {
                            sb.append(" " + dynamicClause.getLogical() + " ( " + dynamicClause.getItems().get(0) + " NOT BETWEEN ? AND ? )");
                        }
                    } else if ("IN".equals(condition)) {
                        if (values != null && values.size() > 0) {
                            sb.append(" " + dynamicClause.getLogical() + " ( " + dynamicClause.getItems().get(0) + " IN ( ");
                            int count = 0;
                            for (T value : values) {
                                if (count > 0) {
                                    sb.append(", ");
                                }
                                sb.append("?");
                                count++;
                            }
                            sb.append(" )");
                            sb.append(" )");
                        }
                    } else if ("NOT IN".equals(condition)) {
                        if (values != null && values.size() > 0) {
                            sb.append(" " + dynamicClause.getLogical() + " ( " + dynamicClause.getItems().get(0) + " NOT IN ( ");
                            int count = 0;
                            for (T value : values) {
                                if (count > 0) {
                                    sb.append(", ");
                                }
                                sb.append("?");
                                count++;
                            }
                            sb.append(" )");
                            sb.append(" )");
                        }
                    } else if ("COMPARE".equals(condition)) {
                        if (values != null && values.size() > 0) {
                            String logicalOperator = "OR";
                            if (argParameter.getLogicalOperator() != null && argParameter.getLogicalOperator().equalsIgnoreCase("AND")) {
                                logicalOperator = "AND";
                            }
                            sb.append(" " + dynamicClause.getLogical() + " ( ");
                            for (int count = 0; count < values.size(); count++) {
                                if (count > 0) {
                                    sb.append(" " + logicalOperator + " ");
                                }
                                sb.append(dynamicClause.getItems().get(0) + " " + mapComparison.get(dynamicClause.getComparison()) + " ?");
                            }
                            sb.append(" )");
                        }
                    }
                    query = argQuery.replace("${" + tag + "}", sb.toString());
                }
            }
        } else {
            query = argQuery.replace("${" + argExpectedTag + "}", "");
        }
        return query;
    }

    /**
     * 動的SQLのためのInputパラメータ関数です。
     *
     * 実行時に与えられたパラメータを動的に生成された条件句に適用します。<br>
     * このメソッドは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。
     *
     * @param argStatement 動的に定義されたpreparedStatementです。
     * @param values 動的に定義されたpreparedStatementです。
     * @param startIndex パラメータの開始indexです。
     * @param <T>  Virtual parameter for BlancoDbDynamicParameter.
     * @return Tag置換後のqueryを戻します。
     * @throws SQLException SQLException may be thrown.
     */
    public static final <T> int setInputParameter(final PreparedStatement argStatement, final List<T> values, final Integer startIndex) throws SQLException {
        int index = startIndex;
        for (T value : values) {
            /*
             * NULL の場合の面倒も見てくれそうだが、
             * Database エンジンによってはエラーになるかもしれない。
             * e.g. SQLServer は NG, MySQL connector は OK.
             */
            argStatement.setObject(index, value);
            index++;
        }
        return index;
    }
}
