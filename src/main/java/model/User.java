package main.java.model;

import java.util.Date;

public class User {

    private final String name;
    private final Date dob;
    private String email;
    private final Date createdOn;
    private UserRole role;

    public User(String name, Date dob, String email) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        createdOn = new Date();
        role = Viewer.getInstance();
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        User user = null;
        if(o instanceof User)
            user = (User) o;
        else
            return false;
        return this.getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
