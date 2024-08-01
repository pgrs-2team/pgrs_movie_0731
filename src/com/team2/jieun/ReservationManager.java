package com.team2.jieun;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.team2.IReservationManager;
import com.team2.Reservation;

public class ReservationManager implements IReservationManager {
    @Override
    public void createReservation(Reservation reservation) {
        String sql = "insert into reservation (reservation_id, reservation_user_name, movie_id, head_count, reservation_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getReservation_id());
            pstmt.setString(2, reservation.getReservation_user_name());
            pstmt.setInt(3, reservation.getMovie_id());
            pstmt.setInt(4, reservation.getHead_count());
            pstmt.setDate(5, Date.valueOf(reservation.getReservation_date()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation getReservation(String userName) {
        String sql = "select reservation_id, reservation_user_name, movie_id, head_count, reservation_date  from reservation where reservation_user_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                            rs.getInt("reservation_id"),
                            rs.getString("reservation_user_name"),
                            rs.getInt("movie_id"),
                            rs.getInt("head_count"),
                            rs.getDate("reservation_date").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> reservationList() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select reservation_id, reservation_user_name, movie_id, head_count, reservation_date from reservation";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void deleteReservation(int id) {
        String sql = "delete from reservation where reservation_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReservation(int id, int headCount) {
        String sql = "update reservation set head_count = ? where reservation_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, headCount);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
