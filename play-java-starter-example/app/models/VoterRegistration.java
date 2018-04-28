package models;

import java.util.Set;
import java.util.HashSet;
import play.data.validation.Constraints;
//import javax.persistence.Entity;
import javax.persistence.*;
import io.ebean.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;

@Entity
public class VoterRegistration extends Model{
    @Constraints.Required
    @Id
    public String username;
    //@Constraints.Required
    //public String firstName;
    //@Constraints.Required
    //public String lastName;
    @Constraints.Required
    public String address;
    @Constraints.Required
    public String city;
    @Constraints.Required
    public String state;
    @Constraints.Required
    public String zipCode;
    @Constraints.Required
    public String dateOfBirth;
    @Constraints.Required
    public String gender;
    @Constraints.Required
    public String party;
    @Constraints.Required
    public String socialSecurity;
    @Constraints.Required
    public String idNumber;
    public boolean approved;
    public void setApproved(boolean value){
        this.approved = value;
    }

    public static Finder<String, VoterRegistration> find = new Finder<>(VoterRegistration.class);
}