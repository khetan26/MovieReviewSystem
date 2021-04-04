package main.java.enums;

public enum Rating {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private int rating;

    private Rating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }
}
