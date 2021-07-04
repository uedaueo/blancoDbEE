package my.db.query;

public class SampleMySQL004FuncLiteralInput {
    final private int paramNum = 2;
    private java.lang.Long param01;
    private java.lang.String param02;

    public SampleMySQL004FuncLiteralInput(java.lang.Long argParam01, java.lang.String argParam02) {
        this.param01 = argParam01;
        this.param02 = argParam02;
    }

    public Object getParam(int param) {
        if (param == 1) {
            return this.param01;
        } else if (param == 2) {
            return this.param02;
        }
        throw new IllegalArgumentException("Maxmum parameter number for this function is " + this.paramNum + " (start from 1).");
    }
}
