package models;

import java.util.HashSet;
import java.util.Set;
import play.data.validation.Constraints;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;

import io.ebean.*;

@Entity
public class SecurityQuestions extends Model{

    @Constraints.Required
    @Id
    public String username;

    @Constraints.Required
    public String pet;

    @Constraints.Required
    public String city;

    @Constraints.Required
    public String school;

    public String getUsername(){ return username; }

    public void setUsername(String username){ this.username = username; }

    public String getPet() {
        return pet;
    }

    public void setFavoritePet(String favoritePet) {
        this.pet = favoritePet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool(){ return school; }

    public void setSchool(String school){ this.school = school; }

    public static Finder<String, SecurityQuestions> find = new Finder<>(SecurityQuestions.class);
}