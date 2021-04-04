package main.java.model;

import main.java.enums.UserType;

public class Critic implements UserRole {

    private static Critic instance = null;
    private static UserType userType = UserType.CRITIC;

    private Critic() {

    }

    public static Critic getInstance() {
        if(instance == null) {
            synchronized (Critic.class) {
                if(instance == null) {
                    instance = new Critic();
                }
            }
        }
        return instance;
    }


    @Override
    public int getReviewFactor() {
        return 2;
    }

    @Override
    public UserRole upgrade() {
        //TODO
        return null;
    }

    @Override
    public boolean isUpgradable(int moviesReviewedCount) {
       //TODO
        return false;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }
}
