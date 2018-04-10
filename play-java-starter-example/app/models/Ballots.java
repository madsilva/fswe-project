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

import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.*;


@Entity
public class Ballots extends Model{

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getElectionID(){
        return electionID;
    }

    public void setElectionID(String electionID){
        this.electionID = electionID;
    }


    @Id @Constraints.Required
    public String precinct;
    @Constraints.Required
    public String electionID;


    public static Finder<String, Ballots> find = new Finder<>(Ballots.class);
}