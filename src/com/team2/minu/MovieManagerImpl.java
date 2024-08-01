package com.team2.minu;

import com.team2.IMovieManager;
import com.team2.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieManagerImpl implements IMovieManager {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public MovieManagerImpl() {}
    private static IMovieManager instance = new MovieManagerImpl();
    public static IMovieManager getInstance() {
        return instance;
    }

    @Override
    public void addMovie(Movie movie) {
        int res = 0;
        try {
            String sql = "insert into movie(title, director, genre, start_date, end_date) values(?, ?, ?, ?, ?);";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDirector());
            ps.setString(3, movie.getGenre());
            ps.setString(4, movie.getStart_date().toString());
            ps.setString(5, movie.getEnd_date().toString());
            res = ps.executeUpdate();

            System.out.println("res = " + res);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    @Override
    public void deleteMovie(int id) {
        try {
            String sql = "delete from movie where movie_id=?;";
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
    public void updateMovie(int id, LocalDate startDate, LocalDate endDate) {
        try {
            String sql = "update movie set start_date=?, end_date=? where movie_id=?;";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, startDate.toString());
            ps.setString(2, endDate.toString());
            ps.setInt(3, id);
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
    public Movie getMovie(String title) {
        Movie movie = null;

        try {
            String sql = "SELECT * FROM movie WHERE title=?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, title);

            rs = ps.executeQuery();
            if (rs.next()) {
                movie = makeMovie(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return movie;
    }

    @Override
    public List<Movie> movieList() {
        List<Movie> movies = new ArrayList<>();
        try {
            String sql = "SELECT movie_id, title, director, genre, start_date, end_date FROM movie";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeQuery();
            rs = ps.getResultSet();

            while (rs.next()) {
                movies.add(makeMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return movies;
    }

    private Movie makeMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setMovie_id(rs.getInt("movie_id"));
        movie.setTitle(rs.getString("title"));
        movie.setDirector(rs.getString("director"));
        movie.setGenre(rs.getString("genre"));
        movie.setStart_date(rs.getDate("start_date").toLocalDate());
        movie.setEnd_date(rs.getDate("end_date").toLocalDate());
        return movie;
    }
}
