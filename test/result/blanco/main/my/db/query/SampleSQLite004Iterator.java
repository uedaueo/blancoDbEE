/*
 * This code is generated by blanco Framework.
 */
package my.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.db.exception.DeadlockException;
import my.db.exception.TimeoutException;
import my.db.row.SampleSQLite004Row;
import my.db.util.BlancoDbDynamicClause;
import my.db.util.BlancoDbDynamicOrderBy;
import my.db.util.BlancoDbDynamicParameter;
import my.db.util.BlancoDbUtil;

/**
 * [SampleSQLite004] 簡易なSQLのサンプルです。 (QueryIterator)
 *
 * 検索型SQL文をラッピングして各種アクセサを提供します。<br>
 * スクロール属性: forward_only<br>
 */
public class SampleSQLite004Iterator {
    protected Map<String, BlancoDbDynamicClause> fMapDynamicClause = new HashMap<String, BlancoDbDynamicClause>()
    {
        {
            put("betweenNumeric", new BlancoDbDynamicClause("BETWEEN01", "NOT BETWEEN", "COL_NUMERIC", "AND", "java.lang.Double"));
            put("inId", new BlancoDbDynamicClause("INCLAUSE01", "IN", "COL_ID", "AND", "java.lang.Long"));
            put("compTextEq", new BlancoDbDynamicClause("COMPARE01", "COMPARE", "COL_TEXT", "OR", "java.lang.String", "EQ"));
            put("compTextLike", new BlancoDbDynamicClause("COMPARE01", "COMPARE", "COL_TEXT", "OR", "java.lang.String", "LIKE"));
            put("orderbyColumns", new BlancoDbDynamicClause("ORDERBY", "ORDERBY", "COL_ID,COL_TEXT,COL_NUMERIC"));
        }
    };

    /**
     * このクラスが内部的に利用するデータベース接続オブジェクト。
     *
     * データベース接続オブジェクトはコンストラクタの引数として外部から与えられます。<br>
     * トランザクションのコミットやロールバックは、このクラスの内部では実行しません。
     */
    protected Connection fConnection;

    /**
     * このクラスが内部的に利用するステートメントオブジェクト。
     *
     * このオブジェクトはデータベース接続オブジェクトから生成されて内部的に利用されます。<br>
     * closeメソッドの呼び出し時に、このオブジェクトのcloseを実行します。
     */
    protected PreparedStatement fStatement;

    /**
     * このクラスが内部的に利用する結果セットオブジェクト。
     *
     * このオブジェクトはデータベースステートメントオブジェクトから生成されて内部的に利用されます。<br>
     * closeメソッドの呼び出し時に、このオブジェクトのcloseを実行します。
     */
    protected ResultSet fResultSet;

    /**
     * SampleSQLite004Iteratorクラスのコンストラクタ。
     *
     * データベースコネクションオブジェクトを引数としてクエリクラスを作成します。<br>
     * このクラスの利用後は、必ず close()メソッドを呼び出す必要があります。<br>
     *
     * @param conn データベース接続
     */
    public SampleSQLite004Iterator(final Connection conn) {
        fConnection = conn;
    }

    /**
     * SampleSQLite004Iteratorクラスのコンストラクタ。
     *
     * データベースコネクションオブジェクトを与えずにクエリクラスを作成します。<br>
     */
    @Deprecated
    public SampleSQLite004Iterator() {
    }

    /**
     * SampleSQLite004Iteratorクラスにデータベース接続を設定。
     *
     * @param conn データベース接続
     */
    @Deprecated
    public void setConnection(final Connection conn) {
        fConnection = conn;
    }

    /**
     * SQL定義書で与えられたSQL文を取得します。
     *
     * SQL入力パラメータとして #キーワードによる指定がある場合には、該当箇所を ? に置き換えた後の SQL文が取得できます。
     *
     * @return JDBCドライバに与えて実行可能な状態のSQL文。
     */
    public String getQuery() {
        return "select COL_ID, COL_TEXT, COL_NUMERIC from\n   TEST_BLANCODB\nwhere\n   COL_TEXT like ?\n   ${BETWEEN01}\n   ${INCLAUSE01}\n   ${COMPARE01}\n   AND COL_NUMERIC = ?\n${ORDERBY}";
    }

