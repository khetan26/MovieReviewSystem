package main.java.model;

import main.java.enums.UserType;

public interface UserRole {

    int getReviewFactor();

    UserRole upgrade();

    boolean isUpgradable(int moviesReviewedCount);

    public UserType getUserType();

}
