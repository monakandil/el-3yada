package com.taekung.nady.el_3yada;

import android.graphics.Bitmap;

/**
 * Created by Taekunger on 5/20/2016.
 */
public class User {

    private String username;
    private String name;
    private Bitmap avatar;
    private String pass;

    public User(String username, String name, Bitmap avatar, String pass) {
        this.username = username;
        this.name = name;
        this.avatar = avatar;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
