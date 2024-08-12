package de.buw.se.Domain;

import java.util.Objects;

public class User {
    protected String name;
    protected String password;
    protected String phoneNumber;
    protected String mail;

    public String getName() { return this.name; }
    public String getPassword() { return this.password; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getMail() { return this.mail; }

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setMail(String mail) { this.mail = mail; }

    @Override
    public String toString() {
        return "name: " + this.getName() + ", password:" + this.getPassword() + ", phone:" + this.getPhoneNumber() + ", mail:" + this.getMail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(mail, user.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, phoneNumber, mail);
    }
}