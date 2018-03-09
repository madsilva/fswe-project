package models;

import java.util.Set;
import java.util.HashSet;
import play.data.validation.Constraints;
import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.*;

@Entity
public class VoterRegistration extends Model{
    @Constraints.Required
    public String username;
    @Constraints.Required
    public String firstName;
    @Constraints.Required
    public String lastName;
    @Constraints.Required
    public String address;
    @Constraints.Required
    public String city;
    @Constraints.Required
    public String state;
    @Constraints.Required
    public String dateOfBirth;
    @Constraints.Required
    public String socialSecurity;
    @Constraints.Required
    public String idNumber;

    public static Finder<String, VoterRegistration> find = new Finder<>(VoterRegistration.class);
}