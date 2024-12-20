package blanco.db.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import blanco.db.task.valueobject.BlancoDbProcessInput;

/**
 * Apache Antタスク [BlancoDb]のクラス。
 *
 * R/Oマッピング自動生成を行う BlancoDb Enterprise Edition のAntTaskです。<br>
 * このクラスでは、Apache Antタスクで一般的に必要なチェックなどのコーディングを肩代わりします。
 * 実際の処理は パッケージ[blanco.db.task]にBlancoDbBatchProcessクラスを作成して記述してください。<br>
 * <br>
 * Antタスクへの組み込み例<br>
 * <pre>
 * &lt;taskdef name=&quot;blancodb&quot; classname=&quot;blanco.db.task.BlancoDbTask">
 *     &lt;classpath&gt;
 *         &lt;fileset dir=&quot;lib&quot; includes=&quot;*.jar&quot; /&gt;
 *         &lt;fileset dir=&quot;lib.ant&quot; includes=&quot;*.jar&quot; /&gt;
 *     &lt;/classpath&gt;
 * &lt;/taskdef&gt;
 * </pre>
 */
public class BlancoDbTask extends Task {
    /**
     * R/Oマッピング自動生成を行う BlancoDb Enterprise Edition のAntTaskです。
     */
    protected BlancoDbProcessInput fInput = new BlancoDbProcessInput();

    /**
     * フィールド [jdbcdriver] に値がセットされたかどうか。
     */
    protected boolean fIsFieldJdbcdriverProcessed = false;

    /**
     * フィールド [jdbcurl] に値がセットされたかどうか。
     */
    protected boolean fIsFieldJdbcurlProcessed = false;

    /**
     * フィールド [jdbcuser] に値がセットされたかどうか。
     */
    protected boolean fIsFieldJdbcuserProcessed = false;

    /**
     * フィールド [jdbcpassword] に値がセットされたかどうか。
     */
    protected boolean fIsFieldJdbcpasswordProcessed = false;

    /**
     * フィールド [jdbcdriverfile] に値がセットされたかどうか。
     */
    protected boolean fIsFieldJdbcdriverfileProcessed = false;

    /**
     * フィールド [metadir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldMetadirProcessed = false;

    /**
     * フィールド [tmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTmpdirProcessed = false;

    /**
     * フィールド [targetdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetdirProcessed = false;

    /**
     * フィールド [basepackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldBasepackageProcessed = false;

    /**
     * フィールド [runtimepackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldRuntimepackageProcessed = false;

    /**
     * フィールド [schema] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSchemaProcessed = false;

    /**
     * フィールド [table] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTableProcessed = false;

    /**
     * フィールド [sql] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSqlProcessed = false;

    /**
     * フィールド [failonerror] に値がセットされたかどうか。
     */
    protected boolean fIsFieldFailonerrorProcessed = false;

    /**
     * フィールド [log] に値がセットされたかどうか。
     */
    protected boolean fIsFieldLogProcessed = false;

    /**
     * フィールド [logmode] に値がセットされたかどうか。
     */
    protected boolean fIsFieldLogmodeProcessed = false;

    /**
     * フィールド [logsql] に値がセットされたかどうか。
     */
    protected boolean fIsFieldLogsqlProcessed = false;

    /**
     * フィールド [statementtimeout] に値がセットされたかどうか。
     */
    protected boolean fIsFieldStatementtimeoutProcessed = false;

    /**
     * フィールド [executesql] に値がセットされたかどうか。
     */
    protected boolean fIsFieldExecutesqlProcessed = false;

    /**
     * フィールド [encoding] に値がセットされたかどうか。
     */
    protected boolean fIsFieldEncodingProcessed = false;

    /**
     * フィールド [convertStringToMsWindows31jUnicode] に値がセットされたかどうか。
     */
    protected boolean fIsFieldConvertStringToMsWindows31jUnicodeProcessed = false;

    /**
     * フィールド [cache] に値がセットされたかどうか。
     */
    protected boolean fIsFieldCacheProcessed = false;

    /**
     * フィールド [useruntime] に値がセットされたかどうか。
     */
    protected boolean fIsFieldUseruntimeProcessed = false;

    /**
     * フィールド [targetStyle] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetStyleProcessed = false;

    /**
     * フィールド [lineSeparator] に値がセットされたかどうか。
     */
    protected boolean fIsFieldLineSeparatorProcessed = false;

