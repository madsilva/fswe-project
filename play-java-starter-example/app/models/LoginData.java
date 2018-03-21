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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public String getFirstname(){
        return firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrivilege(String access){
        this.privilege = access;
    }



    @Id @Constraints.Required
    public String username;
    @Constraints.Required
    public String password;
    //@Constraints.Required
    public String firstname;
    //@Constraints.Required
    public String lastname;
    public String privilege;


    public static Finder<String, LoginData> find = new Finder<>(LoginData.class);
}