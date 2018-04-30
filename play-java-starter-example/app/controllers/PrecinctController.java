package controllers;

import com.avaje.ebean.EbeanServer;
import play.mvc.*;
import java.lang.String;

import views.html.*;
import models.*;
import javax.inject.Inject;
import play.data.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;
import java.io.*;
import java.util.Scanner;


//
import io.ebean.*;
//import anorm._;
//import play.api.db.DB;

import play.mvc.*;


import java.lang.Object;
import java.sql.* ;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;



import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class PrecinctController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    @Inject
    FormFactory formFactory;

    public Result precinct(){

        List<Precinct> precinctInfo = Precinct.find.query().setMaxRows(79).findList();

        if (precinctInfo.isEmpty()){
            try{
                File file = new File("zipcodes.txt");
                Scanner sc = new Scanner(file);
                while (sc.hasNext()){
                    String zip = sc.next();
                    Precinct iowaPrecinct = new Precinct();
                    iowaPrecinct.setZip(zip);
                    iowaPrecinct.setPrecinctID(zip);
                    iowaPrecinct.setState("iowa");
                    iowaPrecinct.save();
                }
                sc.close();

                precinctInfo = Precinct.find.query().setMaxRows(10).findList();
            }
            catch(FileNotFoundException e){
                System.out.println(e.toString());
            }

        }

        List<String> precincts = new ArrayList<String>();
        HashMap<String, String> precinctMap = new HashMap<String, String>();

        for(Precinct precinct : precinctInfo){
            precincts.add(precinct.precinctID);
            if(precinctMap.containsKey(precinct.precinctID)) {
                //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
                precinctMap.put(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
            } else {
                precinctMap.put(precinct.precinctID,precinct.zip);
            }
        }
        System.out.println("LENGTH OF PRECINCT ARE : "+precinctInfo.size());
        //return ok(precinct.render(precincts));
        String previous = "disabled";
        String next = "enabled";

        session("pagination", String.valueOf(1));
        return ok(precinct.render(precinctMap, previous, next));
    }

    public Result nextprecinct(){
        System.out.println("IN NEXT PRECINCT");
        int page = Integer.parseInt(session("pagination"));

        List<Precinct> precinctInfo = Precinct.find.query().findList();
        HashMap<String, String> precinctMap = new HashMap<String, String>();

        int start = page * 80;
        int end = start + 80;
        if (end > precinctInfo.size()){
            end = precinctInfo.size();
        }

        if (end <= start){
            for(int index = 1; index <= 80 ; index++){
                if(precinctMap.containsKey(precinctInfo.get(index).precinctID)) {
                    //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
                    precinctMap.put(precinctInfo.get(index).precinctID,precinctMap.get(precinctInfo.get(index).precinctID)+","+precinctInfo.get(index).zip);
                } else {
                    precinctMap.put(precinctInfo.get(index).precinctID,precinctInfo.get(index).zip);
                }
            }
            String previous = "disabled";
            String next = "enabled";
            session("pagination", String.valueOf(1));
            return ok(precinct.render(precinctMap, previous, next));
        }

        for(int index = start; index < end ; index++){
            if(precinctMap.containsKey(precinctInfo.get(index).precinctID)) {
                //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
                precinctMap.put(precinctInfo.get(index).precinctID,precinctMap.get(precinctInfo.get(index).precinctID)+","+precinctInfo.get(index).zip);
            } else {
                precinctMap.put(precinctInfo.get(index).precinctID,precinctInfo.get(index).zip);
            }
        }

        String previous = "enabled";
        String next = "enabled";
        session("pagination", String.valueOf(page+1));
        return ok(precinct.render(precinctMap, previous, next));
    }

    public Result previousprecinct(){
        System.out.println("IN PREVIOUS PRECINCT");
        int page = Integer.parseInt(session("pagination"));

        List<Precinct> precinctInfo = Precinct.find.query().findList();
        HashMap<String, String> precinctMap = new HashMap<String, String>();

        int start = (page-2) * 80;
        int end = start + 80;

        if (start <= 0){
            for(int index = 1; index <= 80 ; index++){
                if(precinctMap.containsKey(precinctInfo.get(index).precinctID)) {
                    //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
                    precinctMap.put(precinctInfo.get(index).precinctID,precinctMap.get(precinctInfo.get(index).precinctID)+","+precinctInfo.get(index).zip);
                } else {
                    precinctMap.put(precinctInfo.get(index).precinctID,precinctInfo.get(index).zip);
                }
            }
            String previous = "disabled";
            String next = "enabled";
            session("pagination", String.valueOf(1));
            return ok(precinct.render(precinctMap, previous, next));
        }

        for(int index = start; index < end ; index++){
            if(precinctMap.containsKey(precinctInfo.get(index).precinctID)) {
                //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
                precinctMap.put(precinctInfo.get(index).precinctID,precinctMap.get(precinctInfo.get(index).precinctID)+","+precinctInfo.get(index).zip);
            } else {
                precinctMap.put(precinctInfo.get(index).precinctID,precinctInfo.get(index).zip);
            }
        }

        String previous = "enabled";
        String next = "enabled";
        session("pagination", String.valueOf(page-1));
        return ok(precinct.render(precinctMap, previous, next));
    }

    public Result deleteprecinct(String precinctpassed){
        List<Precinct> precinctInfo = Precinct.find.query().where().eq("precinctID", precinctpassed).findList();
        List<String> precincts = new ArrayList<String>();
        //HashMap<String, String> precinctMap = new HashMap<String, String>();
        List<String> precinctZip = new ArrayList<String>();
        String precinctName = null;

        for(Precinct precinct : precinctInfo){
            precinctName = precinct.precinctID;
            session("deleteprecinctzip", precinct.precinctID);
            precinctZip.add(precinct.zip);
        }

        //Form<Precinct> precinctform = formFactory.form(precinctInfo);
        //return ok(deleteprecinct.render(precinctform))


        return ok(deleteprecinct.render(precinctName, precinctZip));
    }

    public Result removeprecinct(){
        DynamicForm df = formFactory.form().bindFromRequest();
        Map<String, String[]> formMap = request().body().asFormUrlEncoded();

        System.out.println("Precinct is "+session("deleteprecinctzip"));

        int count = 0;
        while(count < 10){
            String temp = df.get("precinct"+count);
            if (temp != null) {
                System.out.println("temp is "+temp);
                Precinct tempPrecinct = Precinct.find.byId(temp);
                System.out.println(" TEMP PRECIONCT IS "+ tempPrecinct);
                tempPrecinct.delete();
                System.out.println("Zip code from precinct deleted successfully");
                //Precinct.delete(tempPrecinct);
            }
            count++;
        }

        session().remove("deleteprecinctzip");

//        List<Precinct> precinctInfo = Precinct.find.query().findList();
//        List<String> precincts = new ArrayList<String>();
//        HashMap<String, String> precinctMap = new HashMap<String, String>();
//
//        for(Precinct precinct : precinctInfo){
//            precincts.add(precinct.precinctID);
//            if(precinctMap.containsKey(precinct.precinctID)) {
//                //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
//                precinctMap.put(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
//            } else {
//                precinctMap.put(precinct.precinctID,precinct.zip);
//            }
//        }
//
//        return ok(precinct.render(precinctMap, "enabled", "enabled"));
        List<Precinct> precinctInfo = Precinct.find.query().setMaxRows(79).findList();
        List<String> precincts = new ArrayList<String>();
        HashMap<String, String> precinctMap = new HashMap<String, String>();

        for(Precinct precinct : precinctInfo){
            precincts.add(precinct.precinctID);
            if(precinctMap.containsKey(precinct.precinctID)) {
                //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
                precinctMap.put(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
            } else {
                precinctMap.put(precinct.precinctID,precinct.zip);
            }
        }
        System.out.println("LENGTH OF PRECINCT ARE : "+precinctInfo.size());
        //return ok(precinct.render(precincts));
        String previous = "disabled";
        String next = "enabled";

        session("pagination", String.valueOf(1));
        return ok(precinct.render(precinctMap, previous, next));
    }

    public Result addprecinct(String precinctpassed){
        List<Precinct> PrecinctZip = Precinct.find.query().findList();
        List<StateGeography> GeographyZip = StateGeography.find.query().findList();

        //System.out.println("Precincts are "+precincts);
        List<String> precinctZipArray = new ArrayList<>();
        List<String> geographyZipArray = new ArrayList<>();
        session("addprecinctzip", precinctpassed);

        for(Precinct temp : PrecinctZip){
            precinctZipArray.add(temp.zip);
        }

        for(StateGeography temp : GeographyZip){
            geographyZipArray.add(temp.zip);
        }

        geographyZipArray.removeAll(precinctZipArray);
        System.out.println("LEFT OUT PRECINCTS ARE "+geographyZipArray);
        System.out.println("TEST TEST");

        return ok(addprecinct.render(precinctpassed, geographyZipArray));
    }

    public Result addPrecinctToTable(){
        DynamicForm df = formFactory.form().bindFromRequest();
        Map<String, String[]> formMap = request().body().asFormUrlEncoded();

        System.out.println(" ADD PRECINCT TO TABLE ");
        System.out.println("Precinct is "+session("addprecinctzip"));

        int count = 0;
        while(count < 10){
            String temp = df.get("precinct"+count);
            if (temp != null) {
                System.out.println("temp is "+temp);
                Precinct newPrecinct = new Precinct();
                newPrecinct.precinctID = session("addprecinctzip");
                newPrecinct.zip = temp;
                newPrecinct.save();

                Precinct tempPrecinct = Precinct.find.byId(temp);
                System.out.println(" TEMP PRECIONCT IS "+ tempPrecinct);
                //tempPrecinct.delete();
                System.out.println("Zip code from precinct deleted successfully");
            }
            count++;
        }


        session().remove("addprecinctzip");

//        List<Precinct> precinctInfo = Precinct.find.query().findList();
//        List<String> precincts = new ArrayList<String>();
//        HashMap<String, String> precinctMap = new HashMap<String, String>();
//
//        for(Precinct precinct : precinctInfo){
//            precincts.add(precinct.precinctID);
//            if(precinctMap.containsKey(precinct.precinctID)) {
//                //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
//                precinctMap.put(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
//            } else {
//                precinctMap.put(precinct.precinctID,precinct.zip);
//            }
//        }
//
//
//        return ok(precinct.render(precinctMap, "enabled", "enabled"));

        List<Precinct> precinctInfo = Precinct.find.query().setMaxRows(79).findList();
        List<String> precincts = new ArrayList<String>();
        HashMap<String, String> precinctMap = new HashMap<String, String>();

        for(Precinct precinct : precinctInfo){
            precincts.add(precinct.precinctID);
            if(precinctMap.containsKey(precinct.precinctID)) {
                //precinctMap.replace(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
                precinctMap.put(precinct.precinctID,precinctMap.get(precinct.precinctID)+","+precinct.zip);
            } else {
                precinctMap.put(precinct.precinctID,precinct.zip);
            }
        }
        System.out.println("LENGTH OF PRECINCT ARE : "+precinctInfo.size());
        //return ok(precinct.render(precincts));
        String previous = "disabled";
        String next = "enabled";

        session("pagination", String.valueOf(1));
        return ok(precinct.render(precinctMap, previous, next));
    }
}

