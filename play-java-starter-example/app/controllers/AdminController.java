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

    public Result admin(){
        String user = session("connected");
        String account = session("admin");

        System.out.println("Admin hit"+user);
        if((user != null) && (account != null)) {
            //Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class).bindFromRequest();
            //VoterRegistration voterRegistrationInfo = VoterRegistration.find.query().where().eq("username", user).findUnique();
            //System.out.println("Approved query is "+voterRegistrationInfo.username+voterRegistrationInfo.approved);
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

