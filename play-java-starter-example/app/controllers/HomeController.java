package controllers;

import com.avaje.ebean.EbeanServer;
import play.mvc.*;
import java.lang.String;

//import sun.java2d.pipe.SpanShapeRenderer;
import views.html.*;
import models.*;
import javax.inject.Inject;
import play.data.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;


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
        System.out.println("LOGIN USER KEY IS ");
        System.out.println(user);

        if (user == null){
            Form<LoginData> loginForm = formFactory.form(LoginData.class);
            System.out.println("Login Function hit");
            return ok(login.render(loginForm, " "));
        }
        else{
            return ok(profile.render(user, false));
        }
    }

    // Function for User Logging
    public Result userlogin(){
        System.out.println("User Login Function hit");

        Form<LoginData> loginForm = formFactory.form(LoginData.class).bindFromRequest();
        LoginData loginCredentials = loginForm.get();


        System.out.println(loginCredentials.username);
        System.out.println(DigestUtils.md5Hex(loginCredentials.password));
        LoginData login = LoginData.find.query().where().eq("username", loginCredentials.username).eq("password", DigestUtils.md5Hex(loginCredentials.password)).findUnique();

        if (login == null){
            return ok(error.render("User Not Found"));
        }
        else{
            if (login.priviledge.matches("admin")){
                session("connected", loginForm.get().username);
                session("admin", loginForm.get().username);
                return ok(admin.render(loginForm.get().username));
            }
            else{
                System.out.println("User Logged In"+login.priviledge);
                session("connected", loginForm.get().username);
                return ok(profile.render(loginForm.get().username,false));
            }
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

    public Result resetPassword(){

        return ok(resetpassword.render());
    }

    public Result updatepasswordtoken(){
        DynamicForm df = formFactory.form().bindFromRequest();

        String username = df.get("username");
        System.out.println("USERNAME FOR RESSTING PASSWORD IS "+username);

        LoginData user = LoginData.find.byId(username);

        // Random Hash generation
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 25) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        saltStr = username+","+saltStr;

        System.out.println("HASH GENERATED IS "+saltStr);

        user.resetToken = saltStr;

        user.update();

        // Sending the Link to the User email id
        String link = "http://localhost:9000/password/resetpassword/"+saltStr;
        /**MailGenerator mail = new MailGenerator();
        mail.sendEmail(username," ","Hello", "Reset Password Link"," ", link);
        **/


        Form<LoginData> loginForm = formFactory.form(LoginData.class);
        System.out.println("Login Function hit");
        return ok(login.render(loginForm, "Password Reset Link sent to the User Email"));
    }

    public Result updatepassword(String token){
        System.out.println("Token received is "+token);
        List<String> elephantList = Arrays.asList(token.split(","));

        System.out.println("USERNAME AFTER SPLIT IS "+elephantList.get(0));
        System.out.println("HASHED TOKEN AFTER SPLIT IS "+elephantList.get(1));
        String username = elephantList.get(0);

        LoginData temp = LoginData.find.byId(elephantList.get(0));
        System.out.println("User pulled from database is "+temp);
        System.out.println("User pulled from database is "+temp.resetToken);
        if (temp.resetToken.equals(token)){
            session("updatepasswordkey", username);
            return ok(updatenewpassword.render());
        }
        else{
            return ok(error.render("Please Contact Admin"));
        }
    }

    public Result changepassword(){
        DynamicForm df = formFactory.form().bindFromRequest();
        String oldpassword = df.get("oldpassword");
        String newpassword = df.get("newpassword");
        String username = session("updatepasswordkey");

        //System.out.println(oldpassword+newpassword+username);

        LoginData user = LoginData.find.byId(username);
        if(user.password.equals(DigestUtils.md5Hex(oldpassword))){
            System.out.println("In the if case");
            user.setPassword(DigestUtils.md5Hex(newpassword));
            user.resetToken = "";
            user.update();


            Form<LoginData> loginForm = formFactory.form(LoginData.class);
            System.out.println("Login Function hit");
            System.out.println("New Password Hash is"+DigestUtils.md5Hex(newpassword));
            return ok(login.render(loginForm, "Password Updated Successfully"));
        }
        else{
            Form<LoginData> loginForm = formFactory.form(LoginData.class);
            System.out.println("Login Function hit");
            return ok(login.render(loginForm, "Old Password Did not match"));
        }
    }
}

