package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ElectionResults extends Model {

    @Constraints.Required
    public String electionID;

    @Constraints.Required
    public String precinct;

    @Constraints.Required
    public String candidate;

    @Constraints.Required
    public int votes;

    public static Finder<String, ElectionResults> find = new Finder<>(ElectionResults.class);
}