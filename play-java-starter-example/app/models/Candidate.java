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
import javax.validation.Constraint;

import io.ebean.*;


@Entity
public class Candidate extends Model{

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

    public void setParty(String party){
        this.party = party;
    }

    public String getParty(){
        return party;
    }

    public String getPrecinct(){
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    @Constraints.Required
    public String firstname;
    @Constraints.Required
    public String lastname;
    @Constraints.Required
    public String party;
    @Constraints.Required
    public String precinct;

    public static Finder<String, Candidate> find = new Finder<>(Candidate.class);
}