    /**
     * SQL定義書から与えられたSQL文をもちいてプリコンパイルを実施します。
     *
     * 内部的にConnection.prepareStatementを呼び出します。<br>
     *
     * @throws SQLException SQL例外が発生した場合。
     */
    public void prepareStatement() throws SQLException {
        close();
        prepareStatement(getQuery());
    }

    /**
     * 与えられたSQL文をもちいてプリコンパイルを実施(動的SQL)します。
     *
     * このメソッドは、動的に内容が変化するような SQL を実行する必要がある場合にのみ利用します。<br>
     * SQL 定義書で「動的SQL」が「使用する」に設定されています。<br>
     * 内部的に JDBC ドライバの Connection.prepareStatement を呼び出します。<br>
     *
     * @param query プリコンパイルを実施させたいSQL文。動的SQLの場合には、この引数には加工された後の実行可能なSQL文を与えます。
     * @throws SQLException SQL例外が発生した場合。
     */
    public void prepareStatement(final String query) throws SQLException {
        close();
        fStatement = fConnection.prepareStatement(query);
    }

    /**
     * SQL文に与えるSQL入力パラメータをセットします。
     *
     * 内部的には PreparedStatementにSQL入力パラメータをセットします。
     *
     * @param colText 'colText'列の値
     * @param colNumeric 'colNumeric'列の値
     * @param BETWEEN01 'BETWEEN01'列の値
     * @param INCLAUSE01 'INCLAUSE01'列の値
     * @param COMPARE01 'COMPARE01'列の値
     * @param ORDERBY 'ORDERBY'列の値
     * @throws SQLException SQL例外が発生した場合。
     */
    public void setInputParameter(final String colText, final Double colNumeric, final BlancoDbDynamicParameter<java.lang.Double> BETWEEN01, final BlancoDbDynamicParameter<java.lang.Long> INCLAUSE01, final BlancoDbDynamicParameter<java.lang.String> COMPARE01, final BlancoDbDynamicParameter<BlancoDbDynamicOrderBy> ORDERBY) throws SQLException {
        /* タグを置換する */
        String query = this.getQuery();
        query = BlancoDbUtil.createDynamicClause(fMapDynamicClause, BETWEEN01, query, "BETWEEN01");
        query = BlancoDbUtil.createDynamicClause(fMapDynamicClause, INCLAUSE01, query, "INCLAUSE01");
        query = BlancoDbUtil.createDynamicClause(fMapDynamicClause, COMPARE01, query, "COMPARE01");
        query = BlancoDbUtil.createDynamicClause(fMapDynamicClause, ORDERBY, query, "ORDERBY");

        /* 必ず statement を作り直す */
        prepareStatement(query);

        int index = 1;
        fStatement.setString(index, colText);
        index++;

        if (BETWEEN01 != null) {
            java.util.List<java.lang.Double> values = BETWEEN01.getValues();
            index = BlancoDbUtil.setInputParameter(fStatement, values, index);
        }

        if (INCLAUSE01 != null) {
            java.util.List<java.lang.Long> values = INCLAUSE01.getValues();
            index = BlancoDbUtil.setInputParameter(fStatement, values, index);
        }

        if (COMPARE01 != null) {
            java.util.List<java.lang.String> values = COMPARE01.getValues();
            index = BlancoDbUtil.setInputParameter(fStatement, values, index);
        }

        if (colNumeric == null) {
            fStatement.setNull(index, java.sql.Types.FLOAT);
        } else {
            fStatement.setDouble(index, colNumeric.doubleValue());
        }
        index++;

    }

