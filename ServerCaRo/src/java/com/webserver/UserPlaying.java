/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webserver;

/**
 *
 * @author mrtrung
 */
public class UserPlaying {
    public User user_1;
    public User user_2;

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public User getUser_2() {
        return user_2;
    }

    public User getUser_1() {
        return user_1;
    }
}
