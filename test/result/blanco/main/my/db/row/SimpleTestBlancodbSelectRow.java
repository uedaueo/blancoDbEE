package my.db.row;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A row class created from SQL definition (blancoDb).
 *
 * 'SimpleTestBlancodbSelectRow' row is represented.
 * (1) 'COL_ID' column type:int
 * (2) 'COL_NUMERIC' column type:java.math.BigDecimal
 * (3) 'COL_DATE' column type:java.util.Date
 */
public class SimpleTestBlancodbSelectRow {
    /**
     * Field [COL_ID].
     *
     * フィールド: [COL_ID]。
     */
    private int fColId;

    /**
     * Field [COL_NUMERIC].
     *
     * フィールド: [COL_NUMERIC]。
     */
    private BigDecimal fColNumeric;

    /**
     * Field [COL_DATE].
     *
     * フィールド: [COL_DATE]。
     */
    private Date fColDate;

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
     * フィールド [COL_DATE] の値を設定します。
     *
     * フィールドの説明: [Field [COL_DATE].]。
     *
     * @param argColDate フィールド[COL_DATE]に設定する値。
     */
    public void setColDate(final Date argColDate) {
        fColDate = argColDate;
    }

    /**
     * フィールド [COL_DATE] の値を取得します。
     *
     * フィールドの説明: [Field [COL_DATE].]。
     *
     * @return フィールド[COL_DATE]から取得した値。
     */
    public Date getColDate() {
        return fColDate;
    }

    /**
     * Gets the string representation of this value object.
     *
     * <P>Precautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the stringification process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @return String representation of a value object.
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("my.db.row.SimpleTestBlancodbSelectRow[");
        buf.append("COL_ID=" + fColId);
        buf.append(",COL_NUMERIC=" + fColNumeric);
        buf.append(",COL_DATE=" + fColDate);
        buf.append("]");
        return buf.toString();
    }

    /**
     * Copies this value object to the specified target.
     *
     * <P>Cautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the copying process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final SimpleTestBlancodbSelectRow target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: SimpleTestBlancodbSelectRow#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fColId
        // Type: int
        target.fColId = this.fColId;
        // Name: fColNumeric
        // Type: java.math.BigDecimal
        target.fColNumeric = this.fColNumeric;
        // Name: fColDate
        // Type: java.util.Date
        target.fColDate = (this.fColDate == null ? null : new Date(this.fColDate.getTime()));
    }
}
