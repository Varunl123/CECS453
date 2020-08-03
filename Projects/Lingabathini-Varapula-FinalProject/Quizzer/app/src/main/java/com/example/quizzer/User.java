package com.example.quizzer;

public class User {
    int id;
    String userName;

    public User(int id,String userName) {
        this.userName = userName;
        this.id=id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
