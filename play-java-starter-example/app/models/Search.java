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
public class Search extends Model{


    public String getSqlColumn() {
        return sqlColumn;
    }

    public void setSqlColumn(String sqlColumn) {
        this.sqlColumn = sqlColumn;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    @Id @Constraints.Required
    public String sqlColumn;
    @Constraints.Required
    public String criteria;


    public static Finder<String, Search> find = new Finder<>(Search.class);
}