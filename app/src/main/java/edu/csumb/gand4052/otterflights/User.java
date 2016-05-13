package edu.csumb.gand4052.otterflights;

/**
 * Created by elgandara on 5/11/16.
 */
public class User {

    private String username;
    private String password;
    private String accountType;

    public User() {
        this.username = "";
        this.password = "";
        this.accountType = "";
    }

    public User(String username, String password, String accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public String getUsername() { return this.username;}
    public String getPassword() { return this.password;}
    public String getAccountType() { return this.accountType;}

    public void setUsername(String username) { this.username = username;}
    public void setPassword(String password) { this.password = password;}
    public void setAccountType(String accountType) { this.accountType = accountType;}

    public boolean equals(Object object) {

        if (object instanceof User) {
            User user = (User) object;

            return (this.username.equals(user.username) );
        }
        else {
            return false;
        }
    }

    public String toString() {

        return ("Username: " + this.username);
    }
}
