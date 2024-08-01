package com.team2.kyunghun;

import com.team2.IMovieManager;
import com.team2.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieManager implements IMovieManager {
    private MovieManager() {}
    private static IMovieManager instance = new MovieManager();
    public static IMovieManager getInstance() {
        return instance;
    }

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public void addMovie(Movie movie) {
        try {
            String SQL = "INSERT INTO movie (title, director, genre, start_date, end_date) values (?,?,?,?,?)";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDirector());
            ps.setString(3, movie.getGenre());
            ps.setDate(4, Date.valueOf(movie.getStart_date()));
            ps.setDate(5, Date.valueOf(movie.getEnd_date()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("add error");
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtill.close(ps, conn);
        }
    }

    @Override
    public void deleteMovie(int id) {
        try {
            String SQL = "DELETE FROM movie WHERE movie_id = ?";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete error");
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtill.close(ps, conn);
        }
    }

    @Override
    public void updateMovie(int id, LocalDate startDate, LocalDate endDate) {
        try {
            String SQL = "UPDATE movie SET start_date = ?, end_date = ? WHERE movie_id = ?";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update error");
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtill.close(ps, conn);
        }
    }

    @Override
    public Movie getMovie(String title) {
        Movie movie = null;
        try {
            String SQL = "SELECT * FROM movie WHERE title = ?";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, title);
            rs = ps.executeQuery();
            if (rs.next()) {
                movie = makeMovieSelect(rs);
            }
        } catch (SQLException e) {
            System.out.println("getMovie error");
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtill.close(rs, ps, conn);
        }
        return movie;
    }


    @Override
    public List<Movie> movieList() {
        List<Movie> list = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM movie";
            conn = DBUtill.getConnection();
            ps = conn.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(makeMovieSelect(rs));
            }
        } catch (SQLException e) {
            System.out.println("getMovieList error");
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtill.close(rs, ps, conn);
        }
        return list;
    }

    private Movie makeMovieSelect(ResultSet rs) throws SQLException {
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