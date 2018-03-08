package models;

import java.util.Set;
import java.util.HashSet;

public class UserID{
    public String username;
    public String password;
    public String confPassword;
    public String firstName;
    public String lastName;

    public UserID(){
        username = "";
        password = "";
        confPassword = "";
        firstName = "";
        lastName = "";
    }

    public UserID(String username, String password, String confPassword, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.confPassword = confPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}