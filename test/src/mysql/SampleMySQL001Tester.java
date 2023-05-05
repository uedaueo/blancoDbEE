package mysql;

import my.db.query.SampleMySQL001Iterator;
import my.db.row.SampleMySQL001Row;
import my.db.util.BlancoDbDynamicOrderBy;
import my.db.util.BlancoDbDynamicParameter;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class SampleMySQL001Tester {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final Connection conn = DriverManager.getConnection("jdbc:mysql://10.211.55.29:3306/blancoDb", "blancodb", "blancodb");
        conn.setAutoCommit(false);

        final SampleMySQL001Iterator ite = new SampleMySQL001Iterator(conn);

        BlancoDbDynamicParameter<Double> betweenNumeric =  new BlancoDbDynamicParameter<>();
        betweenNumeric.setKey("betweenNumeric");
        betweenNumeric.setValues(new ArrayList<>());
        List<Double> btw = betweenNumeric.getValues();
        btw.add(10.0);
        btw.add(15.0);

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
        compText.setLogicalOperator("AND");
        List<String> s = compText.getValues();
        s.add("%Field%");
        s.add("%Field%");

        // COL_ID = ?
        // COL_ID LIKE ? -- '%Field%'

        BlancoDbDynamicParameter<BlancoDbDynamicOrderBy> orderbyColumns = new BlancoDbDynamicParameter<>();
        orderbyColumns.setKey("orderbyColumns");
        orderbyColumns.setValues(new ArrayList<>());
        List<BlancoDbDynamicOrderBy> o = orderbyColumns.getValues();
//        o.add(new BlancoDbDynamicOrderBy("COL_ID", "ASC"));
        o.add(new BlancoDbDynamicOrderBy("COL_TEXT", "ASC"));

        // order by COL_TEXT, COL_ID

//        ite.setInputParameter(
//                "%",
//                12.0,
//                betweenNumeric,
//                inId,
//                compText,
//                orderbyColumns
//        );

        ite.setInputParameter(
                "%",
                12.0,
                betweenNumeric,
                inId,
                compText,
                null
        );

        ite.executeQuery();
        List<SampleMySQL001Row> rows = ite.getList(6000);

        for (SampleMySQL001Row row : rows) {
            int col_id = row.getColId();
            Reader col_text = row.getColText();
            char [] buf = new char[1024];
            col_text.read(buf);
            String text = new String(buf);
            BigDecimal col_numeric = row.getColNumeric();

            System.out.println("Search result: [" + col_id + "][" + text + "][" + col_numeric + "]");
        }

        ite.close();
        conn.close();
    }
}
