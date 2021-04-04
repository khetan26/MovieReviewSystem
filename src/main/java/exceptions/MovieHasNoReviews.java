package main.java.exceptions;

public class MovieHasNoReviews extends Exception {

    public MovieHasNoReviews() {
        super("Movie has no reviews");
    }
}
