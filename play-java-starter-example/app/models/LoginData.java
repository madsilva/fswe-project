package models;
//package javaguide.sql;

import java.util.HashSet;
import java.util.Set;
import play.data.validation.Constraints;

//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import play.mvc.Controller;
//import play.db.NamedDatabase;
//import play.db.Database;
//import com.avaje.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.*;


@Entity
public class LoginData extends Model{

    @Id @Constraints.Required
    public String username;
    @Constraints.Required
    public String password;

    public static Finder<String, LoginData> find = new Finder<>(LoginData.class);

//    // Constructer for the class LoginData
//    // Default Constructor
//    public LoginData(){
//
//    }
//    // Custom Constructor
//    public LoginData(String username, String password){
//        this.username = username;
//        this.password = password;
//    }
//
//    private static Set<LoginData> logins;
//
//
//    static {
//        logins = new HashSet<LoginData>();
//        logins.add(new LoginData( "nabeelkhan", "123456"));
//        logins.add(new LoginData( "nabeelkh", "123456"));
//        logins.add(new LoginData("nabeel", "password"));
//    }
//
//    public boolean findLogin(String username, String password){
//        for (LoginData user : logins){
//            if (username.equals(user.username) && password.equals(user.password)){
//                return true;
//            }
//        }
//        // If the user is not present then return 0
//        return false;
//    }
//
//    public static void add(LoginData data){
//        logins.add(data);
//    }
}