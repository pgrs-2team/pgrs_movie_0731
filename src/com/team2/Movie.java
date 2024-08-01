package com.team2;

import java.time.LocalDate;

public class Movie {
    private int movie_id;

    private String title;

    private String director;

    private String genre;

    private LocalDate start_date;

    private LocalDate end_date;

    @Override
    public String toString() {
        return "com.team2.Movie{" +
                "movie_id=" + movie_id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }

    public Movie() {
    }

    public Movie(int movie_id, String title, String director, String genre, LocalDate start_date, LocalDate end_date) {
        this.movie_id = movie_id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Movie(String title, String director, String genre, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
}
