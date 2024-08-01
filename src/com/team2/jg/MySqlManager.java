package com.team2.jg;

import java.sql.*;

public class MySqlManager {

    public static Connection getSQLConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop", "root", "root");
    }

    public static void closeResource(ResultSet rs, PreparedStatement pstmt, Connection conn){

        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
