package com.team2;

import java.time.LocalDate;

public class Reservation {
    private int reservation_id;

    private String reservation_user_name;

    private int movie_id;

    private int head_count;

    private LocalDate reservation_date;

    public Reservation() {
    }

    public Reservation(String reservation_user_name, int movie_id, int head_count, LocalDate reservation_date) {
        this.reservation_user_name = reservation_user_name;
        this.movie_id = movie_id;
        this.head_count = head_count;
        this.reservation_date = reservation_date;
    }

    public Reservation(int reservation_id, String reservation_user_name, int movie_id, int head_count, LocalDate reservation_date) {
        this.reservation_id = reservation_id;
        this.reservation_user_name = reservation_user_name;
        this.movie_id = movie_id;
        this.head_count = head_count;
        this.reservation_date = reservation_date;
    }

    @Override
    public String toString() {
        return "com.team2.Reservation{" +
                "reservation_id=" + reservation_id +
                ", reservation_user_name='" + reservation_user_name + '\'' +
                ", movie_id=" + movie_id +
                ", head_count=" + head_count +
                ", reservation_date=" + reservation_date +
                '}';
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public String getReservation_user_name() {
        return reservation_user_name;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getHead_count() {
        return head_count;
    }

    public LocalDate getReservation_date() {
        return reservation_date;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public void setReservation_user_name(String reservation_user_name) {
        this.reservation_user_name = reservation_user_name;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setHead_count(int head_count) {
        this.head_count = head_count;
    }

    public void setReservation_date(LocalDate reservation_date) {
        this.reservation_date = reservation_date;
    }
}
