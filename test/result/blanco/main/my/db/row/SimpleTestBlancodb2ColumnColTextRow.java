package my.db.row;

import java.io.Reader;

/**
 * A row class created from SQL definition (blancoDb).
 *
 * 'SimpleTestBlancodb2ColumnColTextRow' row is represented.
 * (1) 'COL_TEXT' column type:java.io.Reader
 */
public class SimpleTestBlancodb2ColumnColTextRow {
    /**
     * Field [COL_TEXT].
     *
     * フィールド: [COL_TEXT]。
     */
    private Reader fColText;

    /**
     * フィールド [COL_TEXT] の値を設定します。
     *
     * フィールドの説明: [Field [COL_TEXT].]。
     *
     * @param argColText フィールド[COL_TEXT]に設定する値。
     */
    public void setColText(final Reader argColText) {
        fColText = argColText;
    }

    /**
     * フィールド [COL_TEXT] の値を取得します。
     *
     * フィールドの説明: [Field [COL_TEXT].]。
     *
     * @return フィールド[COL_TEXT]から取得した値。
     */
    public Reader getColText() {
        return fColText;
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
        buf.append("my.db.row.SimpleTestBlancodb2ColumnColTextRow[");
        buf.append("COL_TEXT=" + fColText);
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
    public void copyTo(final SimpleTestBlancodb2ColumnColTextRow target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: SimpleTestBlancodb2ColumnColTextRow#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fColText
        // Type: java.io.Reader
        // Field[fColText] is an unsupported type[java.io.Reader].
    }
}
