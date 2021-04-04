package main.java.model;

import main.java.model.Movie;

import java.util.Comparator;

public class MovieReview {
    private Movie movie;
    private int rating;

    public MovieReview(Movie movie, int rating) {
        this.movie = movie;
        this.rating = rating;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getRating() {
        return rating;
    }

    private static class MovieReviewByCriticInAGenreComparator implements Comparator<MovieReview> {

        @Override
        public int compare(MovieReview o1, MovieReview o2) {
            if(o1.getRating() < o2.getRating())
                return 1;
            else if(o1.getRating() > o2.getRating())
                return -1;
            else
                return 0;
        }
    }

    public static Comparator<MovieReview> getComparator() {
        return new MovieReview.MovieReviewByCriticInAGenreComparator();
    }
}
