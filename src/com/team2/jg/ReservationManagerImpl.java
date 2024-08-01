package com.team2.jg;

import com.team2.IReservationManager;
import com.team2.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationManagerImpl implements IReservationManager {

    Connection conn = null;
    String SQL = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    private ReservationManagerImpl() {}
    private static final ReservationManagerImpl instance = new ReservationManagerImpl();

    public static ReservationManagerImpl getInstance() {
        return instance;
    }

    public boolean checkMovieDate(Reservation reservation){
        boolean check = false;

        try {
            String SQL = "SELECT movie_id FROM movie WHERE movie_id = ? AND ? BETWEEN start_date AND end_date";

            PreparedStatement pstmt = conn.prepareStatement(SQL);

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt.setInt(1, reservation.getMovie_id());
            pstmt.setDate(2, Date.valueOf(reservation.getReservation_date()));

            // 4. SQL2 실행하기
            rs = pstmt.executeQuery();

            if(rs.next()) check = true;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            MySqlManager.closeResource(rs,pstmt,conn);
        }

        return check;
    }

    @Override
    public void createReservation(Reservation reservation) {
        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성

            SQL = "insert into reservation(reservation_id, reservation_user_name , movie_id , head_count , reservation_date) values(?,?,?,?,?)";

            if(checkMovieDate(reservation)){
                // 3. PrepareStatement or Statement 에 명령어 담기
                pstmt = conn.prepareStatement(SQL);
                pstmt.setInt(1,reservation.getReservation_id());
                pstmt.setString(2, reservation.getReservation_user_name());
                pstmt.setInt(3, reservation.getMovie_id());
                pstmt.setInt(4, reservation.getHead_count());
                pstmt.setDate(5, Date.valueOf(reservation.getReservation_date()));

                // 4. 실행하기
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //6. 사용한 리소스 반납 (생성의 역순)
            MySqlManager.closeResource(rs,pstmt,conn);
        }
    }

    @Override
    public Reservation getReservation(String userName) {
        Reservation reservation = null;

        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "select reservation_id, reservation_user_name , movie_id , head_count , reservation_date  from reservation where reservation_user_name = ?";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userName);

            // 4. 실행하기
            rs = pstmt.executeQuery();

            if(rs.next()){
                int reservationId = rs.getInt(1);
                String userName_r = rs.getString(2);
                int movieId_r = rs.getInt(3);
                int headCount = rs.getInt(4);
                LocalDate reservationDate = rs.getDate(5).toLocalDate();

                reservation = new Reservation(reservationId, userName_r, movieId_r, headCount, reservationDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //6. 사용한 리소스 반납 (생성의 역순)
            MySqlManager.closeResource(rs,pstmt,conn);
        }

        return reservation;
    }

    @Override
    public List<Reservation> reservationList() {
        List<Reservation> reservationList = new ArrayList<>();

        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "select reservation_id, reservation_user_name , movie_id , head_count , reservation_date  from reservation";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);

            // 4. 실행하기
            rs = pstmt.executeQuery();


            while (rs.next()){
                int reservationId = rs.getInt(1);
                String userName_r = rs.getString(2);
                int movieId_r = rs.getInt(3);
                int headCount = rs.getInt(4);
                LocalDate reservationDate = rs.getDate(5).toLocalDate();

                reservationList.add(new Reservation(reservationId, userName_r, movieId_r, headCount, reservationDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //6. 사용한 리소스 반납 (생성의 역순)
            MySqlManager.closeResource(rs,pstmt,conn);
        }

        return reservationList;
    }

    @Override
    public void deleteReservation(int id) {
        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "DELETE FROM reservation where reservation_id = ?";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, id);

            // 4. 실행하기
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //6. 사용한 리소스 반납 (생성의 역순)
            MySqlManager.closeResource(rs,pstmt,conn);
        }
    }

    @Override
    public void updateReservation(int id, int headCount) {
        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "UPDATE reservation set head_count = ? where reservation_id = ?";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, headCount);
            pstmt.setInt(2, id);

            // 4. 실행하기
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //6. 사용한 리소스 반납 (생성의 역순)
            MySqlManager.closeResource(rs,pstmt,conn);
        }

    }

}




