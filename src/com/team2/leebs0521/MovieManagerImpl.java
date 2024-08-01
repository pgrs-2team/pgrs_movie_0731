package com.team2.leebs0521;

import com.team2.IMovieManager;
import com.team2.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieManagerImpl implements IMovieManager {
    private static MovieManagerImpl instance;
    private Connection conn;

    private MovieManagerImpl() {
        conn = DBUtil.getInstance().getConnection();
    }

    public static MovieManagerImpl getInstance() {
        if (instance == null) {
            instance = new MovieManagerImpl();
        }
        return instance;
    }

    @Override
    public void addMovie(Movie movie) {
        String sql = "INSERT INTO movie (title, director, genre, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDirector());
            pstmt.setString(3, movie.getGenre());
            pstmt.setDate(4, Date.valueOf(movie.getStart_date()));
            pstmt.setDate(5, Date.valueOf(movie.getEnd_date()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert error");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMovie(int id) {
        String sql = "DELETE FROM movie WHERE movie_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete error");
            e.printStackTrace();
        }
    }

    @Override
    public void updateMovie(int id, LocalDate startDate, LocalDate endDate) {
        String sql = "UPDATE movie SET start_date = ?, end_date = ? WHERE movie_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update error");
            e.printStackTrace();
        }
    }

    @Override
    public Movie getMovie(String title) {
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie WHERE title=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getString("genre"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.out.println("select error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Movie> movieList() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getString("genre"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("select error");
            e.printStackTrace();
        }
        return movies;
    }
}
