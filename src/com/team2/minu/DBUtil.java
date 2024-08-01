package com.team2.minu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static final String url = "jdbc:mysql://localhost:3306/movie";
    public static final String user = "root";
    public static final String password = "root";

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("커넥션 생성 오류");
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    System.out.println("close하다가 에러남.");
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