    /**
     * フィールド [addIntrospected] に値がセットされたかどうか。
     */
    protected boolean fIsFieldAddIntrospectedProcessed = false;

    /**
     * フィールド [noFinalize] に値がセットされたかどうか。
     */
    protected boolean fIsFieldNoFinalizeProcessed = false;

    /**
     * verboseモードで動作させるかどうか。
     *
     * @param arg verboseモードで動作させるかどうか。
     */
    public void setVerbose(final boolean arg) {
        fInput.setVerbose(arg);
    }

    /**
     * verboseモードで動作させるかどうか。
     *
     * @return verboseモードで動作させるかどうか。
     */
    public boolean getVerbose() {
        return fInput.getVerbose();
    }

    /**
     * Antタスクの[jdbcdriver]アトリビュートのセッターメソッド。
     *
     * 項目番号: 1<br>
     * JDBCドライバのクラス名を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setJdbcdriver(final String arg) {
        fInput.setJdbcdriver(arg);
        fIsFieldJdbcdriverProcessed = true;
    }

    /**
     * Antタスクの[jdbcdriver]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 1<br>
     * JDBCドライバのクラス名を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getJdbcdriver() {
        return fInput.getJdbcdriver();
    }

    /**
     * Antタスクの[jdbcurl]アトリビュートのセッターメソッド。
     *
     * 項目番号: 2<br>
     * JDBC接続先URLを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setJdbcurl(final String arg) {
        fInput.setJdbcurl(arg);
        fIsFieldJdbcurlProcessed = true;
    }

    /**
     * Antタスクの[jdbcurl]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 2<br>
     * JDBC接続先URLを指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getJdbcurl() {
        return fInput.getJdbcurl();
    }

    /**
     * Antタスクの[jdbcuser]アトリビュートのセッターメソッド。
     *
     * 項目番号: 3<br>
     * JDBCデータベース接続を行う際のユーザ名を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setJdbcuser(final String arg) {
        fInput.setJdbcuser(arg);
        fIsFieldJdbcuserProcessed = true;
    }

    /**
     * Antタスクの[jdbcuser]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 3<br>
     * JDBCデータベース接続を行う際のユーザ名を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getJdbcuser() {
        return fInput.getJdbcuser();
    }

    /**
     * Antタスクの[jdbcpassword]アトリビュートのセッターメソッド。
     *
     * 項目番号: 4<br>
     * JDBCデータベース接続を行う際のパスワードを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setJdbcpassword(final String arg) {
        fInput.setJdbcpassword(arg);
        fIsFieldJdbcpasswordProcessed = true;
    }

    /**
     * Antタスクの[jdbcpassword]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 4<br>
     * JDBCデータベース接続を行う際のパスワードを指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getJdbcpassword() {
        return fInput.getJdbcpassword();
    }

    /**
     * Antタスクの[jdbcdriverfile]アトリビュートのセッターメソッド。
     *
     * 項目番号: 5<br>
     * JDBCドライバの jar ファイル名を指定します。通常は利用しません。<br>
     *
     * @param arg セットしたい値
     */
    public void setJdbcdriverfile(final String arg) {
        fInput.setJdbcdriverfile(arg);
        fIsFieldJdbcdriverfileProcessed = true;
    }

    /**
     * Antタスクの[jdbcdriverfile]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 5<br>
     * JDBCドライバの jar ファイル名を指定します。通常は利用しません。<br>
     *
     * @return このフィールドの値
     */
    public String getJdbcdriverfile() {
        return fInput.getJdbcdriverfile();
    }

