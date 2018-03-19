package models;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.*;

@Entity
public class UserID extends Model{
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