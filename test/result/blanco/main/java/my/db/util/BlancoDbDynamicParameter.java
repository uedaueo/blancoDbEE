/*
 * This code is generated by blanco Framework.
 */
package my.db.util;

import java.util.List;

/**
 * 動的条件句に対するパラメータを定義するクラス。
 * このクラスはblancoDbが生成したソースコードで利用されます <br>
 * このクラスは blancoDbが生成したソースコードから利用されます。直接呼び出すことは推奨されません。
 * @since 2020.09.04
 * @author blanco Framework
 */
public class BlancoDbDynamicParameter<T> {
    /**
     * DynamicClause を引き当てるキー
     */
    private String key;

    /**
     * PreparedStatement のプレースホルダに適用する値
     */
    private List<T> values;

    /**
     * DynamicClause を引き当てるキー
     *
     * @param argKey DynamicClause を引き当てるキー
     */
    public void setKey(final String argKey) {
        this.key = argKey;
    }

    /**
     * DynamicClause を引き当てるキー
     *
     * @return DynamicClause を引き当てるキー
     */
    public String getKey() {
        return this.key;
    }

    /**
     * PreparedStatement のプレースホルダに適用する値
     *
     * @param argValues PreparedStatement のプレースホルダに適用する値
     */
    public void setValues(final List<T> argValues) {
        this.values = argValues;
    }

    /**
     * PreparedStatement のプレースホルダに適用する値
     *
     * @return PreparedStatement のプレースホルダに適用する値
     */
    public List<T> getValues() {
        return this.values;
    }
}
