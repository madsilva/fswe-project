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
import java.util.Date;
import java.time.LocalDate;

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
public class ElectionController extends Controller{

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    @Inject
    FormFactory formFactory;

    public Result election(){
        Form<Election> electionForm = formFactory.form(Election.class);
        System.out.println("Election Function hit");
        return ok(election.render(electionForm));
    }

    public Result saveElection(){
        Form<Election> electionForm = formFactory.form(Election.class).bindFromRequest();
        Election election = electionForm.get();
        election.save();

        ArrayList<String> electionid = new ArrayList<String>();
        electionid.add(election.electionID);

        List<Precinct> precinct = Precinct.find.all();
        ArrayList<String> precinctid = new ArrayList<String>();
        for (Precinct id: precinct){
            precinctid.add(id.precinctID);
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(precinctid);
        precinctid.clear();
        precinctid.addAll(hs);

        for(String variable : electionid){
            // Saving the ballot
            Ballots ballot = new Ballots();
            ballot.precinct = variable;
            ballot.electionID = election.electionID;
            ballot.save();
        }

        String message = "";

        Form<Candidate> candidateForm = formFactory.form(Candidate.class);
        //return ok(candidateCreation.render(candidateForm, electionid, precinctid, message));
        return ok(uploadCandidate.render(" "));
    }

    public Result electionList() {
        List<Election> elections = Election.find.all();
        return ok(electionList.render(elections));
    }

    public Result voterElectionsView(String username) {
        LocalDate today = LocalDate.now();

        LoginData login = LoginData.find.query().where().eq("username", username).findUnique();

        List<Election> ongoingElections = Election.find.query().where().ge("end_date", today.minusDays(1)).le("start_date", today.minusDays(1)).findList();
        for (Election election : ongoingElections){

        }

        return ok(voterElectionView.render(ongoingElections));
    }

    public Result electionresults(){
        // Fetch all the election IDs and render it in the View
        // Select the election ID from the view to display the result in detail.
        List<ElectionResults> allElections = ElectionResults.find.all();
        List<String> electionIDs = new ArrayList<String>();

        for (ElectionResults temp : allElections){
            electionIDs.add(temp.electionID);
        }

        return ok(electionresults.render(electionIDs));
    }

    public Result displayelectionresults(){
        DynamicForm df = formFactory.form().bindFromRequest();
        String electionid = df.get("electionID");
        List<ElectionResults> allElections = ElectionResults.find.query().where().ge("electionID", electionid).findList();
        List<String> electionIDs = new ArrayList<String>();
        List<String> precinct = new ArrayList<String>();
        List<String> candidate = new ArrayList<String>();
        List<String> votes = new ArrayList<String>();

        for(ElectionResults temp : allElections){
            electionIDs.add(temp.electionID);
            precinct.add(temp.precinct);
            candidate.add(temp.candidate);
            votes.add(temp.votes + "");
        }

        return ok(electionresultsdisplay.render(electionIDs, precinct, candidate, votes));
    }
  
    public Result electionVerification(String electionID){
        Form<VoterVerification> verifyForm = formFactory.form(VoterVerification.class);
        System.out.println("Election Verification Function hit");
        return ok(verify.render(verifyForm, electionID));
    }

    public Result verifyForElection(String electionID){
        Form<Search> verifyForm = formFactory.form(VoterVerification.class).bindFromRequest();
        VoterVerification verifyInfo = verifyForm.get();

        VoterRegistration voterInfo = new VoterRegistration();
        voterInfo = VoterRegistration.find.query().where().eq("username", verifyInfo.username).eq("zip_code", verifyInfo.zipCode).eq("date_of_birth",verifyInfo.dateOfBirth).eq("id_number",verifyInfo.idNumber).findUnique();

        if(voterInfo != null){
            return ok(error.render("No current ballot.\n Verification Success!"));
        }
        else{
            return ok(error.render("Voter Verification Failed"));
        }
    }
}

