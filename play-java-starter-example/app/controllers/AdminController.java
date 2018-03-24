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
public class AdminController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    @Inject
    FormFactory formFactory;

    public Result candidate() {
        Form<Candidate> candidateForm = formFactory.form(Candidate.class);
        System.out.println("Candidate Function hit");
        return ok(candidateCreation.render(candidateForm));
    }

    public Result saveCandidate() {
        Form<Candidate> candidateForm = formFactory.form(Candidate.class).bindFromRequest();
        Candidate candidate = candidateForm.get();
        candidate.save();
        return ok(admin.render(session("connected")));
    }

}

