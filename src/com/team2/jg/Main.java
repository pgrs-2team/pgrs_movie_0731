package com.team2.jg;

import com.team2.IMovieManager;
import com.team2.IReservationManager;
import com.team2.Movie;
import com.team2.Reservation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // 관리 객체 생성
        IMovieManager movieManager = MovieManagerImpl.getInstance();
        IReservationManager reservationManager = ReservationManagerImpl.getInstance();

        int con;

        int movieId;
        String title;
        String director;
        String genre;
        LocalDate startDate;
        LocalDate endDate;

        Scanner sc = new Scanner(System.in);

        System.out.println("------------- 영화 관리 프로그램 -------------");
        System.out.println("1. 영화 추가");
        System.out.println("2. 전체 영화 조회");
        System.out.println("3. 제목으로 영화 조회");
        System.out.println("4. 영화 수정");
        System.out.println("5. 영화 삭제");
        System.out.println("6. 프로그램 종료");

        do{
            System.out.print("숫자를 입력해 주세요 : ");
            con = Integer.parseInt(sc.nextLine());
            switch (con){
                case 1: {
                    System.out.print("무비 아이디 : ");
                    movieId = Integer.parseInt(sc.nextLine());

                    System.out.print("영화 제목 : ");
                    title = sc.nextLine();

                    System.out.print("영화 감독 : ");
                    director = sc.nextLine();

                    System.out.print("영화 장르 : ");
                    genre = sc.nextLine();

                    System.out.print("영화 상영 시작일 : (ex : 2024-07-31) ");
                    startDate = LocalDate.parse(sc.nextLine());

                    System.out.print("영화 상영 종료일 (ex : 2024-08-31) : ");
                    endDate = LocalDate.parse(sc.nextLine());

                    // 1. 영화 추가
                    movieManager.addMovie(new Movie(movieId, title, director, genre, startDate, endDate));

                    break;
                }
                case 2: {
                    // 2. 영화 조회
                    List<Movie> movieList = movieManager.movieList();
                    movieList.forEach(System.out::println);

                    break;
                }

                case 3: {
                    // 3. 제목으로 영화 조회
                    System.out.print("검색 하고 싶은 영화 제목 : ");
                    title = sc.nextLine();
                    System.out.println(movieManager.getMovie(title));

                    break;
                }

                case 4: {
                    // 4. 영화 수정
                    System.out.print("수정 하고 싶은 영화 id : ");
                    movieId = Integer.parseInt(sc.nextLine());
                    System.out.print("수정된 영화 상영일 : ");
                    startDate = LocalDate.parse(sc.nextLine());
                    System.out.print("수정된 영화 상영 종료일 : ");
                    endDate = LocalDate.parse(sc.nextLine());

                    movieManager.updateMovie(movieId, startDate, endDate);

                    break;
                }

                case 5: {
                    // 5. 영화 삭제
                    System.out.print("삭제 하고 싶은 영화 id : ");
                    movieId = Integer.parseInt(sc.nextLine());

                    movieManager.deleteMovie(movieId);

                    break;
                }

                case 6:{
                    System.out.println("프로그램이 종료됩니다.");

                    break;
                }

                default: {
                    System.out.println("올바른 숫자를 입력해 주세요.");
                }
            }

        } while (con != 6);

        int reservationId;
        String userName;
        int movieId_r;
        int headCount;

        System.out.println("------------- 예약 관리 프로그램 -------------");
        System.out.println("1. 예약 하기");
        System.out.println("2. 전체 예약 조회");
        System.out.println("3. 이름으로 예약 조회");
        System.out.println("4. 예약 수정 (인원수만)");
        System.out.println("5. 예약 삭제");
        System.out.println("6. 프로그램 종료");

        do{
            System.out.print("숫자를 입력해 주세요 : ");
            con = Integer.parseInt(sc.nextLine());
            switch (con){
                case 1: {
                    System.out.print("예약 아이디 : ");
                    reservationId = Integer.parseInt(sc.nextLine());

                    System.out.print("사용자 이름 : ");
                    userName = sc.nextLine();

                    System.out.print("영화 아이디 : ");
                    movieId_r = Integer.parseInt(sc.nextLine());

                    System.out.print("에약 인원 수 : ");
                    headCount =  Integer.parseInt(sc.nextLine());


                    // 1. 예약 추가
                    reservationManager.createReservation(new Reservation(reservationId, userName, movieId_r, headCount, LocalDate.now()));

                    break;
                }
                case 2: {
                    // 2. 예약 조회
                    List<Reservation> reservationList = reservationManager.reservationList();
                    reservationList.forEach(System.out::println);

                    break;
                }

                case 3: {
                    // 3. 사용자로 예약 조회
                    System.out.print("사용자 이름으로 예약 조회 : ");
                    userName = sc.nextLine();
                    System.out.println(reservationManager.getReservation(userName));

                    break;
                }

                case 4: {
                    // 4. 예약 수정
                    System.out.print("수정 하고 싶은 예약 id : ");
                    reservationId = Integer.parseInt(sc.nextLine());
                    System.out.print("수정된 인원 수 : ");
                    headCount = Integer.parseInt(sc.nextLine());

                    reservationManager.updateReservation(reservationId, headCount);

                    break;
                }

                case 5: {
                    // 5. 예약 삭제
                    System.out.print("삭제 하고 싶은 예약 id : ");
                    reservationId = Integer.parseInt(sc.nextLine());

                    reservationManager.deleteReservation(reservationId);

                    break;
                }

                case 6:{
                    System.out.println("프로그램이 종료됩니다.");

                    break;
                }

                default: {
                    System.out.println("올바른 숫자를 입력해 주세요.");
                }
            }

        } while (con != 6);
    }
}
