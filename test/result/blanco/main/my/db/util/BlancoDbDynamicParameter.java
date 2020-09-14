/*
 * This code is generated by blanco Framework.
 */
package my.db.util;

import java.util.List;

/**
 * 動的条件句に対するパラメータを定義するクラス。
 * このクラスはblancoDbが生成したソースコードで利用されます <br>
 * @since 2020.09.04
 * @author blanco Framework
 */
public class BlancoDbDynamicParameter<T> {
    /**
     * DynamicClause を引き当てるキー
     */
    private String key;

    /**
     * COMPARE動的条件句タイプを繰り返す際に接続に用いる論理演算子
     */
    private String logicalOperator = "OR";

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
     * COMPARE動的条件句タイプを繰り返す際に接続に用いる論理演算子
     *
     * @param argLogicalOperator COMPARE動的条件句タイプを繰り返す際に接続に用いる論理演算子
     */
    public void setLogicalOperator(final String argLogicalOperator) {
        this.logicalOperator = argLogicalOperator;
    }

    /**
     * COMPARE動的条件句タイプを繰り返す際に接続に用いる論理演算子
     *
     * @return COMPARE動的条件句タイプを繰り返す際に接続に用いる論理演算子
     */
    public String getLogicalOperator() {
        return this.logicalOperator;
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
