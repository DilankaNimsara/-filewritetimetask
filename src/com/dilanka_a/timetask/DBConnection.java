package com.dilanka_a.timetask;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Dilanka Nimsara
 */
public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.20.217:1521:ceft", "LVMT_DEV2", "password");
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usernew","root","");
            return con;
        } catch (Exception e) {
            return null;
        }
    }

}
