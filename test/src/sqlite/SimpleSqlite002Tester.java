package sqlite;

import my.db.query.SampleSQLite001Iterator;
import my.db.query.SampleSQLite002Iterator;
import my.db.row.SampleSQLite001Row;
import my.db.util.BlancoDbDynamicParameter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SimpleSqlite002Tester {
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        final Connection conn = DriverManager.getConnection("jdbc:sqlite:./test/data/sqlite/sqlite.db");
        conn.setAutoCommit(false);

        final SampleSQLite002Iterator ite = new SampleSQLite002Iterator(conn);

        BlancoDbDynamicParameter<Double> betweenNumeric =  new BlancoDbDynamicParameter<>();
        betweenNumeric.setKey("betweenNumeric");
        betweenNumeric.setValues(new ArrayList<>());
        List<Double> btw = betweenNumeric.getValues();
        btw.add(10.0);
        btw.add(15.0);

        BlancoDbDynamicParameter<Long> inId = new BlancoDbDynamicParameter<>();
        inId.setKey("inId");
        inId.setValues(new ArrayList<>());
        List<Long> ids = inId.getValues();
        ids.add(5000L);
        ids.add(5001L);

        BlancoDbDynamicParameter<String> compText = new BlancoDbDynamicParameter<>();
        compText.setKey("compTextLike");
        compText.setValues(new ArrayList<>());
        List<String> s = compText.getValues();
        s.add("%Field%");

        BlancoDbDynamicParameter<String> orderbyColumns = new BlancoDbDynamicParameter<>();
        orderbyColumns.setKey("orderbyColumns");
        orderbyColumns.setValues(new ArrayList<>());
        List<String> o = orderbyColumns.getValues();
        o.add("COL_ID");
        o.add("COL_TEXT");

        String query = ite.setInputParameter(
                "%",
                betweenNumeric,
                inId,
                compText,
                orderbyColumns,
                12.345
        );

        System.out.println("query = " + query);

        ite.executeQuery();
        ResultSet result = ite.getResultSet();

        while (result.next()) {
            int col_id = result.getInt("COL_ID");
            String col_text = result.getString("COL_TEXT");
            Double col_numeric = result.getDouble("COL_NUMERIC");

            System.out.println("Search result: [" + col_id + "][" + col_text + "][" + col_numeric + "]");
        }

        ite.close();

        conn.close();
    }
}
