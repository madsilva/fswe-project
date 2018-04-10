package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<Election> election = Election.find.all();
        ArrayList<String> electionid = new ArrayList<String>();

        for (Election electioninfo: election){
            electionid.add(electioninfo.electionID);
        }

        List<Precinct> precinct = Precinct.find.all();
        ArrayList<String> precinctid = new ArrayList<String>();
        for (Precinct id: precinct){
            precinctid.add(id.precinctID);
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(precinctid);
        precinctid.clear();
        precinctid.addAll(hs);

        String message = "";

        return ok(candidateCreation.render(candidateForm, electionid, precinctid, message));
    }

    public Result saveCandidate() {
        Form<Candidate> candidateForm = formFactory.form(Candidate.class).bindFromRequest();
        Candidate candidate = candidateForm.get();
        candidate.save();


        List<Election> election = Election.find.all();
        ArrayList<String> electionid = new ArrayList<String>();

        for (Election electioninfo: election){
            electionid.add(electioninfo.electionID);
        }

        List<Precinct> precinct = Precinct.find.all();
        ArrayList<String> precinctid = new ArrayList<String>();
        for (Precinct id: precinct){
            precinctid.add(id.precinctID);
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(precinctid);
        precinctid.clear();
        precinctid.addAll(hs);

        String message = "The candidate is saved, Add New Candidate";
        return ok(candidateCreation.render(candidateForm, electionid, precinctid, message));
    }

    public Result candidateList() {
        List<Candidate> candidates = Candidate.find.all();
        return ok(candidateList.render(candidates));
    }

}

