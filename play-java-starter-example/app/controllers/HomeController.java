package controllers;
import play.mvc.*;
import views.html.*;
import models.*;
import javax.inject.Inject;
import play.data.*;

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
        return ok(index.render("Your new application is ready."));
    }

    public Result login(){
        Form<LoginData> loginForm = formFactory.form(LoginData.class);
        System.out.println("Login Function hit");
        return ok(login.render(loginForm));
    }

    public Result signup(){
        return ok(signup.render());
    }

    public Result profile(){
        return ok(profile.render());
    }

    public Result userlogin(){
        System.out.println("User Login Function hit");

        Form<LoginData> loginForm = formFactory.form(LoginData.class).bindFromRequest();
        LoginData loginCredentials = loginForm.get();
        System.out.println("UserDetails are");

        boolean temp = loginCredentials.findLogin(loginCredentials.username, loginCredentials.password);

        if (loginForm.hasErrors()) {
            System.out.println("in");
            return badRequest(login.render(loginForm));
        } else {
            if (temp) {
                System.out.println("in else if");
                session().clear();
                // Creating a new session with the
                session("email", loginForm.get().username);
                return redirect(routes.HomeController.profile());
            }
            else{
                System.out.println("in else else");
                return badRequest(login.render(loginForm));
            }
        }
    }
}
