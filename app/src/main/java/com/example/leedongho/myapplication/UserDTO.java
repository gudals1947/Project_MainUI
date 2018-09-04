package com.example.leedongho.myapplication;

/**
 * Created by 김 형민 on 2018-08-31.
 */

public class UserDTO {
    String mail;
    String name;
    int year;
    boolean choice;
    String method;
    public UserDTO(String mail, String name, String sex, int year, boolean choice, String method) {
        this.mail = mail;
        this.name = name;
        this.year = year;
        this.choice = choice;
        this.method = method;
    }
}
