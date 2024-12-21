/*
 * This code is generated by blanco Framework.
 */
package my.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import my.db.exception.DeadlockException;
import my.db.exception.IntegrityConstraintException;
import my.db.exception.NoRowModifiedException;
import my.db.exception.TimeoutException;
import my.db.exception.TooManyRowsModifiedException;
import my.db.util.BlancoDbDynamicClause;
import my.db.util.BlancoDbDynamicLiteral;
import my.db.util.BlancoDbDynamicOrderBy;
import my.db.util.BlancoDbDynamicParameter;
import my.db.util.BlancoDbUtil;

/**
 * [SimpleTestBlancodbDelete]  (QueryInvoker)
 *
 * Wraps an executable SQL statement and provides various accessors.<br>
 * Single attribute: Enabled (expected number of processes is 1)<br>
 */
public class SimpleTestBlancodbDeleteInvoker {
    /**
     * Database connection object used internally by this class.
     *
     * Database connection object is given externally as arguments to the constructor.<br>
     * Transaction commit and rollback are not performed inside this class.
     */
    protected Connection fConnection;

    /**
     * Statement object used internally by this class.
     *
     * This object is generated from the database connection object and used internally.<br>
     * Closes this object when the close method is called.
     */
    protected PreparedStatement fStatement;

    /**
     * SimpleTestBlancodbDeleteInvokerConstructor for the class.
     *
     * Creates a query class with a database connection object as an argument.<br>
     * After using this class, you must call the close() method.<br>
     *
     * @param conn Database connection
     */
    public SimpleTestBlancodbDeleteInvoker(final Connection conn) {
        fConnection = conn;
    }

    /**
     * SimpleTestBlancodbDeleteInvokerConstructor for the class.
     *
     * Creates a query class without giving a database connection object.<br>
     */
    @Deprecated
    public SimpleTestBlancodbDeleteInvoker() {
    }

    /**
     * SimpleTestBlancodbDeleteInvokerSets a database connection to the class.
     *
     * @param conn Database connection
     */
    @Deprecated
    public void setConnection(final Connection conn) {
        fConnection = conn;
    }

    /**
     * Gets the SQL statement given in the SQL definition document.
     *
     * If the # keyword is specified as the SQL input parameter, the SQL statement after replacing the corresponding part with ? can be obtained.
     *
     * @return SQL statement in the state that can be given to the JDBC driver and executed.
     */
    public String getQuery() {
        return "DELETE FROM TEST_BLANCODB\n WHERE COL_ID = ?";
    }

    /**
     * Precompiles with the SQL statement given from the SQL definition document.
     *
     * Internally calls Connection.prepareStatement.<br>
     *
     * @throws SQLException If an SQL exception occurs.
     */
    public void prepareStatement() throws SQLException {
        close();
        prepareStatement(getQuery());
    }

    /**
     * Precompiles with the given SQL statement (dynamic SQL).
     *
     * This method should only be used when you need to execute SQL that dynamically changes its contents.<br>
     * If you need to use dynamic SQL, please change "Dynamic SQL" to "Use" in the SQL definition document. After the change, it will be available externally.<br>
     * Internally calls the JDBC driver's Connection.prepareStatement.<br>
     *
     * @param query The SQL statement that you want to have precompiled. In the case of dynamic SQL, this argument is the executable SQL statement after it has been processed.
     * @throws SQLException If an SQL exception occurs.
     */
    protected void prepareStatement(final String query) throws SQLException {
        close();
        fStatement = fConnection.prepareStatement(query);
    }

    /**
     * Sets the SQL input parameters to be given to the SQL statement.
     *
     * Internally, the PreparedStatement is set with SQL input parameters.
     *
     * @param colId Value in 'colId' column
     * @throws SQLException If an SQL exception occurs.
     */
    public void setInputParameter(final int colId) throws SQLException {
        if (fStatement == null) {
            prepareStatement();
        }

        int index = 1;
        fStatement.setInt(index, colId);
        index++;

    }

    /**
     * Executes the SQL statement.
     *
     * Since the single attribute is valid, the scope is set to protected.<br>
     * Use the executeSingleUpdate method instead of this method.<br>
     *
     * @return The number of processed lines
     * @throws IntegrityConstraintException If a database constarint violation occurs.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    protected int executeUpdate() throws IntegrityConstraintException, DeadlockException, TimeoutException, SQLException {
        if (fStatement == null) {
            // Since PreparedStatement has not been obtained yet, obtains by calling prepareStatement() method prior to executing PreparedStatement.executeUpdate().
            prepareStatement();
        }

        try {
            return fStatement.executeUpdate();
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * Executes the SQL statement.
     *
     * Verifies that the result of the SQL statement execution is a single row. If the result is not a single row, it will raise an exception.<br>
     * Generated since the single attribute is enabled.<br>
     *
     * @throws NoRowModifiedException If not a single row of data has been changed as a result of the database processing.
     * @throws TooManyRowsModifiedException If more than one row of data has been changed as a result of database processing.
     * @throws IntegrityConstraintException If a database constarint violation occurs.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    public void executeSingleUpdate() throws NoRowModifiedException, TooManyRowsModifiedException, IntegrityConstraintException, DeadlockException, TimeoutException, SQLException {
        int result = 0;
        result = executeUpdate();

        if (result == 0) {
            throw new NoRowModifiedException("Not a single row of data has been changed as a result of the database processing.");
        } else if (result > 1) {
            String message = "More than one row of data has been changed as a result of database processing. The number of changes:" + result;
            throw new TooManyRowsModifiedException(message);
        }
    }

    /**
     * Gets the statement (java.sql.PreparedStatement).
     * @deprecated Basically, Statement does not need to be used directly from the outside.
     *
     * @return The java.sql.PreparedStatement object used internally
     */
    public PreparedStatement getStatement() {
        return fStatement;
    }

    /**
     * Closes this class.
     *
     * Calls the close() method on the JDBC resource object that was created internally.<br>
     * Make sure to call this method when you are done using the class.
     *
     * @throws SQLException If an SQL exception occurs.
     */
    public void close() throws SQLException {
        if (fStatement != null) {
            fStatement.close();
            fStatement = null;
        }
    }
}
