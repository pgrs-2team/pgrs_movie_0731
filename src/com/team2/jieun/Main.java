package com.team2.jieun;

import com.team2.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IMovieManager movieManager = new MovieManager();
    private static final IReservationManager reservationManager = new ReservationManager();

    public static void main(String[] args) throws IOException, SQLException {
        int mainnum = 0;
        while (mainnum != 3) {
            showMainMenu();
            mainnum = getIntInput("관리할 메뉴를 선택하세요 (번호 입력): ");
            if (mainnum == 1) {
                manageMovies();
            } else if (mainnum == 2) {
                manageReservations();
            } else if (mainnum == 3) {
                System.out.println("프로그램이 종료됩니다.");
            } else {
                System.out.println("1, 2, 3 중에 선택 !");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("======== 관리 메뉴 선택 ========");
        System.out.println("1. 영화 관리");
        System.out.println("2. 예약 관리");
        System.out.println("3. 종료");
    }

    private static void manageMovies() {
        int cho = 0;
        while (cho != 6) {
            showMovieMenu();
            cho = getIntInput("영화 관리 메뉴 숫자를 선택: ");
            if (cho == 1) {
                addMovie();
            } else if (cho == 2) {
                listMovies();
            } else if (cho == 3) {
                getMovieByTitle();
            } else if (cho == 4) {
                updateMovie();
            } else if (cho == 5) {
                deleteMovie();
            } else if (cho == 6) {
                // 돌아가기
            } else {
                System.out.println("1~6 중에 선택 !");
            }
        }
    }

    private static void showMovieMenu() {
        System.out.println("======== 영화 관리 메뉴 ========");
        System.out.println("1. 영화 추가하기");
        System.out.println("2. 전체 영화 조회하기");
        System.out.println("3. 영화 조회하기 (제목입력)");
        System.out.println("4. 영화 수정하기 (날짜 수정)");
        System.out.println("5. 영화 삭제하기");
        System.out.println("6. 뒤로가기");
    }

    private static void addMovie() {
        int movieId = getIntInput("movieId: ");
        String title = getStringInput("추가할 영화 제목 입력: ");
        String director = getStringInput("추가할 영화의 감동명 입력: ");
        String genre = getStringInput("추가할 영화 장르 입력: ");
        LocalDate startDate = getDateInput("상영 시작일 입력: ");
        LocalDate endDate = getDateInput("상영 종료일 입력: ");

        movieManager.addMovie(new Movie(movieId, title, director, genre, startDate, endDate));
    }

    private static void listMovies() {
        List<Movie> mlist = movieManager.movieList();
        if (mlist.isEmpty()) {
            System.out.println("등록된 영화가 없음");
        } else {
            for (Movie movie : mlist) {
                System.out.println(movie);
            }
        }
    }

    private static void getMovieByTitle() {
        String title = getStringInput("검색할 영화 제목 입력: ");
        Movie movie = movieManager.getMovie(title);
        if (movie == null) {
            System.out.println("입력한 영화 존재x");
        } else {
            System.out.println(movie);
        }
    }

    private static void updateMovie() {
        int movieId = getIntInput("수정할 영화 번호 입력: ");
        LocalDate startDate = getDateInput("영화 상영 시작일 입력 (수정할 날짜): ");
        LocalDate endDate = getDateInput("영화 상영 종료일 입력 (수정할 날짜): ");

        movieManager.updateMovie(movieId, startDate, endDate);
    }

    private static void deleteMovie() {
        int movieId = getIntInput("삭제할 영화 아이디 입력: ");
        movieManager.deleteMovie(movieId);
    }

    private static void manageReservations() {
        int cho = 0;
        while (cho != 6) {
            showReservationMenu();
            cho = getIntInput("예약 관리 메뉴 숫자 선택: ");
            if (cho == 1) {
                createReservation();
            } else if (cho == 2) {
                listReservations();
            } else if (cho == 3) {
                getReservationByUserName();
            } else if (cho == 4) {
                updateReservation();
            } else if (cho == 5) {
                deleteReservation();
            } else if (cho == 6) {
                // 돌아가기
            } else {
                System.out.println("1~6까지의 숫자를 입력");
            }
        }
    }

    private static void showReservationMenu() {
        System.out.println("======== 예약 관리 메뉴 ========");
        System.out.println("1. 예약 추가하기");
        System.out.println("2. 전체 예약 조회하기");
        System.out.println("3. 이름으로 예약 조회하기");
        System.out.println("4. 예약 수정하기 (인원 수정)");
        System.out.println("5. 예약 삭제하기");
        System.out.println("6. 돌아가기");
    }

    private static void createReservation() {
        int reservationId = getIntInput("예약 아이디: ");
        String userName = getStringInput("사용자 이름: ");
        int movieId = getIntInput("영화 아이디: ");
        int headCount = getIntInput("예약 인원 수: ");

        reservationManager.createReservation(new Reservation(reservationId, userName, movieId, headCount, LocalDate.now()));
    }

    private static void listReservations() {
        List<Reservation> rlist= reservationManager.reservationList();
        if (rlist.isEmpty()) {
            System.out.println("등록된 예약이 없음");
        } else {
            for (Reservation reservation : rlist) {
                System.out.println(reservation);
            }
        }
    }

    private static void getReservationByUserName() {
        String userName = getStringInput("사용자 이름으로 예약 조회: ");
        Reservation reservation = reservationManager.getReservation(userName);
        if (reservation == null) {
            System.out.println("해당 사용자 이름으로 예약을 찾을 수 없습니다.");
        } else {
            System.out.println(reservation);
        }
    }

    private static void updateReservation() {
        int reservationId = getIntInput("수정할 예약 아이디: ");
        int headCount = getIntInput("수정된 인원 수: ");

        reservationManager.updateReservation(reservationId, headCount);
    }

    private static void deleteReservation() {
        int reservationId = getIntInput("삭제할 예약 아이디: ");
        reservationManager.deleteReservation(reservationId);
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static LocalDate getDateInput(String prompt) {
        System.out.print(prompt);
        return LocalDate.parse(scanner.nextLine());
    }
}