    /**
     * Antタスクの[metadir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 6<br>
     * SQL定義メタファイルが格納されているディレクトリを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setMetadir(final String arg) {
        fInput.setMetadir(arg);
        fIsFieldMetadirProcessed = true;
    }

    /**
     * Antタスクの[metadir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 6<br>
     * SQL定義メタファイルが格納されているディレクトリを指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getMetadir() {
        return fInput.getMetadir();
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 7<br>
     * テンポラリフォルダを指定します。無指定の場合にはカレント直下のtmpフォルダを利用します。<br>
     *
     * @param arg セットしたい値
     */
    public void setTmpdir(final String arg) {
        fInput.setTmpdir(arg);
        fIsFieldTmpdirProcessed = true;
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 7<br>
     * テンポラリフォルダを指定します。無指定の場合にはカレント直下のtmpフォルダを利用します。<br>
     * デフォルト値[tmp]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTmpdir() {
        return fInput.getTmpdir();
    }

    /**
     * Antタスクの[targetdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 8<br>
     * blancoDbがJavaソースコードを出力するディレクトリを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetdir(final String arg) {
        fInput.setTargetdir(arg);
        fIsFieldTargetdirProcessed = true;
    }

    /**
     * Antタスクの[targetdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 8<br>
     * blancoDbがJavaソースコードを出力するディレクトリを指定します。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetdir() {
        return fInput.getTargetdir();
    }

    /**
     * Antタスクの[basepackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 9<br>
     * blancoDbがJavaソースコードを生成する際の基準となるパッケージ名を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setBasepackage(final String arg) {
        fInput.setBasepackage(arg);
        fIsFieldBasepackageProcessed = true;
    }

    /**
     * Antタスクの[basepackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 9<br>
     * blancoDbがJavaソースコードを生成する際の基準となるパッケージ名を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getBasepackage() {
        return fInput.getBasepackage();
    }

    /**
     * Antタスクの[runtimepackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 10<br>
     * ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。<br>
     *
     * @param arg セットしたい値
     */
    public void setRuntimepackage(final String arg) {
        fInput.setRuntimepackage(arg);
        fIsFieldRuntimepackageProcessed = true;
    }

    /**
     * Antタスクの[runtimepackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 10<br>
     * ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。<br>
     *
     * @return このフィールドの値
     */
    public String getRuntimepackage() {
        return fInput.getRuntimepackage();
    }

    /**
     * Antタスクの[schema]アトリビュートのセッターメソッド。
     *
     * 項目番号: 11<br>
     * 単一表情報を取得する際のスキーマ名。基本的に無指定です。ただしOracleの場合にのみ、ユーザ名を大文字化したものを指定します。Oracleの場合に これを指定しないと、システム表まで検索してしまい不具合が発生するためです。<br>
     *
     * @param arg セットしたい値
     */
    public void setSchema(final String arg) {
        fInput.setSchema(arg);
        fIsFieldSchemaProcessed = true;
    }

    /**
     * Antタスクの[schema]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 11<br>
     * 単一表情報を取得する際のスキーマ名。基本的に無指定です。ただしOracleの場合にのみ、ユーザ名を大文字化したものを指定します。Oracleの場合に これを指定しないと、システム表まで検索してしまい不具合が発生するためです。<br>
     *
     * @return このフィールドの値
     */
    public String getSchema() {
        return fInput.getSchema();
    }

    /**
     * Antタスクの[table]アトリビュートのセッターメソッド。
     *
     * 項目番号: 12<br>
     * trueを設定すると単一表のためのアクセサ・コードを生成します。<br>
     *
     * @param arg セットしたい値
     */
    public void setTable(final String arg) {
        fInput.setTable(arg);
        fIsFieldTableProcessed = true;
    }

    /**
     * Antタスクの[table]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 12<br>
     * trueを設定すると単一表のためのアクセサ・コードを生成します。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTable() {
        return fInput.getTable();
    }

    /**
     * Antタスクの[sql]アトリビュートのセッターメソッド。
     *
     * 項目番号: 13<br>
     * trueを設定するとSQL定義からコードを生成します。<br>
     *
     * @param arg セットしたい値
     */
    public void setSql(final String arg) {
        fInput.setSql(arg);
        fIsFieldSqlProcessed = true;
    }

    /**
     * Antタスクの[sql]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 13<br>
     * trueを設定するとSQL定義からコードを生成します。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getSql() {
        return fInput.getSql();
    }

    /**
     * Antタスクの[failonerror]アトリビュートのセッターメソッド。
     *
     * 項目番号: 14<br>
     * SQL 定義書からソースコード生成に失敗した際に処理を中断します。<br>
     *
     * @param arg セットしたい値
     */
    public void setFailonerror(final String arg) {
        fInput.setFailonerror(arg);
        fIsFieldFailonerrorProcessed = true;
    }

    /**
     * Antタスクの[failonerror]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 14<br>
     * SQL 定義書からソースコード生成に失敗した際に処理を中断します。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getFailonerror() {
        return fInput.getFailonerror();
    }

    /**
     * Antタスクの[log]アトリビュートのセッターメソッド。
     *
     * 項目番号: 15<br>
     * trueを設定すると Jakarta Commons用のロギングコードを生成します。<br>
     *
     * @param arg セットしたい値
     */
    public void setLog(final String arg) {
        fInput.setLog(arg);
        fIsFieldLogProcessed = true;
    }

    /**
     * Antタスクの[log]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 15<br>
     * trueを設定すると Jakarta Commons用のロギングコードを生成します。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getLog() {
        return fInput.getLog();
    }

    /**
     * Antタスクの[logmode]アトリビュートのセッターメソッド。
     *
     * 項目番号: 16<br>
     * ログモードの指定。debug, performance, sqlid のいずれかの値を指定。<br>
     *
     * @param arg セットしたい値
     */
    public void setLogmode(final String arg) {
        fInput.setLogmode(arg);
        fIsFieldLogmodeProcessed = true;
    }

    /**
     * Antタスクの[logmode]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 16<br>
     * ログモードの指定。debug, performance, sqlid のいずれかの値を指定。<br>
     * デフォルト値[debug]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getLogmode() {
        return fInput.getLogmode();
    }

    /**
     * Antタスクの[logsql]アトリビュートのセッターメソッド。
     *
     * 項目番号: 17<br>
     * SQL をログで出力するかどうかのフラグ。「log」や「logmode」はトレースレベルのログを吐くが、「logsql」は、より可読性のあるログを出す。<br>
     *
     * @param arg セットしたい値
     */
    public void setLogsql(final String arg) {
        fInput.setLogsql(arg);
        fIsFieldLogsqlProcessed = true;
    }

    /**
     * Antタスクの[logsql]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 17<br>
     * SQL をログで出力するかどうかのフラグ。「log」や「logmode」はトレースレベルのログを吐くが、「logsql」は、より可読性のあるログを出す。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getLogsql() {
        return fInput.getLogsql();
    }

    /**
     * Antタスクの[statementtimeout]アトリビュートのセッターメソッド。
     *
     * 項目番号: 19<br>
     * ステートメントのタイムアウト値。SQL文のタイムアウトさせたい値を設定します。setQueryTimeoutに反映されます。無指定の場合にはAPIデフォルト。<br>
     *
     * @param arg セットしたい値
     */
    public void setStatementtimeout(final String arg) {
        fInput.setStatementtimeout(arg);
        fIsFieldStatementtimeoutProcessed = true;
    }

    /**
     * Antタスクの[statementtimeout]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 19<br>
     * ステートメントのタイムアウト値。SQL文のタイムアウトさせたい値を設定します。setQueryTimeoutに反映されます。無指定の場合にはAPIデフォルト。<br>
     *
     * @return このフィールドの値
     */
    public String getStatementtimeout() {
        return fInput.getStatementtimeout();
    }

    /**
     * Antタスクの[executesql]アトリビュートのセッターメソッド。
     *
     * 項目番号: 20<br>
     * ソースコード自動生成時にSQL定義のSQL文を実行するかどうかを設定するフラグ。デフォルトは iterator。iterator:検索型のみSQL文を実行して検証する。none:SQL文は実行しない。<br>
     *
     * @param arg セットしたい値
     */
    public void setExecutesql(final String arg) {
        fInput.setExecutesql(arg);
        fIsFieldExecutesqlProcessed = true;
    }

    /**
     * Antタスクの[executesql]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 20<br>
     * ソースコード自動生成時にSQL定義のSQL文を実行するかどうかを設定するフラグ。デフォルトは iterator。iterator:検索型のみSQL文を実行して検証する。none:SQL文は実行しない。<br>
     * デフォルト値[iterator]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getExecutesql() {
        return fInput.getExecutesql();
    }

    /**
     * Antタスクの[encoding]アトリビュートのセッターメソッド。
     *
     * 項目番号: 21<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setEncoding(final String arg) {
        fInput.setEncoding(arg);
        fIsFieldEncodingProcessed = true;
    }

    /**
     * Antタスクの[encoding]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 21<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getEncoding() {
        return fInput.getEncoding();
    }

    /**
     * Antタスクの[convertStringToMsWindows31jUnicode]アトリビュートのセッターメソッド。
     *
     * 項目番号: 22<br>
     * 文字列について、Microsoft Windows 3.1日本語版のユニコードへと変換するかどうか。検索結果に反映されます。<br>
     *
     * @param arg セットしたい値
     */
    public void setConvertStringToMsWindows31jUnicode(final String arg) {
        fInput.setConvertStringToMsWindows31jUnicode(arg);
        fIsFieldConvertStringToMsWindows31jUnicodeProcessed = true;
    }

    /**
     * Antタスクの[convertStringToMsWindows31jUnicode]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 22<br>
     * 文字列について、Microsoft Windows 3.1日本語版のユニコードへと変換するかどうか。検索結果に反映されます。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getConvertStringToMsWindows31jUnicode() {
        return fInput.getConvertStringToMsWindows31jUnicode();
    }

    /**
     * Antタスクの[cache]アトリビュートのセッターメソッド。
     *
     * 項目番号: 23<br>
     * 定義書メタファイルから中間XMLファイルへの変換をキャッシュで済ますかどうかのフラグ。<br>
     *
     * @param arg セットしたい値
     */
    public void setCache(final String arg) {
        fInput.setCache(arg);
        fIsFieldCacheProcessed = true;
    }

    /**
     * Antタスクの[cache]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 23<br>
     * 定義書メタファイルから中間XMLファイルへの変換をキャッシュで済ますかどうかのフラグ。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getCache() {
        return fInput.getCache();
    }

    /**
     * Antタスクの[useruntime]アトリビュートのセッターメソッド。
     *
     * 項目番号: 24<br>
     * ランタイムを使用してインタフェイスやアノテーションを設定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setUseruntime(final String arg) {
        fInput.setUseruntime(arg);
        fIsFieldUseruntimeProcessed = true;
    }

    /**
     * Antタスクの[useruntime]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 24<br>
     * ランタイムを使用してインタフェイスやアノテーションを設定します。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getUseruntime() {
        return fInput.getUseruntime();
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのセッターメソッド。
     *
     * 項目番号: 25<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetStyle(final String arg) {
        fInput.setTargetStyle(arg);
        fIsFieldTargetStyleProcessed = true;
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 25<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetStyle() {
        return fInput.getTargetStyle();
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのセッターメソッド。
     *
     * 項目番号: 26<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     *
     * @param arg セットしたい値
     */
    public void setLineSeparator(final String arg) {
        fInput.setLineSeparator(arg);
        fIsFieldLineSeparatorProcessed = true;
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 26<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     * デフォルト値[LF]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getLineSeparator() {
        return fInput.getLineSeparator();
    }

    /**
     * Antタスクの[addIntrospected]アトリビュートのセッターメソッド。
     *
     * 項目番号: 27<br>
     * micronaut向けに Row クラスに Introspected アノテーションを付与します<br>
     *
     * @param arg セットしたい値
     */
    public void setAddIntrospected(final String arg) {
        fInput.setAddIntrospected(arg);
        fIsFieldAddIntrospectedProcessed = true;
    }

    /**
     * Antタスクの[addIntrospected]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 27<br>
     * micronaut向けに Row クラスに Introspected アノテーションを付与します<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getAddIntrospected() {
        return fInput.getAddIntrospected();
    }

    /**
     * Antタスクの[noFinalize]アトリビュートのセッターメソッド。
     *
     * 項目番号: 28<br>
     * finalizeメソッドを生成しません。Java9以降は非推奨となっているのでデフォルト値を True とします。<br>
     *
     * @param arg セットしたい値
     */
    public void setNoFinalize(final String arg) {
        fInput.setNoFinalize(arg);
        fIsFieldNoFinalizeProcessed = true;
    }

    /**
     * Antタスクの[noFinalize]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 28<br>
     * finalizeメソッドを生成しません。Java9以降は非推奨となっているのでデフォルト値を True とします。<br>
     * デフォルト値[true]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getNoFinalize() {
        return fInput.getNoFinalize();
    }

    /**
     * Antタスクのメイン処理。Apache Antから このメソッドが呼び出されます。
     *
     * @throws BuildException タスクとしての例外が発生した場合。
     */
    @Override
    public final void execute() throws BuildException {
        System.out.println("BlancoDbTask begin.");

        // 項目番号[1], アトリビュート[jdbcdriver]は必須入力です。入力チェックを行います。
        if (fIsFieldJdbcdriverProcessed == false) {
            throw new BuildException("必須アトリビュート[jdbcdriver]が設定されていません。処理を中断します。");
        }
        // 項目番号[2], アトリビュート[jdbcurl]は必須入力です。入力チェックを行います。
        if (fIsFieldJdbcurlProcessed == false) {
            throw new BuildException("必須アトリビュート[jdbcurl]が設定されていません。処理を中断します。");
        }
        // 項目番号[3], アトリビュート[jdbcuser]は必須入力です。入力チェックを行います。
        if (fIsFieldJdbcuserProcessed == false) {
            throw new BuildException("必須アトリビュート[jdbcuser]が設定されていません。処理を中断します。");
        }
        // 項目番号[4], アトリビュート[jdbcpassword]は必須入力です。入力チェックを行います。
        if (fIsFieldJdbcpasswordProcessed == false) {
            throw new BuildException("必須アトリビュート[jdbcpassword]が設定されていません。処理を中断します。");
        }
        // 項目番号[6], アトリビュート[metadir]は必須入力です。入力チェックを行います。
        if (fIsFieldMetadirProcessed == false) {
            throw new BuildException("必須アトリビュート[metadir]が設定されていません。処理を中断します。");
        }
        // 項目番号[9], アトリビュート[basepackage]は必須入力です。入力チェックを行います。
        if (fIsFieldBasepackageProcessed == false) {
            throw new BuildException("必須アトリビュート[basepackage]が設定されていません。処理を中断します。");
        }

        if (getVerbose()) {
            System.out.println("- verbose:[true]");
            System.out.println("- jdbcdriver:[" + getJdbcdriver() + "]");
            System.out.println("- jdbcurl:[" + getJdbcurl() + "]");
            System.out.println("- jdbcuser:[" + getJdbcuser() + "]");
            System.out.println("- jdbcpassword:[" + getJdbcpassword() + "]");
            System.out.println("- jdbcdriverfile:[" + getJdbcdriverfile() + "]");
            System.out.println("- metadir:[" + getMetadir() + "]");
            System.out.println("- tmpdir:[" + getTmpdir() + "]");
            System.out.println("- targetdir:[" + getTargetdir() + "]");
            System.out.println("- basepackage:[" + getBasepackage() + "]");
            System.out.println("- runtimepackage:[" + getRuntimepackage() + "]");
            System.out.println("- schema:[" + getSchema() + "]");
            System.out.println("- table:[" + getTable() + "]");
            System.out.println("- sql:[" + getSql() + "]");
            System.out.println("- failonerror:[" + getFailonerror() + "]");
            System.out.println("- log:[" + getLog() + "]");
            System.out.println("- logmode:[" + getLogmode() + "]");
            System.out.println("- logsql:[" + getLogsql() + "]");
            System.out.println("- statementtimeout:[" + getStatementtimeout() + "]");
            System.out.println("- executesql:[" + getExecutesql() + "]");
            System.out.println("- encoding:[" + getEncoding() + "]");
            System.out.println("- convertStringToMsWindows31jUnicode:[" + getConvertStringToMsWindows31jUnicode() + "]");
            System.out.println("- cache:[" + getCache() + "]");
            System.out.println("- useruntime:[" + getUseruntime() + "]");
            System.out.println("- targetStyle:[" + getTargetStyle() + "]");
            System.out.println("- lineSeparator:[" + getLineSeparator() + "]");
            System.out.println("- addIntrospected:[" + getAddIntrospected() + "]");
            System.out.println("- noFinalize:[" + getNoFinalize() + "]");
        }

        try {
            // 実際のAntタスクの主処理を実行します。
            // If you get a compile error at this point, You may be able to solve it by implementing a BlancoDbProcess interface and creating an BlancoDbProcessImpl class in package blanco.db.task.
            final BlancoDbProcess proc = new BlancoDbProcessImpl();
            if (proc.execute(fInput) != BlancoDbBatchProcess.END_SUCCESS) {
                throw new BuildException("The task has terminated abnormally.");
            }
        } catch (IllegalArgumentException e) {
            if (getVerbose()) {
                e.printStackTrace();
            }
            throw new BuildException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中に例外が発生しました。処理を中断します。" + e.toString());
        } catch (Error e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中にエラーが発生しました。処理を中断します。" + e.toString());
        }
    }
}
