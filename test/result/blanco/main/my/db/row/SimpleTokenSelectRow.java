package my.db.row;

import java.util.Date;

/**
 * A row class created from SQL definition (blancoDb).
 *
 * 'SimpleTokenSelectRow' row is represented.
 * (1) 'user_id' column type:java.lang.String
 * (2) 'token' column type:java.lang.String
 * (3) 'expired_at' column type:java.util.Date
 * (4) 'created_at' column type:java.util.Date
 * (5) 'updated_at' column type:java.util.Date
 */
public class SimpleTokenSelectRow {
    /**
     * Field [user_id].
     *
     * フィールド: [user_id]。
     */
    private String fUserId;

    /**
     * Field [token].
     *
     * フィールド: [token]。
     */
    private String fToken;

    /**
     * Field [expired_at].
     *
     * フィールド: [expired_at]。
     */
    private Date fExpiredAt;

    /**
     * Field [created_at].
     *
     * フィールド: [created_at]。
     */
    private Date fCreatedAt;

    /**
     * Field [updated_at].
     *
     * フィールド: [updated_at]。
     */
    private Date fUpdatedAt;

    /**
     * フィールド [user_id] の値を設定します。
     *
     * フィールドの説明: [Field [user_id].]。
     *
     * @param argUserId フィールド[user_id]に設定する値。
     */
    public void setUserId(final String argUserId) {
        fUserId = argUserId;
    }

    /**
     * フィールド [user_id] の値を取得します。
     *
     * フィールドの説明: [Field [user_id].]。
     *
     * @return フィールド[user_id]から取得した値。
     */
    public String getUserId() {
        return fUserId;
    }

    /**
     * フィールド [token] の値を設定します。
     *
     * フィールドの説明: [Field [token].]。
     *
     * @param argToken フィールド[token]に設定する値。
     */
    public void setToken(final String argToken) {
        fToken = argToken;
    }

    /**
     * フィールド [token] の値を取得します。
     *
     * フィールドの説明: [Field [token].]。
     *
     * @return フィールド[token]から取得した値。
     */
    public String getToken() {
        return fToken;
    }

    /**
     * フィールド [expired_at] の値を設定します。
     *
     * フィールドの説明: [Field [expired_at].]。
     *
     * @param argExpiredAt フィールド[expired_at]に設定する値。
     */
    public void setExpiredAt(final Date argExpiredAt) {
        fExpiredAt = argExpiredAt;
    }

    /**
     * フィールド [expired_at] の値を取得します。
     *
     * フィールドの説明: [Field [expired_at].]。
     *
     * @return フィールド[expired_at]から取得した値。
     */
    public Date getExpiredAt() {
        return fExpiredAt;
    }

    /**
     * フィールド [created_at] の値を設定します。
     *
     * フィールドの説明: [Field [created_at].]。
     *
     * @param argCreatedAt フィールド[created_at]に設定する値。
     */
    public void setCreatedAt(final Date argCreatedAt) {
        fCreatedAt = argCreatedAt;
    }

    /**
     * フィールド [created_at] の値を取得します。
     *
     * フィールドの説明: [Field [created_at].]。
     *
     * @return フィールド[created_at]から取得した値。
     */
    public Date getCreatedAt() {
        return fCreatedAt;
    }

    /**
     * フィールド [updated_at] の値を設定します。
     *
     * フィールドの説明: [Field [updated_at].]。
     *
     * @param argUpdatedAt フィールド[updated_at]に設定する値。
     */
    public void setUpdatedAt(final Date argUpdatedAt) {
        fUpdatedAt = argUpdatedAt;
    }

    /**
     * フィールド [updated_at] の値を取得します。
     *
     * フィールドの説明: [Field [updated_at].]。
     *
     * @return フィールド[updated_at]から取得した値。
     */
    public Date getUpdatedAt() {
        return fUpdatedAt;
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
        buf.append("my.db.row.SimpleTokenSelectRow[");
        buf.append("user_id=" + fUserId);
        buf.append(",token=" + fToken);
        buf.append(",expired_at=" + fExpiredAt);
        buf.append(",created_at=" + fCreatedAt);
        buf.append(",updated_at=" + fUpdatedAt);
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
    public void copyTo(final SimpleTokenSelectRow target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: SimpleTokenSelectRow#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fUserId
        // Type: java.lang.String
        target.fUserId = this.fUserId;
        // Name: fToken
        // Type: java.lang.String
        target.fToken = this.fToken;
        // Name: fExpiredAt
        // Type: java.util.Date
        target.fExpiredAt = (this.fExpiredAt == null ? null : new Date(this.fExpiredAt.getTime()));
        // Name: fCreatedAt
        // Type: java.util.Date
        target.fCreatedAt = (this.fCreatedAt == null ? null : new Date(this.fCreatedAt.getTime()));
        // Name: fUpdatedAt
        // Type: java.util.Date
        target.fUpdatedAt = (this.fUpdatedAt == null ? null : new Date(this.fUpdatedAt.getTime()));
    }
}
