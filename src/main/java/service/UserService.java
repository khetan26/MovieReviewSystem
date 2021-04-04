package main.java.service;

import main.java.dao.UserDao;
import main.java.model.User;
import main.java.util.ValidationUtil;
import java.util.Date;
import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(String name, String dob, String email) throws IllegalArgumentException {
        if(ValidationUtil.isNullOrEmpty(name))
            throw new IllegalArgumentException("User name cannot be null or empty");
        if(ValidationUtil.isNullOrEmpty(email))
            throw new IllegalArgumentException("User email cannot be null or empty");
        if(isDuplicateUser(email))
            throw new IllegalArgumentException(String.format("User with email %s already exists", email));
        Date parsedDob = null;
        if(!ValidationUtil.isNullOrEmpty(dob))
            parsedDob = ValidationUtil.parseDate(dob);

        User user = new User(name, parsedDob, email);
        userDao.addUser(user);
    }

    private boolean isDuplicateUser(String userId) {
        return getUser(userId) != null;
    }

    public boolean isUserUpgradable(User user, int moviesReviewedCount) {
        return user.getRole().isUpgradable(moviesReviewedCount);

    }

    public void upgradeUser(User user) {
        user.setRole(user.getRole().upgrade());
    }


    /** Uniquely identifying a user through his email
     */
    public User getUser(String userId) {
        return userDao.getUser(userId);
    }


}
