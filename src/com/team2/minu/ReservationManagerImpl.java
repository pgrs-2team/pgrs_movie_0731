package com.team2.minu;

import com.team2.IReservationManager;
import com.team2.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationManagerImpl implements IReservationManager {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ReservationManagerImpl(){}
    private static IReservationManager instance = new ReservationManagerImpl();
    public static IReservationManager getInstance() {
        return instance;
    }

    @Override
    public void createReservation(Reservation reservation) {
        String reservation_user_name = reservation.getReservation_user_name();
        int movie_id = reservation.getMovie_id();
        int headcount = reservation.getHead_count();
        LocalDate reservation_date = reservation.getReservation_date();

        try {
            String sql = "INSERT INTO reservation (reservation_user_name, movie_id, head_count, reservation_date) values(" +
                    "?," +
                    "(select movie_id from movie where movie_id=? and ? between start_date and end_date)," +
                    "?," +
                    "?" +
                    ")" +
                    ";";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, reservation_user_name);
            ps.setInt(2, movie_id);
            ps.setString(3, String.valueOf(reservation_date));
            ps.setInt(4, headcount);
            ps.setString(5, String.valueOf(reservation_date));

            int res = ps.executeUpdate();

            System.out.println("res = " + res);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    @Override
    public Reservation getReservation(String userName) {
        Reservation reservation = null;
        try {
            String sql = "SELECT * FROM reservation WHERE reservation_user_name=?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userName);

            rs = ps.executeQuery();
            if (rs.next()) {
                return makeReservation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return reservation;
    }

    @Override
    public List<Reservation> reservationList() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String sql = "SELECT reservation_id, reservation_user_name, movie_id, head_count, reservation_date FROM reservation";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeQuery();
            rs = ps.getResultSet();

            while (rs.next()) {
                reservations.add(makeReservation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return reservations;
    }

    @Override
    public void deleteReservation(int id) {
        try {
            String sql = "delete from reservation where reservation_id=?;";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int res = ps.executeUpdate();

            System.out.println("res = " + res);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    @Override
    public void updateReservation(int id, int headCount) {
        try {
            String sql = "update reservation set head_count=? where reservation_id=?;";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, headCount);
            ps.setInt(2, id);
            int res = ps.executeUpdate();

            System.out.println("res = " + res);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    private Reservation makeReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservation_id(rs.getInt("reservation_id"));
        reservation.setReservation_user_name(rs.getString("reservation_user_name"));
        reservation.setMovie_id(rs.getInt("movie_id"));
        reservation.setHead_count(rs.getInt("head_count"));
        reservation.setReservation_date(rs.getDate("reservation_date").toLocalDate());
        return reservation;
    }
}
