package my.db.row;

import java.io.Reader;
import java.math.BigDecimal;

/**
 * A row class created from SQL definition (blancoDb).
 *
 * 'SampleMySQL004Row' row is represented.
 * (1) 'ID' column type:int
 * (2) 'TEXT' column type:java.io.Reader
 * (3) 'MyNUMERIC' column type:java.math.BigDecimal
 */
public class SampleMySQL004Row {
    /**
     * Field [ID].
     *
     * フィールド: [ID]。
     */
    private int fId;

    /**
     * Field [TEXT].
     *
     * フィールド: [TEXT]。
     */
    private Reader fText;

    /**
     * Field [MyNUMERIC].
     *
     * フィールド: [MyNUMERIC]。
     */
    private BigDecimal fMyNUMERIC;

    /**
     * フィールド [ID] の値を設定します。
     *
     * フィールドの説明: [Field [ID].]。
     *
     * @param argId フィールド[ID]に設定する値。
     */
    public void setId(final int argId) {
        fId = argId;
    }

    /**
     * フィールド [ID] の値を取得します。
     *
     * フィールドの説明: [Field [ID].]。
     *
     * @return フィールド[ID]から取得した値。
     */
    public int getId() {
        return fId;
    }

    /**
     * フィールド [TEXT] の値を設定します。
     *
     * フィールドの説明: [Field [TEXT].]。
     *
     * @param argText フィールド[TEXT]に設定する値。
     */
    public void setText(final Reader argText) {
        fText = argText;
    }

    /**
     * フィールド [TEXT] の値を取得します。
     *
     * フィールドの説明: [Field [TEXT].]。
     *
     * @return フィールド[TEXT]から取得した値。
     */
    public Reader getText() {
        return fText;
    }

    /**
     * フィールド [MyNUMERIC] の値を設定します。
     *
     * フィールドの説明: [Field [MyNUMERIC].]。
     *
     * @param argMyNUMERIC フィールド[MyNUMERIC]に設定する値。
     */
    public void setMyNUMERIC(final BigDecimal argMyNUMERIC) {
        fMyNUMERIC = argMyNUMERIC;
    }

    /**
     * フィールド [MyNUMERIC] の値を取得します。
     *
     * フィールドの説明: [Field [MyNUMERIC].]。
     *
     * @return フィールド[MyNUMERIC]から取得した値。
     */
    public BigDecimal getMyNUMERIC() {
        return fMyNUMERIC;
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
        buf.append("my.db.row.SampleMySQL004Row[");
        buf.append("ID=" + fId);
        buf.append(",TEXT=" + fText);
        buf.append(",MyNUMERIC=" + fMyNUMERIC);
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
    public void copyTo(final SampleMySQL004Row target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: SampleMySQL004Row#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fId
        // Type: int
        target.fId = this.fId;
        // Name: fText
        // Type: java.io.Reader
        // フィールド[fText]はサポート外の型[java.io.Reader]です。
        // Name: fMyNUMERIC
        // Type: java.math.BigDecimal
        target.fMyNUMERIC = this.fMyNUMERIC;
    }
}
