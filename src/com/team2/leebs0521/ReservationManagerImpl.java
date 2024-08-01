package com.team2.leebs0521;

import com.team2.IReservationManager;
import com.team2.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationManagerImpl implements IReservationManager {
    private static ReservationManagerImpl instance;
    private Connection conn;

    private ReservationManagerImpl() {
        conn = DBUtil.getInstance().getConnection();
    }

    public static ReservationManagerImpl getInstance() {
        if (instance == null) {
            instance = new ReservationManagerImpl();
        }
        return instance;
    }

    @Override
    public void createReservation(Reservation reservation) {

        String sql = "INSERT INTO reservation (reservation_user_name, movie_id, head_count, reservation_date) "
                + "SELECT ?,?,?,? FROM movie WHERE movie_id = ? AND ? BETWEEN start_date AND end_date";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, reservation.getReservation_user_name());
            pstmt.setInt(2, reservation.getMovie_id());
            pstmt.setInt(3, reservation.getHead_count());
            pstmt.setDate(4, Date.valueOf(reservation.getReservation_date()));
            pstmt.setInt(5, reservation.getMovie_id());
            pstmt.setDate(6, Date.valueOf(reservation.getReservation_date()));
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
        } catch (SQLException e) {
            System.out.println("insert error");
            e.printStackTrace();
        }
    }

    @Override
    public Reservation getReservation(String userName) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE reservation_user_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getString("reservation_user_name"),
                        rs.getInt("movie_id"),
                        rs.getInt("head_count"),
                        rs.getDate("reservation_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.out.println("select error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> reservationList() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getString("reservation_user_name"),
                        rs.getInt("movie_id"),
                        rs.getInt("head_count"),
                        rs.getDate("reservation_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("select error");
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void deleteReservation(int id) {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete error");
            e.printStackTrace();
        }
    }

    @Override
    public void updateReservation(int id, int headCount) {
        String sql = "UPDATE reservation SET head_count = ? WHERE reservation_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, headCount);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update error");
            e.printStackTrace();
        }
    }
}
