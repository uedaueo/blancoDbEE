/*
 * This code is generated by blanco Framework.
 */
package my.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import blanco.db.runtime.BlancoDbQuery;
import blanco.fw.BlancoGeneratedBy;

import my.db.exception.DeadlockException;
import my.db.exception.IntegrityConstraintException;
import my.db.exception.NoRowModifiedException;
import my.db.exception.TimeoutException;
import my.db.exception.TooManyRowsModifiedException;
import my.db.util.BlancoDbUtil;

/**
 * [SimpleTestBlancodbInsertNoNulls]  (QueryInvoker)
 *
 * 実行型SQL文をラッピングして各種アクセサを提供します。<br>
 * シングル属性: 有効 (期待する処理件数は1件)<br>
 */
@BlancoGeneratedBy(name = "blancoDb")
public class SimpleTestBlancodbInsertNoNullsInvoker implements BlancoDbQuery {
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
     * SimpleTestBlancodbInsertNoNullsInvokerクラスのコンストラクタ。
     *
     * データベースコネクションオブジェクトを引数としてクエリクラスを作成します。<br>
     * このクラスの利用後は、必ず close()メソッドを呼び出す必要があります。<br>
     *
     * @param conn データベース接続
     */
    public SimpleTestBlancodbInsertNoNullsInvoker(final Connection conn) {
        fConnection = conn;
    }

    /**
     * SimpleTestBlancodbInsertNoNullsInvokerクラスのコンストラクタ。
     *
     * データベースコネクションオブジェクトを与えずにクエリクラスを作成します。<br>
     */
    @Deprecated
    public SimpleTestBlancodbInsertNoNullsInvoker() {
    }

    /**
     * SimpleTestBlancodbInsertNoNullsInvokerクラスにデータベース接続を設定。
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
        return "INSERT\n  INTO TEST_BLANCODB\n       (COL_ID)\nVALUES\n       (?)";
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
     * 動的 SQL を利用する必要がある場合には、SQL 定義書で「動的SQL」を「使用する」に変更してください。変更後は外部から利用可能になります。<br>
     * 内部的に JDBC ドライバの Connection.prepareStatement を呼び出します。<br>
     *
     * @param query プリコンパイルを実施させたいSQL文。動的SQLの場合には、この引数には加工された後の実行可能なSQL文を与えます。
     * @throws SQLException SQL例外が発生した場合。
     */
    protected void prepareStatement(final String query) throws SQLException {
        close();
        fStatement = fConnection.prepareStatement(query);
    }

    /**
     * SQL文に与えるSQL入力パラメータをセットします。
     *
     * 内部的には PreparedStatementにSQL入力パラメータをセットします。
     *
     * @param colId 'colId'列の値
     * @throws SQLException SQL例外が発生した場合。
     */
    public void setInputParameter(final int colId) throws SQLException {
        if (fStatement == null) {
            prepareStatement();
        }
        fStatement.setInt(1, colId);
    }

    /**
     * SQL文を実行します。
     *
     * シングル属性が有効なのでスコープをprotectedとします。<br>
     * このメソッドの代わりに executeSingleUpdateメソッドを利用してください。<br>
     *
     * @return 処理された行数
     * @throws IntegrityConstraintException データベース制約違反が発生した場合。
     * @throws DeadlockException データベースデッドロックが発生した場合。
     * @throws TimeoutException データベースタイムアウトが発生した場合。
     * @throws SQLException SQL例外が発生した場合。
     */
    protected int executeUpdate() throws IntegrityConstraintException, DeadlockException, TimeoutException, SQLException {
        if (fStatement == null) {
            // PreparedStatementが未取得の状態なので、PreparedStatement.executeUpdate()実行に先立ちprepareStatement()メソッドを呼び出して取得します。
            prepareStatement();
        }

        try {
            return fStatement.executeUpdate();
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * SQL文を実行します。
     *
     * SQL文の実行結果が1行であることを確認します。実行結果が1行以外である場合には例外を発生させます。<br>
     * シングル属性が有効となっているので生成されます。<br>
     *
     * @throws NoRowModifiedException データベースの処理の結果、1行もデータが変更されなかった場合。
     * @throws TooManyRowsModifiedException データベースの処理の結果、1行を超えるデータが変更されてしまった場合。
     * @throws IntegrityConstraintException データベース制約違反が発生した場合。
     * @throws DeadlockException データベースデッドロックが発生した場合。
     * @throws TimeoutException データベースタイムアウトが発生した場合。
     * @throws SQLException SQL例外が発生した場合。
     */
    public void executeSingleUpdate() throws NoRowModifiedException, TooManyRowsModifiedException, IntegrityConstraintException, DeadlockException, TimeoutException, SQLException {
        int result = 0;
        result = executeUpdate();

        if (result == 0) {
            throw new NoRowModifiedException("データベースの処理の結果、1行もデータが変更されませんでした。");
        } else if (result > 1) {
            String message = "データベースの処理の結果、1行を超えるデータが変更されました。変更件数:" + result;
            throw new TooManyRowsModifiedException(message);
        }
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
     * このクラスのクローズ処理をおこないます。
     *
     * 内部的に生成していたJDBCリソースのオブジェクトに対して close()メソッドの呼び出しをおこないます。<br>
     * クラスの利用が終わったら、必ずこのメソッドを呼び出すようにします。
     *
     * @throws SQLException SQL例外が発生した場合。
     */
    public void close() throws SQLException {
        if (fStatement != null) {
            fStatement.close();
            fStatement = null;
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
            final String message = "SimpleTestBlancodbInsertNoNullsInvoker : close()メソッドによるリソースの開放が行われていません。";
            System.out.println(message);
        }
    }
}
