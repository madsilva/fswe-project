package controllers;

import play.mvc.*;
import views.html.*;
import models.*;
import javax.inject.Inject;
import play.data.*;
import org.apache.commons.codec.digest.DigestUtils;
//
//import anorm._;
//import play.api.db.DB;

import play.mvc.*;
import java.util.List;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

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
        if (user.password.equals(user.confPassword)){
            LoginData loginCredentials = new LoginData();
            loginCredentials.setUsername(user.username);
            loginCredentials.setFirstname(user.firstName);
            loginCredentials.setLastname(user.lastName);
            loginCredentials.setPrivilege("voter");
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
        return ok(approval.render(voterInfo));
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


        LoginData login = LoginData.find.query().where().eq("username", loginCredentials.username).eq("password", DigestUtils.md5Hex(loginCredentials.password)).findUnique();

        if (login == null){
            System.out.println("User Not Found");
            return ok(error.render("User Not Found"));
//            Form<LoginData> loginForm2 = formFactory.form(LoginData.class);
//            return badRequest(login.render(loginForm2));
        }
        else{
            if (login.privilege.matches("admin")){
                return ok(admin.render(loginForm.get().username));
            }
            else{
                System.out.println("User Logged In"+login.privilege);
                session("connected", loginForm.get().username);
                return ok(profile.render(loginForm.get().username,false));
            }
        }


//        System.out.println("Result is");
//        System.out.println(login);


//        boolean temp = loginCredentials.findLogin(loginCredentials.username, loginCredentials.password);
//
//        if (loginForm.hasErrors()) {
//            System.out.println("in");
//            return badRequest(login.render(loginForm));
//        } else {
//            if (temp) {
//                System.out.println("in else if");
//                session().clear();
//                // Creating a new session with the
//                session("email", loginForm.get().username);
//                return redirect(routes.HomeController.profile());
//            }
//            else{
//                System.out.println("in else else");
//                return badRequest(login.render(loginForm));
//            }
//        }

    }
}
