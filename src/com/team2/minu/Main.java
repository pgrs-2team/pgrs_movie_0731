package com.team2.minu;

import com.team2.IMovieManager;
import com.team2.IReservationManager;
import com.team2.Movie;
import com.team2.Reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        IMovieManager movieRepo = MovieManagerImpl.getInstance();
        IReservationManager reservationRepo = ReservationManagerImpl.getInstance();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean exit = false;
        while (!exit) {
            System.out.println("1. 영화 등록");
            System.out.println("2. 영화 삭제");
            System.out.println("3. 영화 정보 수정");
            System.out.println("4. 제목으로 영화 검색");
            System.out.println("5. 영화 목록 조회");
            System.out.println("6. 예약하기");
            System.out.println("7. 예약 삭제");
            System.out.println("8. 예약 수정");
            System.out.println("9. 예약자 이름으로 예약 검색");
            System.out.println("10. 전체 예약 목록 조회");
            System.out.println("11. 종료");
            System.out.print("기능을 선택하세요: ");
            int sel = Integer.parseInt(br.readLine());
            switch (sel) {
                case 1:
                    System.out.print("제목을 입력하세요: ");
                    String title = br.readLine();
                    System.out.print("감독명을 입력하세요: ");
                    String director = br.readLine();
                    System.out.print("장르명을 입력하세요: ");
                    String genre = br.readLine();
                    System.out.print("상영시작일을 입력하세요: ");
                    LocalDate start_date = LocalDate.parse(br.readLine());
                    System.out.print("상영종료일을 입력하세요: ");
                    LocalDate end_date = LocalDate.parse(br.readLine());
                    Movie movie = new Movie(title, director, genre, start_date, end_date);
                    movieRepo.addMovie(movie);
                    break;
                case 2:
                    System.out.print("삭제할 영화 id를 입력하세요: ");
                    int d_id = Integer.parseInt(br.readLine());
                    movieRepo.deleteMovie(d_id);
                    break;
                case 3:
                    System.out.print("수정할 영화 id를 입력하세요: ");
                    int u_id = Integer.parseInt(br.readLine());
                    System.out.print("상영시작일을 수정하세요: ");
                    LocalDate new_start_date = LocalDate.parse(br.readLine());
                    System.out.print("상영종료일을 수정하세요: ");
                    LocalDate new_end_date = LocalDate.parse(br.readLine());
                    movieRepo.updateMovie(u_id, new_start_date, new_end_date);
                    break;
                case 4:
                    System.out.print("제목을 입력하세요: ");
                    String search = br.readLine();
                    Movie findMovie = movieRepo.getMovie(search);
                    System.out.println(findMovie);
                    break;
                case 5:
                    List<Movie> movies = movieRepo.movieList();
                    for (Movie m : movies) {
                        System.out.println(m);
                    }
                    break;
                case 6:
                    System.out.print("예약자명을 입력하세요: ");
                    String username = br.readLine();
                    System.out.print("영화 id를 입력하세요: ");
                    int movie_id = Integer.parseInt(br.readLine());
                    System.out.print("인원수를 입력하세요: ");
                    int headcount = Integer.parseInt(br.readLine());
                    System.out.print("예약일을 입력하세요: ");
                    LocalDate reservation_date = LocalDate.parse(br.readLine());
                    reservationRepo.createReservation(new Reservation(username, movie_id, headcount, reservation_date));
                    break;
                case 7:
                    System.out.print("삭제할 예약 id를 입력하세요: ");
                    int deleteId = Integer.parseInt(br.readLine());
                    reservationRepo.deleteReservation(deleteId);
                    break;
                case 8:
                    System.out.print("수정할 예약 id를 입력하세요: ");
                    int updateId = Integer.parseInt(br.readLine());
                    System.out.print("수정할 인원수를 입력하세요: ");
                    int new_headcount = Integer.parseInt(br.readLine());
                    reservationRepo.updateReservation(updateId, new_headcount);
                    break;
                case 9:
                    System.out.print("예약자명을 입력하세요: ");
                    String searchUsername = br.readLine();
                    Reservation reservation = reservationRepo.getReservation(searchUsername);
                    System.out.println(reservation);
                    break;
                case 10:
                    List<Reservation> reservations = reservationRepo.reservationList();
                    for (Reservation r : reservations) {
                        System.out.println(r);
                    }
                    break;
                case 11:
                    System.out.println("안녕히 가십시오.");
                    exit = true;
                    break;
            }
        }
    }
}