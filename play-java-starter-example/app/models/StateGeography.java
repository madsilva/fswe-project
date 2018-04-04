package models;

import java.util.HashSet;
import java.util.Set;
import play.data.validation.Constraints;
import javax.persistence.Entity;
import javax.persistence.Id;
import io.ebean.*;

@Entity
public class StateGeography extends Model{

    @Constraints.Required
    public String state;

    @Constraints.Required
    public String zip;

    @Constraints.Required
    public String county;

    @Constraints.Required
    public String city;

    public static Finder<String, StateGeography> find = new Finder<>(StateGeography.class);
}