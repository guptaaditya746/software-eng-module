package de.buw.se.backend.service;

import de.buw.se.backend.model.User;

public class LoginResult {

    private final boolean success;
    private final User userData;

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
