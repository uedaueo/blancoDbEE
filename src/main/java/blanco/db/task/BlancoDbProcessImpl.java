package blanco.db.task;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import blanco.commons.util.BlancoStringUtil;
import blanco.db.BlancoDbConstants;
import blanco.db.BlancoDbXml2JavaClass;
import blanco.db.common.BlancoDbMeta2Xml;
import blanco.db.common.BlancoDbTableMeta2Xml;
import blanco.db.common.stringgroup.BlancoDbExecuteSqlStringGroup;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.resourcebundle.BlancoDbResourceBundle;
import blanco.db.task.valueobject.BlancoDbProcessInput;

public class BlancoDbProcessImpl implements BlancoDbProcess {
    /**
     * Stores resource bundle accessor objects.
     */
    private final BlancoDbResourceBundle fBundle = new BlancoDbResourceBundle();

    public int execute(final BlancoDbProcessInput input) throws IOException,
            IllegalArgumentException {
        System.out.println("- " + BlancoDbConstants.PRODUCT_NAME + " ("
                + BlancoDbConstants.VERSION + ")");

        try {
            System.out.println("db: begin.");
            final long startMills = System.currentTimeMillis();

            final File blancoTmpDbTableDirectory = new File(input.getTmpdir()
                    + "/db/table");
            final File blancoTmpDbSqlDirectory = new File(input.getTmpdir()
                    + "/db/sql");
            blancoTmpDbTableDirectory.mkdirs();
            blancoTmpDbSqlDirectory.mkdirs();

            final BlancoDbSetting dbSetting = new BlancoDbSetting();
            dbSetting.setTargetStyle(input.getTargetStyle());
            dbSetting.setLineSeparator(input.getLineSeparator());
            dbSetting.setVerbose(input.getVerbose());

            dbSetting.setTargetDir(input.getTargetdir());
            dbSetting.setBasePackage(input.getBasepackage());
            dbSetting.setRuntimePackage(input.getRuntimepackage());

            dbSetting.setJdbcdriver(input.getJdbcdriver());
            dbSetting.setJdbcurl(input.getJdbcurl());
            dbSetting.setJdbcuser(input.getJdbcuser());
            dbSetting.setJdbcpassword(input.getJdbcpassword());
            if (BlancoStringUtil.null2Blank(input.getJdbcdriverfile()).length() > 0) {
                dbSetting.setJdbcdriverfile(input.getJdbcdriverfile());
            }
            dbSetting.setEncoding(input.getEncoding());

            if ("true".equals(input.getConvertStringToMsWindows31jUnicode())) {
                dbSetting.setConvertStringToMsWindows31jUnicode(true);
            }

            if ("true".equals(input.getUseruntime())) {
                dbSetting.setUseRuntime(true);
            }

            dbSetting.setAddIntrospected("true".equals(input.getAddIntrospected()));
            dbSetting.setNoFinalize("true".equals(input.getNoFinalize()));

            // Whether or not to abort the process when an exception occurs in the SQL definition during processing.
            dbSetting.setFailonerror("true".equals(input.getFailonerror()));

            if (input.getLog().equals("true")) {
                dbSetting.setLogging(true);
                dbSetting.setLoggingMode(new BlancoDbLoggingModeStringGroup()
                        .convertToInt(input.getLogmode()));
                if (dbSetting.getLoggingMode() == BlancoDbLoggingModeStringGroup.NOT_DEFINED) {
                    throw new IllegalArgumentException("The value ["
                            + input.getLogmode() + "] specified as logging mode is not supported. Aborts the process.");
                }
            }
			if (input.getLogsql().equals("true")) {
				// Enables highly readable logging.
				dbSetting.setLoggingsql(true);
			}
            if (BlancoStringUtil.null2Blank(input.getStatementtimeout())
                    .length() > 0) {
                try {
                    dbSetting.setStatementTimeout(Integer.parseInt(input
                            .getStatementtimeout()));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException(
                            "The value specified as the statement timeout value ["
                                    + input.getStatementtimeout()
                                    + "] could not be parsed as a numeric value. Aborts the process :"
                                    + ex.toString());
                }
            }
            dbSetting.setExecuteSql(new BlancoDbExecuteSqlStringGroup()
                    .convertToInt(input.getExecutesql()));
            if (dbSetting.getExecuteSql() == BlancoDbExecuteSqlStringGroup.NOT_DEFINED) {
                throw new IllegalArgumentException("An invalid value ("
                        + input.getExecutesql() + ") was given as executesql.");
            }

            if (input.getSchema() != null) {
                // Specifies the schema name.
                dbSetting.setSchema(input.getSchema());
            }

            if (input.getTable() == null || input.getTable().equals("true")) {
                // Auto-generates single table access.
                final BlancoDbTableMeta2Xml tableMeta2Xml = new BlancoDbTableMeta2Xml() {
                    public boolean progress(int progressCurrent,
                            int progressTotal, String progressItem) {
                        // Always returns true.
                        return true;
                    }
                };
                tableMeta2Xml.process(dbSetting, blancoTmpDbTableDirectory);

                // Auto-generates R/O mapping based on XML files.
                final BlancoDbXml2JavaClass generator = new BlancoDbXml2JavaClass() {
                    public boolean progress(int progressCurrent,
                            int progressTotal, String progressItem) {
                        // Always returns true.
                        return true;
                    }
                };

                generator.process(dbSetting, blancoTmpDbTableDirectory);
            }

            if (input.getSql() == null || input.getSql().equals("true")) {
                final File fileMetadir = new File(input.getMetadir());
                if (fileMetadir.exists() == false) {
                    throw new IllegalArgumentException("The meta directory ["
                            + input.getMetadir() + "] does not exist.");
                }

                final BlancoDbMeta2Xml meta2Xml = new BlancoDbMeta2Xml();
                meta2Xml.setCacheMeta2Xml(input.getCache().equals("true"));
                meta2Xml.processDirectory(fileMetadir, blancoTmpDbSqlDirectory
                        .getAbsolutePath());

                // Auto-generates R/O mapping based on XML files.
                final BlancoDbXml2JavaClass generator = new BlancoDbXml2JavaClass() {
                    public boolean progress(int progressCurrent,
                            int progressTotal, String progressItem) {
                        // Always returns true.
                        return true;
                    }
                };
                generator.process(dbSetting, blancoTmpDbSqlDirectory);
            }

            final long endMills = System.currentTimeMillis() - startMills;
            System.out.println("db: end: " + (endMills / 1000) + " sec.");
        } catch (SQLException e) {
            throw new IllegalArgumentException(fBundle.getTaskErr001()
                    + e.toString());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(fBundle.getTaskErr002()
                    + e.toString());
        } catch (SAXException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr003()
                    + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr004()
                    + e.toString());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr005()
                    + e.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr006()
                    + e.toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Input value error :" + e.toString());
        }
        return BlancoDbBatchProcess.END_SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    public boolean progress(final String argProgressMessage) {
        System.out.println(argProgressMessage);
        return false;
    }
}
