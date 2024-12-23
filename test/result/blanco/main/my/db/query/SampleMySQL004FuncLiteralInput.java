/*
 * This code is generated by blanco Framework.
 */
package my.db.query;

/**
 * [SampleMySQL004] 簡易なSQLのサンプルです。 (FunctionLiteralInput)
 *
 * Provides an object for input of dynamic conditional clause function definitions.<br>
 */
public class SampleMySQL004FuncLiteralInput {
    /**
     * The number of parameters expected by this input class.
     */
    private Integer paramNum = 2;

    /**
     * 1th parameter
     */
    private Long param01;

    /**
     * 2th parameter
     */
    private String param02;

    /**
     * Constructor
     *
     * @param argParam01 1th parameter
     * @param argParam02 2th parameter
     */
    public SampleMySQL004FuncLiteralInput(final Long argParam01, final String argParam02) {
        this.param01 = argParam01;
        this.param02 = argParam02;
    }

    /**
     * Get parameters
     *
     * @param param The order of the parameters starting from 1.
     * @return Returns a parameter. Must be cast with a given type.
     */
    public Object getParam(final Integer param) {
        if (param == 1) {
            return this.param01;
        }
        if (param == 2) {
            return this.param02;
        }
        throw new IllegalArgumentException("Maxmum parameter number for this function is " + this.paramNum + " (start from 1).");
    }
}
