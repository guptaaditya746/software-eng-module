package de.buw.se;

public class Event {
    private String programName;
    private String password;

    public Event(String programName, String password) {
        this.programName = programName;
        this.password = password;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Program: " + programName + ", Password: " + password;
    }
}
