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
public class VoterVerification extends Model{
    @Constraints.Required
    @Id
    public String username;
    @Constraints.Required
    public String zipCode;
    @Constraints.Required
    public String dateOfBirth;
    @Constraints.Required
    public String idNumber;
    }


}