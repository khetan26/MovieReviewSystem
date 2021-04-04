package main.java.exceptions;

public class MultipleReviewPerMovieException extends Exception {

    public MultipleReviewPerMovieException() {
        super("A user cannot review same movie twice");
    }
}
