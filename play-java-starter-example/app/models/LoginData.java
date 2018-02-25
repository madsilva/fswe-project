package models;

import java.util.HashSet;
import java.util.Set;

public class LoginData{
    public String username;
    public String password;

    // Constructer for the class LoginData
    // Default Constructor
    public LoginData(){

    }
    // Custom Constructor
    public LoginData(String username, String password){
        this.username = username;
        this.password = password;
    }

    private static Set<LoginData> logins;


    static {
        logins = new HashSet<LoginData>();
        logins.add(new LoginData( "nabeelkhan", "123456"));
        logins.add(new LoginData( "nabeelkh", "123456"));
        logins.add(new LoginData("nabeel", "password"));
    }

    public boolean findLogin(String username, String password){
        for (LoginData user : logins){
            if (username.equals(user.username) && password.equals(user.password)){
                return true;
            }
        }
        // If the user is not present then return 0
        return false;
    }

    public static void add(LoginData data){
        logins.add(data);
    }
}