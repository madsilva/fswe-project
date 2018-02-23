package controllers;

import play.mvc.*;

import views.html.*;
import views.html.users.*;
import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserIDController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index(){
        return TODO;
    }

    public Result create(){
        Form<UserID> userForm = FormFactory.form(UserID.class);
        return  ok(create.render(userForm));
    }

    public Result save(){
        return TODO;
    }

    public Result edit(){
        return TODO;
    }

    public Result update(){
        return TODO;
    }

    public Result delete(){
        return TODO;
    }

    public Result show(){
        return TODO;
    }

}
