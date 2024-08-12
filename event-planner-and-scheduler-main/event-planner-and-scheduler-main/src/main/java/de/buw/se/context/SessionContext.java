package de.buw.se.context;

import de.buw.se.model.User;

public class SessionContext {
    private static SessionContext instance;
    private User currentUser;

    protected SessionContext() {
    } 

    public static SessionContext getInstance() {
    	
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance; 
    }
     
    public static void setInstance(SessionContext sessionContext) {
        instance = sessionContext;
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
