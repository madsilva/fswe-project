package models;

import java.util.HashSet;
import java.util.Set;
import play.data.validation.Constraints;
import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.*;

@Entity
public class Election extends Model{

    @Constraints.Required
    public String electionType;

    @Constraints.Required
    public String state;

    @Constraints.Required
    @Id
    public String electionID;


    public static Finder<String, Election> find = new Finder<>(Election.class);
}