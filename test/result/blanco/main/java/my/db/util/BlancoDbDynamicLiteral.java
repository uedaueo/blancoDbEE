/*
 * This code is generated by blanco Framework.
 */
package my.db.util;

/**
 * 動的に生成されるリテラルを定義するクラス。
 * このクラスはblancoDbが生成したソースコードで利用されます <br>
 * @since 2021.05.19
 * @author blanco Framework
 */
public class BlancoDbDynamicLiteral {
    /**
     * 設定されたリテラルを無効化する
     */
    private Boolean invalid = false;

    /**
     * コンストラクタ
     */
    public BlancoDbDynamicLiteral() {
    }

    /**
     * コンストラクタ
     *
     * @param argInvalid 設定されたリテラルを無効化する
     */
    public BlancoDbDynamicLiteral(final Boolean argInvalid) {
        this.invalid = argInvalid;
    }

    /**
     * 設定されたリテラルを無効化する
     *
     * @param argInvalid 設定されたリテラルを無効化する
     */
    public void setInvalid(final Boolean argInvalid) {
        this.invalid = argInvalid;
    }

    /**
     * 設定されたリテラルを無効化する
     *
     * @return 設定されたリテラルを無効化する
     */
    public Boolean getInvalid() {
        return this.invalid;
    }
}
