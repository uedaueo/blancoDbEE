package my.db.row;

import java.math.BigDecimal;

/**
 * A row class created from SQL definition (blancoDb).
 *
 * 'SampleMySQL001Row' row is represented.
 * (1) 'COL_ID' column type:int
 * (2) 'COL_TEXT' column type:java.lang.String
 * (3) 'COL_NUMERIC' column type:java.math.BigDecimal
 */
public class SampleMySQL001Row {
    /**
     * Field [COL_ID].
     *
     * フィールド: [COL_ID]。
     */
    private int fColId;

    /**
     * Field [COL_TEXT].
     *
     * フィールド: [COL_TEXT]。
     */
    private String fColText;

    /**
     * Field [COL_NUMERIC].
     *
     * フィールド: [COL_NUMERIC]。
     */
    private BigDecimal fColNumeric;

    /**
     * フィールド [COL_ID] の値を設定します。
     *
     * フィールドの説明: [Field [COL_ID].]。
     *
     * @param argColId フィールド[COL_ID]に設定する値。
     */
    public void setColId(final int argColId) {
        fColId = argColId;
    }

    /**
     * フィールド [COL_ID] の値を取得します。
     *
     * フィールドの説明: [Field [COL_ID].]。
     *
     * @return フィールド[COL_ID]から取得した値。
     */
    public int getColId() {
        return fColId;
    }

    /**
     * フィールド [COL_TEXT] の値を設定します。
     *
     * フィールドの説明: [Field [COL_TEXT].]。
     *
     * @param argColText フィールド[COL_TEXT]に設定する値。
     */
    public void setColText(final String argColText) {
        fColText = argColText;
    }

    /**
     * フィールド [COL_TEXT] の値を取得します。
     *
     * フィールドの説明: [Field [COL_TEXT].]。
     *
     * @return フィールド[COL_TEXT]から取得した値。
     */
    public String getColText() {
        return fColText;
    }

    /**
     * フィールド [COL_NUMERIC] の値を設定します。
     *
     * フィールドの説明: [Field [COL_NUMERIC].]。
     *
     * @param argColNumeric フィールド[COL_NUMERIC]に設定する値。
     */
    public void setColNumeric(final BigDecimal argColNumeric) {
        fColNumeric = argColNumeric;
    }

    /**
     * フィールド [COL_NUMERIC] の値を取得します。
     *
     * フィールドの説明: [Field [COL_NUMERIC].]。
     *
     * @return フィールド[COL_NUMERIC]から取得した値。
     */
    public BigDecimal getColNumeric() {
        return fColNumeric;
    }

    /**
     * このバリューオブジェクトの文字列表現を取得します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ文字列化の処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @return バリューオブジェクトの文字列表現。
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("my.db.row.SampleMySQL001Row[");
        buf.append("COL_ID=" + fColId);
        buf.append(",COL_TEXT=" + fColText);
        buf.append(",COL_NUMERIC=" + fColNumeric);
        buf.append("]");
        return buf.toString();
    }

    /**
     * このバリューオブジェクトを指定のターゲットに複写します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ複写処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final SampleMySQL001Row target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: SampleMySQL001Row#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fColId
        // Type: int
        target.fColId = this.fColId;
        // Name: fColText
        // Type: java.lang.String
        target.fColText = this.fColText;
        // Name: fColNumeric
        // Type: java.math.BigDecimal
        target.fColNumeric = this.fColNumeric;
    }
}
