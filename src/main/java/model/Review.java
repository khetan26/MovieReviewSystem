package main.java.model;

import main.java.enums.UserType;

import java.util.Date;
import java.util.UUID;

public class Review {

    private final String id;
    private final String movieId; //for this code impl we are considering movie title to be movieId
    private final String userId;
    private final int rating;
    private final Date createdOn;
    private final UserType userType;

    public Review(String movieId, String userId, int rating, UserType userType) {
        this.id = UUID.randomUUID().toString();
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
        this.createdOn = new Date();
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public UserType getUserType() {
        return userType;
    }
}
