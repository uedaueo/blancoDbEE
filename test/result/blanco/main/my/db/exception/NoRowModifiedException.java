/*
 * This code is generated by blanco Framework.
 */
package my.db.exception;

/**
 * Exception class to indicate that no rows of data were changed as a result of database processing <br>
 * This class is used in the source code generated by blancoDb <br>
 * Note: This class will be used as a file after automatic source code generation.
 * @since 2005.05.12
 * @author blanco Framework
 */
public class NoRowModifiedException extends NotSingleRowException {
    /**
     * SQLState code that represents this class.<br>
     * Note: When using this class, do not rely on SQLState, but use the type of the exception class to determine the state.
     */
    protected static final String SQLSTATE_NOROWMODIFIED = "00102";

    /**
     * Creates an instance of the exception class to indicate that no rows of data were changed as a result of database processing.
     *
     * @deprecated It is recommended to use a different constructor that can store the reason.
     */
    public NoRowModifiedException() {
        super("No row modified exception has occured.", SQLSTATE_NOROWMODIFIED);
    }

    /**
     * Creates an instance of the exception class to indicate that no rows of data were changed as a result of database processing.
     *
     * @param reason Description of the exception
     */
    public NoRowModifiedException(final String reason) {
        super(reason, SQLSTATE_NOROWMODIFIED);
    }

    /**
     * Creates an instance of the exception class to indicate that no rows of data were changed as a result of database processing.
     *
     * @param reason Description of the exception
     * @param SQLState XOPEN code or SQL 99 code that identifies the exception
     */
    public NoRowModifiedException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }

    /**
     * Creates an instance of the exception class to indicate that no rows of data were changed as a result of database processing.
     *
     * @param reason Description of the exception
     * @param SQLState XOPEN code or SQL 99 code that identifies the exception
     * @param vendorCode Specific exception codes defined by the database vendor
     */
    public NoRowModifiedException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
}
