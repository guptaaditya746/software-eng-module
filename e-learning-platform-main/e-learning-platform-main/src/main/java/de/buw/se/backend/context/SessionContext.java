package de.buw.se.backend.context;

import de.buw.se.backend.model.User;

public class SessionContext {

    private static SessionContext instance;
    private User currentUser;

    private SessionContext() {
    }

    public static SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
