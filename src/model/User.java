package model;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean cekPassword(String password) {
        return this.password.equals(password);
    }

    public String toCSV() {
        return username + "," + password;
    }

    @Override
    public String toString() {
        return "Username: " + username;
    }
} 
    