    /**
     * 検索型クエリを実行します。<br>
     *
     * @throws DeadlockException データベースデッドロックが発生した場合。
     * @throws TimeoutException データベースタイムアウトが発生した場合。
     * @throws SQLException SQL例外が発生した場合。
     */
    public void executeQuery() throws DeadlockException, TimeoutException, SQLException {
        if (fStatement == null) {
            // PreparedStatementが未取得の状態なので、PreparedStatement.executeQuery()実行に先立ちprepareStatement()メソッドを呼び出して取得します。
            prepareStatement();
        }
        if (fResultSet != null) {
            // 前回の結果セット(ResultSet)が残っているので、これを一旦開放します。
            fResultSet.close();
            fResultSet = null;
        }

        try {
            fResultSet = fStatement.executeQuery();
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * カーソルを現在の位置から1行次へ移動します。
     *
     * @return 新しい現在の行が有効な場合はtrue、それ以上の行がない場合はfalse。
     * @throws DeadlockException データベースデッドロックが発生した場合。
     * @throws TimeoutException データベースタイムアウトが発生した場合。
     * @throws SQLException SQL例外が発生した場合。
     */
    public boolean next() throws DeadlockException, TimeoutException, SQLException {
        if (fResultSet == null) {
            executeQuery();
        }

        try {
            return fResultSet.next();
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * 現在の行のデータをオブジェクトとして取得します。
     *
     * このメソッドを呼び出す前に、next()などのカーソルを操作するメソッドを呼び出す必要があります。
     *
     * @return 行オブジェクト。
     * @throws SQLException SQL例外が発生した場合。
     */
    public SampleSQLite004Row getRow() throws SQLException {
        SampleSQLite004Row result = new SampleSQLite004Row();
        result.setColId(fResultSet.getInt(1));
        result.setColText(fResultSet.getString(2));
        result.setColNumeric(fResultSet.getBigDecimal(3));

        return result;
    }

    /**
     * ステートメント (java.sql.PreparedStatement) を取得します。
     * @deprecated 基本的にStatementは外部から直接利用する必要はありません。
     *
     * @return 内部的に利用されている java.sql.PreparedStatementオブジェクト
     */
    public PreparedStatement getStatement() {
        return fStatement;
    }

    /**
     * 内部的に保持されているResultSetオブジェクトを取得します。
     *
     * @deprecated 基本的にResultSetは外部から直接利用する必要はありません。
     *
     * @return ResultSetオブジェクト。
     */
    public ResultSet getResultSet() {
        return fResultSet;
    }

    /**
     * 検索結果をリストの形式で取得します。
     *
     * リストには SampleSQLite004クラスが格納されます。<br>
     * 検索結果の件数があらかじめわかっていて、且つ件数が少ない場合に利用することができます。<br>
     * 検索結果の件数が多い場合には、このメソッドは利用せず、代わりに next()メソッドを利用することをお勧めします。<br>
     * このQueryIteratorは FORWARD_ONLY(順方向カーソル)です。大量のデータを扱うことがわかっている場合には、このgetListメソッドの利用は極力避けるか、あるいは スクロールカーソルとしてソースコードを再生成してください。
     *
     * @param size 読み出しを行う行数。
     * @return SampleSQLite004クラスのList。検索結果が0件の場合には空のリストが戻ります。
     * @throws SQLException SQL例外が発生した場合。
     */
    public List<SampleSQLite004Row> getList(final int size) throws SQLException {
        List<SampleSQLite004Row> result = new ArrayList<SampleSQLite004Row>(8192);
        for (int count = 1; count <= size; count++) {
            if (next() == false) {
                break;
            }
            result.add(getRow());
        }
        return result;
    }

    /**
     * このクラスのクローズ処理をおこないます。
     *
     * 内部的に生成していたJDBCリソースのオブジェクトに対して close()メソッドの呼び出しをおこないます。<br>
     * クラスの利用が終わったら、必ずこのメソッドを呼び出すようにします。
     *
     * @throws SQLException SQL例外が発生した場合。
     */
    public void close() throws SQLException {
        try {
            if (fResultSet != null) {
                fResultSet.close();
                fResultSet = null;
            }
        } finally {
            if (fStatement != null) {
                fStatement.close();
                fStatement = null;
            }
        }
    }

    /**
     * finalizeメソッド。
     *
     * このクラスが内部的に生成したオブジェクトのなかで、close()呼び出し忘れバグが存在するかどうかチェックします。<br>
     *
     * @throws Throwable finalize処理の中で発生した例外。
     */
    protected void finalize() throws Throwable {
        super.finalize();
        if (fStatement != null) {
            final String message = "SampleSQLite004Iterator : close()メソッドによるリソースの開放が行われていません。";
            System.out.println(message);
        }
    }
}
