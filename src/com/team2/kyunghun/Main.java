package com.team2.kyunghun;

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
    public static void main(String[] args) {
        IMovieManager im = MovieManager.getInstance();
        IReservationManager ir = ReserveManager.getInstance();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String action = "";
            while (!"종료".equalsIgnoreCase(action)) {
                System.out.print("작업을 선택하세요 (영화 / 예약 / 종료) >> ");
                action = br.readLine();

                if ("영화".equalsIgnoreCase(action)) {
                    choiceMovie(br, im);
                } else if ("예약".equalsIgnoreCase(action)) {
                    choiceReserve(br, ir);
                } else if ("종료".equalsIgnoreCase(action)) {
                    System.out.println("프로그램을 종료합니다.");
                } else {
                    System.out.println("잘못 입력하였습니다. 다시 입력해 주세요.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void choiceMovie(BufferedReader br, IMovieManager im) {
        String action = "";
        while (!"종료".equalsIgnoreCase(action)) {
            System.out.print("작업을 선택하세요 (영화 등록 / 영화 수정 / 영화 조회 / 영화 전체 목록 조회 / 영화 삭제 / 종료 >> ");
            try {
                action = br.readLine();

                if ("영화 등록".equalsIgnoreCase(action)) {
                    System.out.print("제목 >> ");
                    String title = br.readLine();
                    System.out.print("감독 >> ");
                    String director = br.readLine();
                    System.out.print("장르 >> ");
                    String genre = br.readLine();
                    System.out.print("상영 시작일 >> ");
                    LocalDate startDate = LocalDate.parse(br.readLine());
                    System.out.print("상영 종료일 >> ");
                    LocalDate endDate = LocalDate.parse(br.readLine());

                    Movie movie = new Movie(title, director, genre, startDate, endDate);
                    im.addMovie(movie);
                    System.out.println("영화 등록이 완료되었습니다.");
                } else if ("영화 수정".equalsIgnoreCase(action)) {
                    System.out.print("수정할 영화 ID를 입력해 주세요. >> ");
                    int movieId = Integer.parseInt(br.readLine());
                    System.out.print("새로운 상영 시작일 >> ");
                    LocalDate startDate = LocalDate.parse(br.readLine());
                    System.out.print("새로운 상영 종료일 >> ");
                    LocalDate endDate = LocalDate.parse(br.readLine());
                    im.updateMovie(movieId, startDate, endDate);
                    System.out.println("영화 수정이 완료되었습니다.");
                } else if ("영화 조회".equalsIgnoreCase(action)) {
                    System.out.print("영화 제목을 입력해 주세요. >> ");
                    String title = br.readLine();
                    Movie movie = im.getMovie(title);
                    if (movie == null) {
                        System.out.println("해당 제목으로 영화를 찾을 수 없습니다.");
                    } else {
                            System.out.println(movie);
                    }
                } else if ("영화 전체 목록 조회".equalsIgnoreCase(action)) {
                    List<Movie> movies = im.movieList();
                    if (movies.isEmpty()) {
                        System.out.println("현재 영화 목록이 비어있습니다.");
                    } else {
                        for (Movie movie : movies) {
                            System.out.println(movie);
                        }
                    }
                }
                else if ("영화 삭제".equalsIgnoreCase(action)) {
                    System.out.print("삭제할 영화 ID를 입력해 주세요. >> ");
                    int movieId = Integer.parseInt(br.readLine());
                    im.deleteMovie(movieId);
                    System.out.println("영화 삭제가 완료되었습니다.");
                } else if ("종료".equalsIgnoreCase(action)) {
                    System.out.println("프로그램을 종료합니다.");
                } else System.out.println("잘못 입력하였습니다. 다시 입력해 주세요.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void choiceReserve(BufferedReader br, IReservationManager ir) {
        String action = "";
        while (!"종료".equalsIgnoreCase(action)) {
            System.out.print("작업을 선택하세요 (예약 등록 / 예약 수정 / 예약 조회 / 예약 전체 목록 조회 / 예약 삭제 / 종료 >> ");
            try {
                action = br.readLine();

                if ("예약 등록".equalsIgnoreCase(action)) {
                    System.out.print("사용자 이름 >> ");
                    String userName = br.readLine();
                    System.out.print("영화 ID >> ");
                    int movieId = Integer.parseInt(br.readLine());
                    System.out.print("인원 수 >> ");
                    int headCount = Integer.parseInt(br.readLine());
                    System.out.print("예약 날짜 >> ");
                    LocalDate reservationDate = LocalDate.parse(br.readLine());

                    Reservation reservation = new Reservation(userName, movieId, headCount, reservationDate);
                    ir.createReservation(reservation);
                } else if ("예약 수정".equalsIgnoreCase(action)) {
                    System.out.print("수정할 예약 ID >> ");
                    int reservationId = Integer.parseInt(br.readLine());
                    System.out.print("새로운 인원 수 >> ");
                    int headCount = Integer.parseInt(br.readLine());
                    ir.updateReservation(reservationId, headCount);
                    System.out.println("예약 수정이 완료되었습니다.");
                } else if ("예약 조회".equalsIgnoreCase(action)) {
                    System.out.print("조회할 예약자 이름을 입력해 주세요. >> ");
                    String userName = br.readLine();
                    Reservation reservation = ir.getReservation(userName);
                    if (reservation == null) {
                        System.out.println("해당 이름으로 예약을 찾을 수 없습니다.");
                    } else {
                            System.out.println(reservation);
                    }
                }
                    else if ("예약 전체 목록 조회".equalsIgnoreCase(action)) {
                        List<Reservation> reservations = ir.reservationList();
                        if (reservations.isEmpty()) {
                            System.out.println("현재 예약 목록이 비어있습니다.");
                        } else {
                            for (Reservation reservation : reservations) {
                                System.out.println(reservation);
                            }
                        }
                    }
                    else if ("예약 삭제".equalsIgnoreCase(action)) {
                    System.out.print("삭제할 예약 ID를 입력해 주세요. >> ");
                    int reservationId = Integer.parseInt(br.readLine());
                    ir.deleteReservation(reservationId);
                    System.out.println("예약 삭제가 완료되었습니다.");
                } else if ("종료".equalsIgnoreCase(action)) {
                    System.out.println("프로그램을 종료합니다.");
                } else System.out.println("잘못 입력하였습니다. 다시 입력해 주세요.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
