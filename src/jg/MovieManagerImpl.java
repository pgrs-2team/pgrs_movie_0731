package jg;

import com.team2.IMovieManager;
import com.team2.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieManagerImpl implements IMovieManager {

    Connection conn = null;
    String SQL = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    private MovieManagerImpl() {}
    private static final MovieManagerImpl instance = new MovieManagerImpl();

    public static MovieManagerImpl getInstance() {
        return instance;
    }

    @Override
    public void addMovie(Movie movie) {

        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "insert into movie(movie_id, title, director, genre, start_date, end_date) values(?,?,?,?,?,?)";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1,movie.getMovie_id());
            pstmt.setString(2, movie.getTitle());
            pstmt.setString(3, movie.getDirector());
            pstmt.setString(4, movie.getGenre());
            pstmt.setDate(5, Date.valueOf(movie.getStart_date()));
            pstmt.setDate(6, Date.valueOf(movie.getEnd_date()));

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
    public void deleteMovie(int id) {
        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "DELETE FROM movie where movie_id = ?";

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
    public void updateMovie(int id, LocalDate startDate, LocalDate endDate) {
        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "UPDATE movie set start_date = ?, end_date = ? where movie_id = ?";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            pstmt.setInt(3, id);

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
    public Movie getMovie(String title) {
        Movie movie =  null;
        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "select movie_id, title, director, genre, start_date, end_date from movie where title = ?";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, title);

            // 4. 실행하기
            rs = pstmt.executeQuery();

            // 5. 결과값 처리
            if(rs.next()){
                int bno = rs.getInt(1);
                String title_d = rs.getString(2);
                String director = rs.getString(3);
                String genre = rs.getString(4);
                LocalDate startDate = rs.getDate(5).toLocalDate();
                LocalDate endDate = rs.getDate(6).toLocalDate();

                movie = new Movie(bno, title_d, director, genre, startDate, endDate);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //6. 사용한 리소스 반납 (생성의 역순)
            MySqlManager.closeResource(rs,pstmt,conn);
        }

        return movie;
    }

    @Override
    public List<Movie> movieList() {
        List<Movie> movieList = new ArrayList<>();

        try {
            // 1. 커넥션 생성
            conn = MySqlManager.getSQLConnection();

            // 2. SQL 작성
            SQL = "select movie_id, title, director, genre, start_date, end_date from movie";

            // 3. PrepareStatement or Statement 에 명령어 담기
            pstmt = conn.prepareStatement(SQL);

            // 4. 실행하기
            rs = pstmt.executeQuery();

            // 5. 결과값 처리
            while (rs.next()){
                int bno = rs.getInt(1);
                String title = rs.getString(2);
                String director = rs.getString(3);
                String genre = rs.getString(4);
                LocalDate startDate = rs.getDate(5).toLocalDate();
                LocalDate endDate = rs.getDate(6).toLocalDate();
                movieList.add(new Movie(bno, title, director, genre, startDate, endDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //6. 사용한 리소스 반납 (생성의 역순)
            MySqlManager.closeResource(rs,pstmt,conn);
        }

        return movieList;
    }

    
}
