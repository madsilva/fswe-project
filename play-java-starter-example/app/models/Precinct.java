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

    public String getPrecinctID() {
        return precinctID;
    }

    public void setPrecinctID(String precinctID) {
        this.precinctID = precinctID;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Constraints.Required
    @Id
    public String zip;

    public String state;

    public void setState(String state){ this.state = state; }

    public String getState(){ return state; }

    public static Finder<String, Precinct> find = new Finder<>(Precinct.class);
}