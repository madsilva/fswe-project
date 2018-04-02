package models;

import java.util.HashSet;
import java.util.Set;
import play.data.validation.Constraints;
import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.*;

@Entity
public class Precinct extends Model{

    @Constraints.Required
    public String precinctID;

    @Constraints.Required
    @Id
    public String zip;


    public static Finder<String, Precinct> find = new Finder<>(Precinct.class);
}