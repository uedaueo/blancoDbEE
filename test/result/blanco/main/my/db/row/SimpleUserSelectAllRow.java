package my.db.row;

import java.util.Date;

/**
 * A row class created from SQL definition (blancoDb).
 *
 * 'SimpleUserSelectAllRow' row is represented.
 * (1) 'user_id' column type:java.lang.String
 * (2) 'password' column type:java.lang.String
 * (3) 'created_at' column type:java.util.Date
 * (4) 'updated_at' column type:java.util.Date
 */
public class SimpleUserSelectAllRow {
    /**
     * Field [user_id].
     *
     * フィールド: [user_id]。
     */
    private String fUserId;

    /**
     * Field [password].
     *
     * フィールド: [password]。
     */
    private String fPassword;

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
     * フィールド [password] の値を設定します。
     *
     * フィールドの説明: [Field [password].]。
     *
     * @param argPassword フィールド[password]に設定する値。
     */
    public void setPassword(final String argPassword) {
        fPassword = argPassword;
    }

    /**
     * フィールド [password] の値を取得します。
     *
     * フィールドの説明: [Field [password].]。
     *
     * @return フィールド[password]から取得した値。
     */
    public String getPassword() {
        return fPassword;
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
        buf.append("my.db.row.SimpleUserSelectAllRow[");
        buf.append("user_id=" + fUserId);
        buf.append(",password=" + fPassword);
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
    public void copyTo(final SimpleUserSelectAllRow target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: SimpleUserSelectAllRow#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fUserId
        // Type: java.lang.String
        target.fUserId = this.fUserId;
        // Name: fPassword
        // Type: java.lang.String
        target.fPassword = this.fPassword;
        // Name: fCreatedAt
        // Type: java.util.Date
        target.fCreatedAt = (this.fCreatedAt == null ? null : new Date(this.fCreatedAt.getTime()));
        // Name: fUpdatedAt
        // Type: java.util.Date
        target.fUpdatedAt = (this.fUpdatedAt == null ? null : new Date(this.fUpdatedAt.getTime()));
    }
}
