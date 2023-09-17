/*
 * This code is generated by blanco Framework.
 */
package my.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import my.db.exception.DeadlockException;
import my.db.exception.TimeoutException;
import my.db.row.SimpleTestBlancodb2SelectAllRow;
import my.db.util.BlancoDbUtil;

/**
 * [SimpleTestBlancodb2SelectAll]  (QueryIterator)
 *
 * Wraps a search-type SQL statement to provide various accessors.<br>
 * Scroll attribute: insensitive<br>
 */
public class SimpleTestBlancodb2SelectAllIterator {
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
     * The result set object used internally by this class.
     *
     * This object is created from the database statement object and used internally.<br>
     * Closes this object when the close method is called.
     */
    protected ResultSet fResultSet;

    /**
     * SimpleTestBlancodb2SelectAllIteratorConstructor for the class.
     *
     * Creates a query class with a database connection object as an argument.<br>
     * After using this class, you must call the close() method.<br>
     *
     * @param conn Database connection
     */
    public SimpleTestBlancodb2SelectAllIterator(final Connection conn) {
        fConnection = conn;
    }

    /**
     * SimpleTestBlancodb2SelectAllIteratorConstructor for the class.
     *
     * Creates a query class without giving a database connection object.<br>
     */
    @Deprecated
    public SimpleTestBlancodb2SelectAllIterator() {
    }

    /**
     * SimpleTestBlancodb2SelectAllIteratorSets a database connection to the class.
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
        return "SELECT COL_ID, COL_NUMERIC\n  FROM TEST_BLANCODB2\n ORDER BY COL_ID";
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
     * scroll attribute: insensitive
     *
     * @param query The SQL statement that you want to have precompiled. In the case of dynamic SQL, this argument is the executable SQL statement after it has been processed.
     * @throws SQLException If an SQL exception occurs.
     */
    protected void prepareStatement(final String query) throws SQLException {
        close();
        fStatement = fConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Executes a search-type query.<br>
     *
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    public void executeQuery() throws DeadlockException, TimeoutException, SQLException {
        if (fStatement == null) {
            // Since PreparedStatement has not yet been obtained, it is obtained by calling the prepareStatement() method prior to executing PreparedStatement.executeQuery().
            prepareStatement();
        }
        if (fResultSet != null) {
            // Since the previous result set (ResultSet) is still there, releases it.
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
     * Moves the cursor to the next line from the current position.
     *
     * @return True if the new current row is valid, false if there are no more rows.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
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
     * Moves the cursor one line forward from the current position.
     *
     * @return True if the new current row is valid, false if there are no more rows.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean previous() throws DeadlockException, TimeoutException, SQLException {
        if (fResultSet == null) {
            executeQuery();
        }

        try {
            return fResultSet.previous();
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * Moves the cursor to the first line of the result set.
     *
     * @return True if the new current row is valid, false if there are no more rows.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean first() throws DeadlockException, TimeoutException, SQLException {
        if (fResultSet == null) {
            executeQuery();
        }

        try {
            return fResultSet.first();
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * Moves the cursor to the last line of the result set.
     *
     * @return True if the new current row is valid, false if there are no more rows.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean last() throws DeadlockException, TimeoutException, SQLException {
        if (fResultSet == null) {
            executeQuery();
        }

        try {
            return fResultSet.last();
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * Moves the cursor to the specified line of the result set.
     *
     * absolute(1) is the same as calling first().<br>
     * absolute(-1) is the same as calling last().<br>
     *
     * @param rows Specifies the line number to which the cursor will move. If it is a positive number, it counts from the beginning of the result set. If the number is negative, it counts from the end of the result set.
     * @return True if the new current row is valid, false if there are no more rows.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean absolute(final int rows) throws DeadlockException, TimeoutException, SQLException {
        if (fResultSet == null) {
            executeQuery();
        }

        try {
            return fResultSet.absolute(rows);
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * Moves the cursor by the relative number of rows in the result set.
     * relative(1) is the same as calling next().<br>
     * relative(-1) is the same as calling previous().<br>
     *
     * @param rows Specifies the number of relative rows to move from the current row. A positive number moves the cursor forward, a negative number moves the cursor backward.
     * @return True if the new current row is valid, false if there are no more rows.
     * @throws DeadlockException If a database deadlock occurs.
     * @throws TimeoutException If a database timeout occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean relative(final int rows) throws DeadlockException, TimeoutException, SQLException {
        if (fResultSet == null) {
            executeQuery();
        }

        try {
            return fResultSet.relative(rows);
        } catch (SQLException ex) {
            throw BlancoDbUtil.convertToBlancoException(ex);
        }
    }

    /**
     * Gets the data of the current row as an object.
     *
     * Before calling this method, you need to call a method that manipulates the cursor, such as next().
     *
     * @return Row object.
     * @throws SQLException If an SQL exception occurs.
     */
    public SimpleTestBlancodb2SelectAllRow getRow() throws SQLException {
        SimpleTestBlancodb2SelectAllRow result = new SimpleTestBlancodb2SelectAllRow();
        result.setColId(fResultSet.getInt(1));
        result.setColNumeric(fResultSet.getBigDecimal(2));

        return result;
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
     * Gets the internally held ResultSet object.
     *
     * @deprecated Basically, you don't need to use ResultSet directly from outside.
     *
     * @return The ResultSet object.
     */
    public ResultSet getResultSet() {
        return fResultSet;
    }

    /**
     * Gets the search results in the form of a list.
     *
     * The list will contain the SimpleTestBlancodb2SelectAll class.<br>
     * This can be used when the number of search results is known in advance and the number is small.<br>
     * If you have a large number of search results, it is recommended that you do not use this method, but use the next() method instead.<br>
     *
     * @param absoluteStartPoint The line to start reading. Specify 1 if you want to read from the first line.
     * @param size The number of lines to read.
     * @return SimpleTestBlancodb2SelectAllClass List, which will return an empty list if the search results are zero.
     * @throws SQLException If an SQL exception occurs.
     */
    public List<SimpleTestBlancodb2SelectAllRow> getList(final int absoluteStartPoint, final int size) throws SQLException {
        List<SimpleTestBlancodb2SelectAllRow> result = new ArrayList<SimpleTestBlancodb2SelectAllRow>(8192);
        if (absolute(absoluteStartPoint) == false) {
            return result;
        }
        for (int count = 1; count <= size; count++) {
            if (count != 1) {
                if (next() == false) {
                    break;
                }
            }
            result.add(getRow());
        }
        return result;
    }

    /**
     * Closes this class.
     *
     * Calls the close() method on the JDBC resource object that was created internally.<br>
     * Make sure to call this method after using the class.
     *
     * @throws SQLException If an SQL exception occurs.
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
     * finalize method.
     *
     * Checks if there is a close() call forgetting bug in the object generated internally by this class.<br>
     *
     * @throws Throwable Exception raised in the finalize process.
     */
    protected void finalize() throws Throwable {
        super.finalize();
        if (fStatement != null) {
            final String message = "SimpleTestBlancodb2SelectAllIterator : The resource has not been released by the close() method.";
            System.out.println(message);
        }
    }
}