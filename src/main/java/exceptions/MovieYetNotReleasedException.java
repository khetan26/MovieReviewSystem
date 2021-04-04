package main.java.exceptions;

public class MovieYetNotReleasedException extends Exception {

    public MovieYetNotReleasedException() {
        super("Cannot review an upcoming movie");
    }

}
