package controllers;

import com.avaje.ebean.EbeanServer;
import play.mvc.*;
import java.lang.String;
import views.html.*;
import models.*;
import javax.inject.Inject;
import play.data.*;
import org.apache.commons.codec.digest.DigestUtils;
//
import io.ebean.*;
//import anorm._;
//import play.api.db.DB;

import play.mvc.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.Object;
import java.sql.* ;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;



import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller{

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    @Inject
    FormFactory formFactory;

    public Result index() {
        return ok(index.render("Welcome to the Online Voting System"));
    }


    public Result login(){
        String user = session("connected");
        if (user == null){
            Form<LoginData> loginForm = formFactory.form(LoginData.class);
            System.out.println("Login Function hit");
            return ok(login.render(loginForm, " "));
        }
        else{
            return ok(profile.render(user, false));
        }
    }

    public Result create(){
        String user = session("connected");
        if (user == null){
            Form<UserID> userForm = formFactory.form(UserID.class);
            System.out.println("Create Function hit");
            return  ok(create.render(userForm));
        }
        else{
            return ok(profile.render(user, false));
        }
    }

    public Result save(){
        Form<UserID> userForm = formFactory.form(UserID.class).bindFromRequest();
        UserID user = userForm.get();

        System.out.println(user.password);
        System.out.println(user.confPassword);

        if (user.password.equals(user.confPassword)){
            LoginData loginCredentials = new LoginData();
            loginCredentials.setUsername(user.username);
            loginCredentials.setFirstname(user.firstName);
            loginCredentials.setLastname(user.lastName);
            loginCredentials.setPriviledge("voter");
            System.out.println("Firstname & Lastname are : "+user.firstName+user.lastName);
            loginCredentials.setPassword(DigestUtils.md5Hex(user.password));
            loginCredentials.save();
            System.out.println("hashed password saved : "+DigestUtils.md5Hex(user.password));
        }
        else{
            System.out.println("Passwords do not match");
            return ok(error.render("Passwords do not match"));
        }
        Form<LoginData> loginForm = formFactory.form(LoginData.class);
        return ok(login.render(loginForm,"You have successfully signed up"));
    }

    public Result voterRegistration(){
        String user = session("connected");
        if(user != null){
            VoterRegistration voterRegistrationInfo = VoterRegistration.find.query().where().eq("username", user).findUnique();
            if (voterRegistrationInfo != null){
                return ok(error.render("You have Already applied for Voter Registration"));
            }
            else{
                Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class);
                System.out.println("Voter Registration Function hit");
                return ok(voterRegistration.render(voterForm));
            }
        }
        else{
            return ok(error.render("User not signed in"));
        }
    }

    public Result saveVoter() {
        Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class).bindFromRequest();
        VoterRegistration voter = voterForm.get();
        String user = session("connected");
        System.out.println("username is "+user+voter.username);
        if(user.matches(voter.username)){
            voter.setApproved(false);
            voter.save();
            return ok(profile.render(voter.username, false));
        }
        else{
            // Username not matching
            return ok(error.render("Username Not Matching"));
        }
    }

    public Result admin(){
        String user = session("connected");
        System.out.println("Admin hit");
        if(user != null) {
            //Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class).bindFromRequest();
            VoterRegistration voterRegistrationInfo = VoterRegistration.find.query().where().eq("username", user).findUnique();
            System.out.println("Approved query is "+voterRegistrationInfo.username+voterRegistrationInfo.approved);
            System.out.println("Admin hit if case");
            return ok(admin.render(user));
        } else {
            System.out.println("Admin hit else case");
            return ok(error.render("User not Signed in"));
        }
    }

    public Result approval(){
        List<VoterRegistration> voterInfo = VoterRegistration.find.query().where().eq("approved", false).findList();
        List<String> unapprovedNames = new ArrayList<String>();

        for(VoterRegistration voter : voterInfo){
            unapprovedNames.add(voter.username);
        }

        return ok(approval.render(unapprovedNames));
    }

    public Result profile(){
        String user = session("connected");
        System.out.println("Profile hit");
        if(user != null) {
            //Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class).bindFromRequest();
            VoterRegistration voterRegistrationInfo = VoterRegistration.find.query().where().eq("username", user).findUnique();
            System.out.println("Approved query is "+voterRegistrationInfo.username+voterRegistrationInfo.approved);
            System.out.println("Profile hit if case");
            return ok(profile.render(user, voterRegistrationInfo.approved));
        } else {
            System.out.println("Profile hit else case");
            return ok(error.render("User not Signed in"));
        }
    }

    public Result logout(){
        //session().clear();
        session().remove("connected");
        System.out.println("Session cleared");
        Form<LoginData> loginForm = formFactory.form(LoginData.class);
        System.out.println("Login Function hit");
        return ok(login.render(loginForm,"User Logged Out Successfully"));
    }

    public Result error(){
        return ok(error.render("error"));
    }

    public Result userlogin(){
        System.out.println("User Login Function hit");

        Form<LoginData> loginForm = formFactory.form(LoginData.class).bindFromRequest();
        LoginData loginCredentials = loginForm.get();
        System.out.println("UserDetails are");


        System.out.println(loginCredentials.username);
        System.out.println(DigestUtils.md5Hex(loginCredentials.password));
        LoginData login = LoginData.find.query().where().eq("username", loginCredentials.username).eq("password", DigestUtils.md5Hex(loginCredentials.password)).findUnique();

        if (login == null){
            System.out.println("User Not Found");
            return ok(error.render("User Not Found"));
//            Form<LoginData> loginForm2 = formFactory.form(LoginData.class);
//            return badRequest(login.render(loginForm2));
        }
        else{
            if (login.priviledge.matches("admin")){
                return ok(admin.render(loginForm.get().username));
            }
            else{
                System.out.println("User Logged In"+login.priviledge);
                session("connected", loginForm.get().username);
                return ok(profile.render(loginForm.get().username,false));
            }
        }

    }

    public Result update(String username){
        //VoterRegistration voterRegistrationInfo = VoterRegistration.find.query().where().eq("username", username).findUnique();
        VoterRegistration voterRegistrationInfo = VoterRegistration.find.byId(username);
        voterRegistrationInfo.setApproved(true);
        //voterRegistrationInfo.approved = true;
        voterRegistrationInfo.update();
        System.out.println("*************THE EBEAN SERVER IS 1*************");
        //EbeanServer ebs = Ebean.getServer("default");
        System.out.println("*************THE EBEAN SERVER IS 2*************");
        //System.out.println(ebs);
        //Ebean.save(voterRegistrationInfo);
        //voterRegistrationInfo.save();

        List<VoterRegistration> voterInfo = VoterRegistration.find.query().where().eq("approved", false).findList();
        List<String> unapprovedNames = new ArrayList<String>();

        for(VoterRegistration voter : voterInfo){
            unapprovedNames.add(voter.username);
        }

        return ok(approval.render(unapprovedNames));
    }

    public Result destroy(String username){
        //VoterRegistration approvedVoter = VoterRegistration.find.query().where().eq("username", username).findUnique();
        VoterRegistration approvedVoter = VoterRegistration.find.byId(username);
        approvedVoter.delete();

        List<VoterRegistration> voterInfo = VoterRegistration.find.query().where().eq("approved", false).findList();
        List<String> unapprovedNames = new ArrayList<String>();

        for(VoterRegistration voter : voterInfo){
            unapprovedNames.add(voter.username);
        }

        return ok(approval.render(unapprovedNames));
    }

    public Result precinct(){
        List<Precinct> precinctInfo = Precinct.find.query().findList();
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
        //System.out.println("Hask m map "+precinctMap);
        //return ok(precinct.render(precincts));
        return ok(precinct.render(precinctMap));
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

        List<Precinct> precinctInfo = Precinct.find.query().findList();
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

        return ok(precinct.render(precinctMap));
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

        List<Precinct> precinctInfo = Precinct.find.query().findList();
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

        return ok(precinct.render(precinctMap));
    }
}


