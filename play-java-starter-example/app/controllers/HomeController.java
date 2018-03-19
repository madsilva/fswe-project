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
        Form<LoginData> loginForm = formFactory.form(LoginData.class);
        System.out.println("Login Function hit");
        return ok(login.render(loginForm, " "));
    }

    public Result create(){
        Form<UserID> userForm = formFactory.form(UserID.class);
        System.out.println("Create Function hit");
        return  ok(create.render(userForm));
    }

    public Result save(){
        Form<UserID> userForm = formFactory.form(UserID.class).bindFromRequest();
        UserID user = userForm.get();
        if (user.password.equals(user.confPassword)){
            LoginData loginCredentials = new LoginData();
            loginCredentials.setUsername(user.username);
            loginCredentials.setFirstname(user.firstName);
            loginCredentials.setLastname(user.lastName);
            System.out.println("Firstname & Lastname are : "+user.firstName+user.lastName);
            loginCredentials.setPassword(DigestUtils.md5Hex(user.password));
            loginCredentials.save();
            System.out.println("hashed password saved : "+DigestUtils.md5Hex(user.password));
        }
        else{
            System.out.println("Passwords do not match");
            return ok(error.render());
        }

        return ok(profile.render(user.firstName));
    }

    public Result voterRegistration(){
        Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class);
        System.out.println("Voter Registration Function hit");
        return ok(voterRegistration.render(voterForm));
    }

    public Result saveVoter() {
        Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class).bindFromRequest();
        VoterRegistration voter = voterForm.get();
        voter.save();

        return ok(profile.render(voter.firstName));
    }

    public Result profile(){
        String user = session("connected");
        if(user != null) {
            return ok(profile.render(user));
        } else {
            return ok(error.render());
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
        return ok(error.render());
    }

    public Result userlogin(){
        System.out.println("User Login Function hit");

        Form<LoginData> loginForm = formFactory.form(LoginData.class).bindFromRequest();
        LoginData loginCredentials = loginForm.get();
        System.out.println("UserDetails are");


        LoginData login = LoginData.find.query().where().eq("username", loginCredentials.username).eq("password", DigestUtils.md5Hex(loginCredentials.password)).findUnique();

        if (login == null){
            System.out.println("User Not Found");
            return ok(error.render());
//            Form<LoginData> loginForm2 = formFactory.form(LoginData.class);
//            return badRequest(login.render(loginForm2));
        }
        else{
            System.out.println("User Logged In");
            session("connected", loginForm.get().username);
            return ok(profile.render(loginForm.get().username));
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
