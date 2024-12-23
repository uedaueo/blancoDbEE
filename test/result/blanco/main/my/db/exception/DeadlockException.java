/*
 * This code is generated by blanco Framework.
 */
package my.db.exception;

import java.sql.SQLException;

/**
 * Exception class for database deadlocks <br>
 * This class is used in the source code generated by blancoDb <br>
 * Note: This class will be used as a file after automatic source code generation.
 * @since 2006.02.28
 * @author blanco Framework
 */
public class DeadlockException extends SQLException {
    /**
     * SQLState code that represents this class.
     */
    protected static final String SQLSTATE_DEADLOCK = "40001";

    /**
     * Creates an instance of the exception object to indicate that a database deadlock has occurred.
     *
     * @deprecated Please do not use this constructor as much as possible, but use another constructor that can use the SQLState of the original exception and venderCode.
     */
    public DeadlockException() {
        super("Deadlock exception has occured.", SQLSTATE_DEADLOCK);
    }

    /**
     * Creates an instance of the exception object to indicate that a database deadlock has occurred.
     *
     * @deprecated Please do not use this constructor as much as possible, but use another constructor that can use the SQLState of the original exception and venderCode.
     *
     * @param reason Description of the exception
     */
    public DeadlockException(final String reason) {
        super(reason, SQLSTATE_DEADLOCK);
    }

    /**
     * Creates an instance of the exception object to indicate that a database deadlock has occurred.
     *
     * @deprecated Please do not use this constructor as much as possible, but use another constructor that can use the SQLState of the original exception and venderCode.
     *
     * @param reason Description of the exception
     * @param SQLState XOPEN code or SQL 99 code that identifies the exception
     */
    public DeadlockException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }

    /**
     * Creates an instance of the exception object to indicate that a database deadlock has occurred.
     *
     * @param reason Description of the exception
     * @param SQLState XOPEN code or SQL 99 code that identifies the exception
     * @param vendorCode Specific exception codes defined by the database vendor
     */
    public DeadlockException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
}
