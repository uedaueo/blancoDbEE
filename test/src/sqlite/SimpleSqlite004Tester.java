package sqlite;

import my.db.query.SampleSQLite004Iterator;
import my.db.row.SampleSQLite004Row;
import my.db.util.BlancoDbDynamicOrderBy;
import my.db.util.BlancoDbDynamicParameter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class SimpleSqlite004Tester {
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        final Connection conn = DriverManager.getConnection("jdbc:sqlite:./test/data/sqlite/sqlite.db");
        conn.setAutoCommit(false);

        final SampleSQLite004Iterator ite = new SampleSQLite004Iterator(conn);

        BlancoDbDynamicParameter<Double> betweenNumeric =  new BlancoDbDynamicParameter<>();
        betweenNumeric.setKey("betweenNumeric");
        betweenNumeric.setValues(new ArrayList<>());
        List<Double> btw = betweenNumeric.getValues();
        btw.add(0.0);
        btw.add(1.0);

        // between ? AND ?

        BlancoDbDynamicParameter<Long> inId = new BlancoDbDynamicParameter<>();
        inId.setKey("inId");
        inId.setValues(new ArrayList<>());
        List<Long> ids = inId.getValues();
        ids.add(5000L);
        ids.add(5001L);

        // IN (? ,?, ?)

        BlancoDbDynamicParameter<String> compText = new BlancoDbDynamicParameter<>();
        compText.setKey("compTextLike");
        compText.setValues(new ArrayList<>());
        List<String> s = compText.getValues();
        s.add("%Field%");

        // COL_ID = ?
        // COL_ID LIKE ? -- '%Field%'

        BlancoDbDynamicParameter<BlancoDbDynamicOrderBy> orderbyColumns = new BlancoDbDynamicParameter<>();
        orderbyColumns.setKey("orderbyColumns");
        orderbyColumns.setValues(new ArrayList<>());
        List<BlancoDbDynamicOrderBy> o = orderbyColumns.getValues();
        o.add(new BlancoDbDynamicOrderBy("COL_TEXT", "ASC"));
        o.add(new BlancoDbDynamicOrderBy("COL_ID", "DESC"));

        // order by COL_TEXT, COL_ID

        ite.setInputParameter(
                "%",
                12.345,
                betweenNumeric,
                inId,
                compText,
                orderbyColumns
        );

        ite.executeQuery();
        List<SampleSQLite004Row> rows = ite.getList(6000);

        for (SampleSQLite004Row row : rows) {
            int col_id = row.getColId();
            String col_text = row.getColText();
            BigDecimal col_numeric = row.getColNumeric();

            System.out.println("Search result: [" + col_id + "][" + col_text + "][" + col_numeric + "]");
        }

        ite.close();
        conn.close();
    }
}
