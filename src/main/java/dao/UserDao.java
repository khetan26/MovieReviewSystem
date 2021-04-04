package main.java.dao;

import main.java.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDao {

    private Map<String, User> userMap;

    public UserDao() {
        userMap = new ConcurrentHashMap<>();
    }

    public void addUser(User user) {
        userMap.putIfAbsent(user.getEmail(), user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(userMap.values());
    }

    public User getUser(String userId) {
        return userMap.get(userId);
    }
}
