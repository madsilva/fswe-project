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
    public String getSenator() {
        return senator;
    }

    public void setSenator(String senator) {
        this.senator = senator;
    }

    public String getUsRepresentative() {
        return usRepresentative;
    }

    public void setUsRepresentative(String usRepresentative) {
        this.usRepresentative = usRepresentative;
    }

    public String getMayor() {
        return mayor;
    }

    public void setMayor(String mayor) {
        this.mayor = mayor;
    }

    public String getGovernor() {
        return governor;
    }

    public void setGovernor(String governor) {
        this.governor = governor;
    }

    public String senator;
    public String usRepresentative;
    public String mayor;
    public String governor;

}