package mysql;

import my.db.query.SampleMySQL001Iterator;
import my.db.query.SampleMySQL003Iterator;
import my.db.row.SampleMySQL001Row;
import my.db.row.SampleMySQL003Row;
import my.db.util.BlancoDbDynamicLiteral;
import my.db.util.BlancoDbDynamicOrderBy;
import my.db.util.BlancoDbDynamicParameter;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class SampleMySQL003Tester {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final Connection conn = DriverManager.getConnection("jdbc:mysql://10.211.55.29:3306/blancoDb", "blancodb", "blancodb");
        conn.setAutoCommit(false);

        final SampleMySQL003Iterator ite = new SampleMySQL003Iterator(conn);

        // between ? AND ?

        BlancoDbDynamicParameter<Double> betweenNumeric =  new BlancoDbDynamicParameter<>();
        betweenNumeric.setKey("betweenNumeric");
        betweenNumeric.setValues(new ArrayList<>());
        List<Double> btw = betweenNumeric.getValues();
        btw.add(10.0);
        btw.add(15.0);

        // IN (? ,?, ?)

        BlancoDbDynamicParameter<Long> inId = new BlancoDbDynamicParameter<>();
        inId.setKey("inId");
        inId.setValues(new ArrayList<>());
        List<Long> ids = inId.getValues();
        ids.add(5000L);
        ids.add(5001L);

        // COL_ID = ?
        // COL_ID LIKE ? -- '%Field%'

        BlancoDbDynamicParameter<String> compText = new BlancoDbDynamicParameter<>();
        compText.setKey("compTextLike");
        compText.setValues(new ArrayList<>());
        compText.setLogicalOperator("AND");
        List<String> s = compText.getValues();
        s.add("%Field%");
        s.add("%Field%");

        // order by COL_TEXT, COL_ID

        BlancoDbDynamicParameter<BlancoDbDynamicOrderBy> orderbyColumns = new BlancoDbDynamicParameter<>();
        orderbyColumns.setKey("orderbyColumns");
        orderbyColumns.setValues(new ArrayList<>());
        List<BlancoDbDynamicOrderBy> o = orderbyColumns.getValues();
//        o.add(new BlancoDbDynamicOrderBy("COL_ID", "ASC"));
        o.add(new BlancoDbDynamicOrderBy("TEXT", "ASC"));

        // JOIN LITERAL
        BlancoDbDynamicParameter<BlancoDbDynamicLiteral> joinLiteral = new BlancoDbDynamicParameter<>();
        joinLiteral.setKey("joinLiteral");
        joinLiteral.setValues(new ArrayList<>());
        List<BlancoDbDynamicLiteral> l = joinLiteral.getValues();
        l.add(new BlancoDbDynamicLiteral(true));

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
                orderbyColumns,
                joinLiteral
        );

        ite.executeQuery();
        List<SampleMySQL003Row> rows = ite.getList(6000);

        for (SampleMySQL003Row row : rows) {
            int col_id = row.getId();
            Reader col_text = row.getText();
            char [] buf = new char[10];
            col_text.read(buf);
            String text = new String(buf);
            BigDecimal col_numeric = row.getMyNUMERIC();

            System.out.println("Search result: [" + col_id + "][" + text + "][" + col_numeric + "]");
        }

        ite.close();
        conn.close();
    }
}
