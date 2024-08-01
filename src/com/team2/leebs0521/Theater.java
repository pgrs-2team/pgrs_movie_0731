package com.team2.leebs0521;

import com.team2.IMovieManager;
import com.team2.IReservationManager;
import com.team2.Movie;
import com.team2.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Theater implements Runnable{
    private final IMovieManager movieManager;
    private final IReservationManager reservationManager;
    private final Scanner scanner;

    public Theater(IMovieManager movieManager, IReservationManager reservationManager, Scanner scanner) {
        this.movieManager = movieManager;
        this.reservationManager = reservationManager;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (true) {
            showMainMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1 -> manageMovies();
                case 2 -> manageReservations();
                case 11 -> {
                    System.out.println("프로그램 종료...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("잘못된 선택입니다. 다시 시도해 주세요.");
            }
        }
    }
    private void showMainMenu() {
        System.out.println("메인 메뉴:");
        System.out.println("1. 영화 관리");
        System.out.println("2. 예약 관리");
        System.out.println("11. 종료");
    }

    private int getUserInput() {
        System.out.print("선택: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void manageMovies() {
        while (true) {
            showMovieMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1 -> addMovie();
                case 2 -> deleteMovie();
                case 3 -> updateMovie();
                case 4 -> searchMovie();
                case 5 -> listMovies();
                case 6 -> { return; }
                default -> System.out.println("잘못 선택하였습니다.");
            }
        }
    }

    private void showMovieMenu() {
        System.out.println("영화 관리:");
        System.out.println("1. 영화 추가");
        System.out.println("2. 영화 삭제");
        System.out.println("3. 영화 수정");
        System.out.println("4. 영화 제목으로 검색");
        System.out.println("5. 전체 영화 목록");
        System.out.println("6. 메인 메뉴로 돌아가기");
    }

    private void addMovie() {
        System.out.print("영화 제목: ");
        String title = scanner.nextLine();
        System.out.print("영화 감독: ");
        String director = scanner.nextLine();
        System.out.print("영화 장르: ");
        String genre = scanner.nextLine();
        System.out.print("영화 시작 날짜(yyyy-mm-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("영화 종료 날짜(yyyy-mm-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        Movie movie = new Movie(title, director, genre, startDate, endDate);
        movieManager.addMovie(movie);
        System.out.println("영화가 성공적으로 추가되었습니다.");
    }

    private void deleteMovie() {
        System.out.print("삭제할 영화 ID: ");
        int id = getUserInput();
        movieManager.deleteMovie(id);
        System.out.println("영화가 성공적으로 삭제되었습니다.");
    }

    private void updateMovie() {
        System.out.print("수정할 영화 ID: ");
        int id = getUserInput();
        System.out.print("시작 날짜:(yyyy-mm-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("종료 날짜:(yyyy-mm-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        movieManager.updateMovie(id, startDate, endDate);
        System.out.println("영화가 성공적으로 수정되었습니다.");
    }

    private void searchMovie() {
        System.out.print("검색할 영화 제목: ");
        String title = scanner.nextLine();
        Movie movie = movieManager.getMovie(title);

        if (movie == null) {
            System.out.println("해당 제목의 영화를 찾을 수 없습니다.");
            return;
        }

        System.out.println("찾은 영화: " + movie);
    }

    private void listMovies() {
        List<Movie> movies = movieManager.movieList();
        if (movies.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
            return;
        }
        movies.forEach(System.out::println);
    }

    private void manageReservations() {
        while (true) {
            showReservationMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1 -> createReservation();
                case 2 -> searchReservation();
                case 3 -> listReservations();
                case 4 -> deleteReservation();
                case 5 -> updateReservation();
                case 6 -> { return; }
                default -> System.out.println("잘못 입력하였습니다.");
            }
        }
    }

    private void showReservationMenu() {
        System.out.println("예약 관리:");
        System.out.println("1. 예약 추가");
        System.out.println("2. 예약자 이름으로 검색");
        System.out.println("3. 전체 예약 목록");
        System.out.println("4. 예약 삭제");
        System.out.println("5. 예약 수정");
        System.out.println("6. 메인 메뉴로 돌아가기");
    }

    private void createReservation() {
        System.out.print("예약자 이름: ");
        String userName = scanner.nextLine();
        System.out.print("영화 ID: ");
        int movieId = getUserInput();
        System.out.print("인원 수: ");
        int headCount = getUserInput();
        System.out.print("예약 날짜(yyyy-mm-dd): ");
        LocalDate reservationDate = LocalDate.parse(scanner.nextLine());

        Reservation reservation = new Reservation(0, userName, movieId, headCount, reservationDate);

        reservationManager.createReservation(reservation);
        System.out.println("예약이 성공적으로 추가되었습니다.");
    }

    private void searchReservation() {
        System.out.print("예약자 이름: ");
        String userName = scanner.nextLine();
        Reservation reservation = reservationManager.getReservation(userName);
        if (reservation == null) {
            System.out.println("해당 이름으로 예약된 내역이 없습니다.");
            return;
        }
        System.out.println("찾은 예약: " + reservation);
    }

    private void listReservations() {
        List<Reservation> reservations = reservationManager.reservationList();
        if (reservations.isEmpty()) {
            System.out.println("등록된 예약이 없습니다.");
            return;
        }
        reservations.forEach(System.out::println);
    }

    private void deleteReservation() {
        System.out.print("삭제할 예약 ID: ");
        int id = getUserInput();
        reservationManager.deleteReservation(id);
        System.out.println("예약이 성공적으로 삭제되었습니다.");
    }

    private void updateReservation() {
        System.out.print("수정할 예약 ID: ");
        int id = getUserInput();
        System.out.print("인원 수: ");
        int headCount = getUserInput();

        reservationManager.updateReservation(id, headCount);
        System.out.println("예약이 성공적으로 수정되었습니다.");
    }
}
