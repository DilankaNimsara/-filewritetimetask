package com.dilanka_a.timetask;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dilanka_a
 */
public class PoolMain {

    public static BufferedWriter BUFFER_WRITER = null;
    public static int REPORT_COUNT = 1;
    public static ArrayList<String> TRANSACTION = new ArrayList<String>();

    public void runMain() {

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        FileWriter fileWriter;
        String fileName;

        long startTime = System.nanoTime();

        String sql = "SELECT ID FROM LVMT_SWT_ST_TRANSACTION";

        try {

            LocalDate date = LocalDate.now();
            fileName = "ID_LIST_" + date + "-REPORT_" + REPORT_COUNT + ".txt";
            fileWriter = new FileWriter("src/com/dilanka_a/timetask/files/" + fileName);

            BUFFER_WRITER = new BufferedWriter(fileWriter);

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                TRANSACTION.add(rs.getString("ID"));
            }

            ExecutorService pool1 = Executors.newFixedThreadPool(10);

            int count;


            for (int i = 0; i < TRANSACTION.size(); i++) {
                Runnable writeFile = new PoolWrite(TRANSACTION.get(i), BUFFER_WRITER);
                pool1.execute(writeFile);
            }

            pool1.shutdown();

            while (!pool1.isTerminated()) {
            }

            REPORT_COUNT++;

            System.out.println("File " + fileName + " created successfully...");

        } catch (Exception e) {
            System.out.println("Error creating file >>>>>>>>> " + e);
        } finally {
            try {
                BUFFER_WRITER.flush();
                BUFFER_WRITER.close();
                TRANSACTION.clear();
                rs.close();
                ps.close();
                connection.close();
            } catch (SQLException | IOException throwables) {
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Execution Time : " + (endTime - startTime) / 50000 + "ms");

    }

}
