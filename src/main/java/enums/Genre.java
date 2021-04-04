package main.java.enums;

public enum Genre {
    COMEDY("Comedy"),
    DRAMA("Drama"),
    ACTION("Action"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    HORROR("Horror"),
    DOCUMENTARY("Documentary"),
    KIDS("Kids");

    private String value;

    private Genre(String value) {
        this.value = value;
    }

    public String getRating() {
        return this.value;
    }
}
