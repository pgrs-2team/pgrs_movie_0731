package com.team2.kyunghun;

import com.team2.IReservationManager;
import com.team2.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReserveManager implements IReservationManager {
    private ReserveManager() {}

    private static IReservationManager instance = new ReserveManager();
    public static IReservationManager getInstance() {
        return instance;
    }

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public void createReservation(Reservation reservation) {
        try {
            String SQL = "INSERT INTO reservation (reservation_user_name, movie_id, head_count, reservation_date) "
                    + "SELECT ?,?,?,? FROM movie WHERE movie_id = ? AND ? BETWEEN start_date AND end_date";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, reservation.getReservation_user_name());
            ps.setInt(2, reservation.getMovie_id());
            ps.setInt(3, reservation.getHead_count());
            ps.setDate(4, Date.valueOf(reservation.getReservation_date()));
            ps.setInt(5, reservation.getMovie_id());
            ps.setDate(6, Date.valueOf(reservation.getReservation_date()));

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("예약이 성공적으로 등록되었습니다.");
            } else {
                System.out.println("해당 영화는 현재 상영하고 있지 않습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtill.close(ps, conn);
        }
    }

    @Override
    public Reservation getReservation(String userName) {
        Reservation reservation = null;
        try {
            String SQL = "SELECT * FROM reservation WHERE reservation_user_name = ?";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (rs.next()) {
                reservation = makeReservationSelect(rs);
            }
        } catch (SQLException e) {
            System.out.println("getReservation error");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtill.close(rs, ps, conn);
        }
        return reservation;
    }

    @Override
    public List<Reservation> reservationList() {
        List<Reservation> list = new ArrayList<>();
        try {
            String SQL = "SELECT reservation_id, reservation_user_name, movie_id, head_count, reservation_date FROM reservation ";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(makeReservationSelect(rs));
            }
        } catch (SQLException e) {
            System.out.println("getReservationList error");
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtill.close(rs, ps, conn);
        }
        return list;
    }


    @Override
    public void deleteReservation(int id) {
        try {
            String SQL = "DELETE FROM reservation WHERE reservation_id = ?";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete error");
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally {
            DBUtill.close(ps, conn);
        }
    }

    @Override
    public void updateReservation(int id, int headCount) {
        try {
            String SQL = "UPDATE reservation SET head_count = ? WHERE reservation_id = ?";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, headCount);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update error");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtill.close(ps, conn);
        }
    }

    private Reservation makeReservationSelect(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservation_id(rs.getInt("reservation_id"));
        reservation.setReservation_user_name(rs.getString("reservation_user_name"));
        reservation.setMovie_id(rs.getInt("movie_id"));
        reservation.setHead_count(rs.getInt("head_count"));
        reservation.setReservation_date(rs.getDate("reservation_date").toLocalDate());
        return reservation;
    }
}
