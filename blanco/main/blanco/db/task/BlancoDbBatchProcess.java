package blanco.db.task;

import java.io.IOException;

import blanco.db.task.valueobject.BlancoDbProcessInput;

/**
 * Batch process class [BlancoDbBatchProcess].
 *
 * <P>Example of a batch processing call.</P>
 * <code>
 * java -classpath (classpath) blanco.db.task.BlancoDbBatchProcess -help
 * </code>
 */
public class BlancoDbBatchProcess {
    /**
     * Normal end.
     */
    public static final int END_SUCCESS = 0;

    /**
     * Termination due to abnormal input. In the case that java.lang.IllegalArgumentException is raised internally.
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * Termination due to I/O exception. In the case that java.io.IOException is raised internally.
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * Abnormal end. In the case that batch process fails to start or java.lang.Error or java.lang.RuntimeException is raised internally.
     */
    public static final int END_ERROR = 9;

    /**
     * The entry point when executed from the command line.
     *
     * @param args Agruments inherited from the console.
     */
    public static final void main(final String[] args) {
        final BlancoDbBatchProcess batchProcess = new BlancoDbBatchProcess();

        // Arguments for batch process.
        final BlancoDbProcessInput input = new BlancoDbProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldJdbcdriverProcessed = false;
        boolean isFieldJdbcurlProcessed = false;
        boolean isFieldJdbcuserProcessed = false;
        boolean isFieldJdbcpasswordProcessed = false;
        boolean isFieldMetadirProcessed = false;
        boolean isFieldBasepackageProcessed = false;

        // Parses command line arguments.
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-verbose=")) {
                input.setVerbose(Boolean.valueOf(arg.substring(9)).booleanValue());
            } else if (arg.startsWith("-jdbcdriver=")) {
                input.setJdbcdriver(arg.substring(12));
                isFieldJdbcdriverProcessed = true;
            } else if (arg.startsWith("-jdbcurl=")) {
                input.setJdbcurl(arg.substring(9));
                isFieldJdbcurlProcessed = true;
            } else if (arg.startsWith("-jdbcuser=")) {
                input.setJdbcuser(arg.substring(10));
                isFieldJdbcuserProcessed = true;
            } else if (arg.startsWith("-jdbcpassword=")) {
                input.setJdbcpassword(arg.substring(14));
                isFieldJdbcpasswordProcessed = true;
            } else if (arg.startsWith("-jdbcdriverfile=")) {
                input.setJdbcdriverfile(arg.substring(16));
            } else if (arg.startsWith("-metadir=")) {
                input.setMetadir(arg.substring(9));
                isFieldMetadirProcessed = true;
            } else if (arg.startsWith("-tmpdir=")) {
                input.setTmpdir(arg.substring(8));
            } else if (arg.startsWith("-targetdir=")) {
                input.setTargetdir(arg.substring(11));
            } else if (arg.startsWith("-basepackage=")) {
                input.setBasepackage(arg.substring(13));
                isFieldBasepackageProcessed = true;
            } else if (arg.startsWith("-runtimepackage=")) {
                input.setRuntimepackage(arg.substring(16));
            } else if (arg.startsWith("-schema=")) {
                input.setSchema(arg.substring(8));
            } else if (arg.startsWith("-table=")) {
                input.setTable(arg.substring(7));
            } else if (arg.startsWith("-sql=")) {
                input.setSql(arg.substring(5));
            } else if (arg.startsWith("-failonerror=")) {
                input.setFailonerror(arg.substring(13));
            } else if (arg.startsWith("-log=")) {
                input.setLog(arg.substring(5));
            } else if (arg.startsWith("-logmode=")) {
                input.setLogmode(arg.substring(9));
            } else if (arg.startsWith("-logsql=")) {
                input.setLogsql(arg.substring(8));
            } else if (arg.startsWith("-statementtimeout=")) {
                input.setStatementtimeout(arg.substring(18));
            } else if (arg.startsWith("-executesql=")) {
                input.setExecutesql(arg.substring(12));
            } else if (arg.startsWith("-encoding=")) {
                input.setEncoding(arg.substring(10));
            } else if (arg.startsWith("-convertStringToMsWindows31jUnicode=")) {
                input.setConvertStringToMsWindows31jUnicode(arg.substring(36));
            } else if (arg.startsWith("-cache=")) {
                input.setCache(arg.substring(7));
            } else if (arg.startsWith("-useruntime=")) {
                input.setUseruntime(arg.substring(12));
            } else if (arg.startsWith("-targetStyle=")) {
                input.setTargetStyle(arg.substring(13));
            } else if (arg.startsWith("-lineSeparator=")) {
                input.setLineSeparator(arg.substring(15));
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoDbBatchProcess: The input parameter[" + arg + "] was ignored.");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldJdbcdriverProcessed == false) {
            System.out.println("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcdriver] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldJdbcurlProcessed == false) {
            System.out.println("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcurl] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldJdbcuserProcessed == false) {
            System.out.println("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcuser] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldJdbcpasswordProcessed == false) {
            System.out.println("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcpassword] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldMetadirProcessed == false) {
            System.out.println("BlancoDbBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldBasepackageProcessed == false) {
            System.out.println("BlancoDbBatchProcess: Failed to start the process. The required field value[basepackage] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // Returns the exit code.
        // Note: Please note that calling System.exit().
        System.exit(retCode);
    }

    /**
     * A method to describe the specific batch processing contents.
     *
     * This method is used to describe the actual process.
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public int process(final BlancoDbProcessInput input) throws IOException, IllegalArgumentException {
        // Checks the input parameters.
        validateInput(input);

        // If you get a compile error at this point, You may be able to solve it by implementing a BlancoDbProcess interface and creating an BlancoDbProcessImpl class in package blanco.db.task.
        final BlancoDbProcess process = new BlancoDbProcessImpl();

        // Executes the main body of the process.
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * The entry point for instantiating a class and running a batch.
     *
     * This method provides the following specifications.
     * <ul>
     * <li>Checks the contents of the input parameters of the method.
     * <li>Catches exceptions such as IllegalArgumentException, RuntimeException, Error, etc. and converts them to return values.
     * </ul>
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public final int execute(final BlancoDbProcessInput input) throws IllegalArgumentException {
        try {
            // Executes the main body of the batch process.
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoDbBatchProcess: An input exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoDbBatchProcess: An I/O exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoDbBatchProcess: A runtime exception has occurred. Abort the batch process.:" + ex.toString());
            ex.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoDbBatchProcess: A runtime exception has occurred. Abort the batch process.:" + er.toString());
            er.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        }
    }

    /**
     * A method to show an explanation of how to use this batch processing class on the stdout.
     */
    public static final void usage() {
        System.out.println("BlancoDbBatchProcess: Usage:");
        System.out.println("  java blanco.db.task.BlancoDbBatchProcess -verbose=value1 -jdbcdriver=value2 -jdbcurl=value3 -jdbcuser=value4 -jdbcpassword=value5 -jdbcdriverfile=value6 -metadir=value7 -tmpdir=value8 -targetdir=value9 -basepackage=value10 -runtimepackage=value11 -schema=value12 -table=value13 -sql=value14 -failonerror=value15 -log=value16 -logmode=value17 -logsql=value18 -statementtimeout=value19 -executesql=value20 -encoding=value21 -convertStringToMsWindows31jUnicode=value22 -cache=value23 -useruntime=value24 -targetStyle=value25 -lineSeparator=value26");
        System.out.println("    -verbose");
        System.out.println("      explanation[Whether to run in verbose mode.]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -jdbcdriver");
        System.out.println("      explanation[JDBCドライバのクラス名を指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -jdbcurl");
        System.out.println("      explanation[JDBC接続先URLを指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -jdbcuser");
        System.out.println("      explanation[JDBCデータベース接続を行う際のユーザ名を指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -jdbcpassword");
        System.out.println("      explanation[JDBCデータベース接続を行う際のパスワードを指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -jdbcdriverfile");
        System.out.println("      explanation[JDBCドライバの jar ファイル名を指定します。通常は利用しません。]");
        System.out.println("      type[string]");
        System.out.println("    -metadir");
        System.out.println("      explanation[SQL定義メタファイルが格納されているディレクトリを指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -tmpdir");
        System.out.println("      explanation[テンポラリフォルダを指定します。無指定の場合にはカレント直下のtmpフォルダを利用します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[tmp]");
        System.out.println("    -targetdir");
        System.out.println("      explanation[blancoDbがJavaソースコードを出力するディレクトリを指定します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -basepackage");
        System.out.println("      explanation[blancoDbがJavaソースコードを生成する際の基準となるパッケージ名を指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -runtimepackage");
        System.out.println("      explanation[ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。]");
        System.out.println("      type[string]");
        System.out.println("    -schema");
        System.out.println("      explanation[単一表情報を取得する際のスキーマ名。基本的に無指定です。ただしOracleの場合にのみ、ユーザ名を大文字化したものを指定します。Oracleの場合に これを指定しないと、システム表まで検索してしまい不具合が発生するためです。]");
        System.out.println("      type[string]");
        System.out.println("    -table");
        System.out.println("      explanation[trueを設定すると単一表のためのアクセサ・コードを生成します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -sql");
        System.out.println("      explanation[trueを設定するとSQL定義からコードを生成します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -failonerror");
        System.out.println("      explanation[SQL 定義書からソースコード生成に失敗した際に処理を中断します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -log");
        System.out.println("      explanation[trueを設定すると Jakarta Commons用のロギングコードを生成します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -logmode");
        System.out.println("      explanation[ログモードの指定。debug, performance, sqlid のいずれかの値を指定。]");
        System.out.println("      type[string]");
        System.out.println("      default value[debug]");
        System.out.println("    -logsql");
        System.out.println("      explanation[SQL をログで出力するかどうかのフラグ。「log」や「logmode」はトレースレベルのログを吐くが、「logsql」は、より可読性のあるログを出す。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -statementtimeout");
        System.out.println("      explanation[ステートメントのタイムアウト値。SQL文のタイムアウトさせたい値を設定します。setQueryTimeoutに反映されます。無指定の場合にはAPIデフォルト。]");
        System.out.println("      type[string]");
        System.out.println("    -executesql");
        System.out.println("      explanation[ソースコード自動生成時にSQL定義のSQL文を実行するかどうかを設定するフラグ。デフォルトは iterator。iterator:検索型のみSQL文を実行して検証する。none:SQL文は実行しない。]");
        System.out.println("      type[string]");
        System.out.println("      default value[iterator]");
        System.out.println("    -encoding");
        System.out.println("      explanation[自動生成するソースファイルの文字エンコーディングを指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -convertStringToMsWindows31jUnicode");
        System.out.println("      explanation[文字列について、Microsoft Windows 3.1日本語版のユニコードへと変換するかどうか。検索結果に反映されます。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -cache");
        System.out.println("      explanation[定義書メタファイルから中間XMLファイルへの変換をキャッシュで済ますかどうかのフラグ。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -useruntime");
        System.out.println("      explanation[ランタイムを使用してインタフェイスやアノテーションを設定します。]");
        System.out.println("      type[string]");
        System.out.println("      default value[false]");
        System.out.println("    -targetStyle");
        System.out.println("      explanation[出力先フォルダの書式を指定します。<br>\nblanco: [targetdir]/main<br>\nmaven: [targetdir]/main/java<br>\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -lineSeparator");
        System.out.println("      explanation[行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]");
        System.out.println("      type[string]");
        System.out.println("      default value[LF]");
        System.out.println("    -? , -help");
        System.out.println("      explanation[show the usage.]");
    }

    /**
     * A method to check the validity of input parameters for this batch processing class.
     *
     * @param input Input parameters for batch process.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public void validateInput(final BlancoDbProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: Failed to start the process. The input parameter[input] was given as null.");
        }
        if (input.getJdbcdriver() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcdriver] in the input parameter[input] is not set to a value.");
        }
        if (input.getJdbcurl() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcurl] in the input parameter[input] is not set to a value.");
        }
        if (input.getJdbcuser() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcuser] in the input parameter[input] is not set to a value.");
        }
        if (input.getJdbcpassword() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: Failed to start the process. The required field value[jdbcpassword] in the input parameter[input] is not set to a value.");
        }
        if (input.getMetadir() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
        }
        if (input.getBasepackage() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: Failed to start the process. The required field value[basepackage] in the input parameter[input] is not set to a value.");
        }
    }
}
