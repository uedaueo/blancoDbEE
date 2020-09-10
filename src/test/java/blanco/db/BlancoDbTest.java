/*
 * blanco Framework
 * Copyright (C) 2004-2020 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db;

import blanco.db.task.BlancoDbProcessImpl;
import blanco.db.task.valueobject.BlancoDbProcessInput;
import org.junit.Test;

import java.io.IOException;

/**
 * Kotlin言語用の生成試験。
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoDbTest {

    @Test
    public void testBlancoDb() {

//        <blancodb basepackage="my.db" jdbcdriver="org.sqlite.JDBC" jdbcurl="jdbc:sqlite:.\\test\\data\\sqlite\\sqlite.db" jdbcuser="sqlite" jdbcpassword="password" metadir="test/data/sqlite/" targetdir="test/result/blanco" sql="true" table="true" encoding="Shift-JIS" />

        BlancoDbProcessInput input = new BlancoDbProcessInput();
        input.setBasepackage("my.db");
        input.setJdbcdriver("org.sqlite.JDBC");
        input.setJdbcurl("jdbc:sqlite:./test/data/sqlite/sqlite.db");
        input.setJdbcuser("sqlite");
        input.setJdbcpassword("password");
        input.setMetadir("test/data/sqlite/");
        input.setTargetdir("test/result/blanco");
        input.setSql("true");
        input.setTable("false");
        input.setEncoding("UTF-8");
        input.setLineSeparator("LF");
        input.setTargetStyle("maven");
//        input.setExecutesql("none"); // TODO interator の場合に動的SQLの置換がちゃんとできるようにする。

        BlancoDbProcessImpl imple = new BlancoDbProcessImpl();
        try {
            imple.execute(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMySQLBlancoDb() {

        BlancoDbProcessInput input = new BlancoDbProcessInput();
        input.setBasepackage("my.db");
        input.setJdbcdriver("com.mysql.cj.jdbc.Driver");
        input.setJdbcurl("jdbc:mysql://10.211.55.26:3306/blancoDb");
        input.setJdbcuser("blancodb");
        input.setJdbcpassword("blancodb");
        input.setMetadir("test/data/mysql/");
        input.setTargetdir("test/result/blanco");
        input.setSql("true");
        input.setTable("false");
        input.setEncoding("UTF-8");
        input.setLineSeparator("LF");
        input.setTargetStyle("maven");
//        input.setExecutesql("none"); // TODO interator の場合に動的SQLの置換がちゃんとできるようにする。

        BlancoDbProcessImpl imple = new BlancoDbProcessImpl();
        try {
            imple.execute(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
