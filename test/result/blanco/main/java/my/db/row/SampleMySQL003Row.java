package my.db.row;

import java.io.Reader;
import java.math.BigDecimal;

/**
 * SQL定義書(blancoDb)から作成された行クラス。
 *
 * 'SampleMySQL003Row'行を表現します。
 * (1) 'ID'列 型:int
 * (2) 'TEXT'列 型:java.io.Reader
 * (3) 'MyNUMERIC'列 型:java.math.BigDecimal
 */
public class SampleMySQL003Row {
    /**
     * フィールド[ID]です。
     *
     * フィールド: [ID]。
     */
    private int fId;

    /**
     * フィールド[TEXT]です。
     *
     * フィールド: [TEXT]。
     */
    private Reader fText;

    /**
     * フィールド[MyNUMERIC]です。
     *
     * フィールド: [MyNUMERIC]。
     */
    private BigDecimal fMyNUMERIC;

    /**
     * フィールド [ID] の値を設定します。
     *
     * フィールドの説明: [フィールド[ID]です。]。
     *
     * @param argId フィールド[ID]に設定する値。
     */
    public void setId(final int argId) {
        fId = argId;
    }

    /**
     * フィールド [ID] の値を取得します。
     *
     * フィールドの説明: [フィールド[ID]です。]。
     *
     * @return フィールド[ID]から取得した値。
     */
    public int getId() {
        return fId;
    }

    /**
     * フィールド [TEXT] の値を設定します。
     *
     * フィールドの説明: [フィールド[TEXT]です。]。
     *
     * @param argText フィールド[TEXT]に設定する値。
     */
    public void setText(final Reader argText) {
        fText = argText;
    }

    /**
     * フィールド [TEXT] の値を取得します。
     *
     * フィールドの説明: [フィールド[TEXT]です。]。
     *
     * @return フィールド[TEXT]から取得した値。
     */
    public Reader getText() {
        return fText;
    }

    /**
     * フィールド [MyNUMERIC] の値を設定します。
     *
     * フィールドの説明: [フィールド[MyNUMERIC]です。]。
     *
     * @param argMyNUMERIC フィールド[MyNUMERIC]に設定する値。
     */
    public void setMyNUMERIC(final BigDecimal argMyNUMERIC) {
        fMyNUMERIC = argMyNUMERIC;
    }

    /**
     * フィールド [MyNUMERIC] の値を取得します。
     *
     * フィールドの説明: [フィールド[MyNUMERIC]です。]。
     *
     * @return フィールド[MyNUMERIC]から取得した値。
     */
    public BigDecimal getMyNUMERIC() {
        return fMyNUMERIC;
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
        buf.append("my.db.row.SampleMySQL003Row[");
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
    public void copyTo(final SampleMySQL003Row target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: SampleMySQL003Row#copyTo(target): argument 'target' is null");
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
