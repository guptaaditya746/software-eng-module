package de.buw.se.service;

import de.buw.se.model.User;

public class LoginResult {
    private boolean success;
    private User userData;

    public LoginResult(boolean success, User userData) {
        this.success = success;
        this.userData = userData;
    }
 
    public boolean isSuccess() {
    	 
        return success;
    } 

    public User getUserData() {
        return userData;
    }
}
