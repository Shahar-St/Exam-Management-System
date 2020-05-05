package org.args.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class User {

    @Id
    private int socialId;
    private String firstName, lastName, password, userName;

    //Group c'tors
    public User() {
    }

    public User(int socialId, String firstName, String lastName, String password, String userName) {
        this.socialId = socialId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
    }

    //Group setters and getters
    public int getSocialId() {
        return socialId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
