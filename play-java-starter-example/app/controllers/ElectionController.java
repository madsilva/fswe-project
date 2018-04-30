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

import controllers.*;


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
        if (election.electionType.equals("PresidentialElection")){
            election.state = "USA";
        }
        election.save();


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

        Form<Candidate> candidateForm = formFactory.form(Candidate.class);
        //return ok(candidateCreation.render(candidateForm, electionid, precinctid, message));
        return ok(uploadCandidate.render(" "));
    }

    public Result electionList() {
        List<Election> elections = Election.find.all();
        return ok(electionList.render(elections));
    }

    public Result voterElectionsView(String username) {
        System.out.println("IN THE VOTER ELECTION VIEW ");
        LocalDate today = LocalDate.now();

        VoterRegistration voter = VoterRegistration.find.query().where().eq("username", username).findUnique();

        boolean approved = false;
        if (voter != null){
            approved = voter.approved;
        }

        if (!approved) {
            return ok(error.render("user is not approved to vote in elections."));
        }


        // this will only work for 1 zip code per precinct???
        String voterPrecinctID = Precinct.find.query().where().eq("zip", voter.zipCode).findUnique().precinctID;
        String voterState = Precinct.find.query().where().eq("zip", voter.zipCode).findUnique().state;

        List<Election> ongoingElections = Election.find.query().where().ge("end_date", today.plusDays(1)).le("start_date", today.plusDays(1)).findList();

        System.out.println("MIDWAY  "+ongoingElections);

        Iterator<Election> iter = ongoingElections.iterator();

        while (iter.hasNext()) {
            Election election = iter.next();
            System.out.println("PrecinctID"+election.precinctID+"END");
            if(election.electionType.equals("StateElection")){
                if(election.state.equals(voterState)){

                }else{
                    iter.remove();
                }
            }
            else if(election.electionType.equals("LocalElection")){
                if(election.precinctID.equals(voterPrecinctID)){

                }else{
                    iter.remove();
                }
            }
//            if (election.precinctID.equals("")) {
//                System.out.println("IN 10ND");
//                if (!election.state.trim().equals(voter.state.trim())) {
//                    System.out.println("IN 1ST");
//                    iter.remove();
//                }
//            }
//            else {
//                if (election.state != null) {
//                    System.out.println("IN 6ND");
//                    if (election.precinctID != voterPrecinctID) {
//                        System.out.println("IN 7ND");
//                        iter.remove();
//                    }
//                }
//            }
        }


        System.out.println("IN 3ND");

        String[] alreadyVotedInElections = voter.electionsVotedIn.split(" ");
        iter = ongoingElections.iterator();
        while (iter.hasNext()) {
            Election election = iter.next();
            if (Arrays.asList(alreadyVotedInElections).contains(election.electionID)) {
                System.out.println("IN 8th");
                iter.remove();
            }
        }

        System.out.println("ELECTION ARE "+ongoingElections);
        return ok(voterElectionView.render(ongoingElections));
    }

    public Result electionresults(){
        // Fetch all the election IDs and render it in the View
        // Select the election ID from the view to display the result in detail.
        List<Election> allElections = Election.find.all();
        List<String> electionIDs = new ArrayList<String>();

        for (Election temp : allElections){
            electionIDs.add(temp.electionID);
        }

        return ok(electionresults.render(electionIDs));
    }

    public Result displayelectionresults(){
        DynamicForm df = formFactory.form().bindFromRequest();
        String electionid = df.get("electionID");

        Election election = Election.find.query().where().eq("election_id", electionid).findUnique();
        List<Candidate> candidates = Candidate.find.query().where().eq("election_id", electionid).findList();

        List<Candidate> senatorList = new ArrayList<>();
        List<Candidate> representativeList = new ArrayList<>();
        List<Candidate> mayorList = new ArrayList<>();
        List<Candidate> governorList = new ArrayList<>();
        List<Candidate> presidentList = new ArrayList<>();

        for (Candidate candidate : candidates) {
            if (candidate.position.equals("senator")){
                senatorList.add(candidate);
            }
            else if (candidate.position.equals("us representative")){
                representativeList.add(candidate);
            }
            else if (candidate.position.equals("mayor")){
                mayorList.add(candidate);
            }
            else if (candidate.position.equals("governor")){
                governorList.add(candidate);
            }
            else if (candidate.position.equals("president")){
                presidentList.add(candidate);
            }
        }

        return ok(electionresultsdisplay.render(senatorList, governorList, mayorList, representativeList, presidentList, election));



    }
  
    public Result electionVerification(String electionID){
        Form<VoterVerification> verifyForm = formFactory.form(VoterVerification.class);
        System.out.println("Election Verification Function hit");
        return ok(verify.render(verifyForm, electionID));
    }

    public Result verifyForElection(String electionID){
        Form<VoterVerification> verifyForm = formFactory.form(VoterVerification.class).bindFromRequest();
        VoterVerification verifyInfo = verifyForm.get();

        VoterRegistration voterInfo = new VoterRegistration();
        voterInfo = VoterRegistration.find.query().where().eq("username", verifyInfo.username).eq("zip_code", verifyInfo.zipCode).eq("date_of_birth",verifyInfo.dateOfBirth).eq("id_number",verifyInfo.idNumber).findUnique();

        if(voterInfo != null){
            return redirect("/vote/"+electionID);
        }
        else{
            return ok(error.render("Voter Verification Failed"));
        }
    }

    public Result vote(String electionID) {
        Form<Ballots> ballotForm = formFactory.form(Ballots.class);
        List<Candidate> candidates = Candidate.find.query().where().eq("election_id", electionID).findList();
        List<Candidate> senatorList = new ArrayList<>();
        List<Candidate> representativeList = new ArrayList<>();
        List<Candidate> mayorList = new ArrayList<>();
        List<Candidate> governorList = new ArrayList<>();
        List<Candidate> presidentList = new ArrayList<>();

        Election elec = Election.find.byId(electionID);
        if (elec.electionType.equals("StateElection")){
            for (Candidate person : candidates){
                if (person.position.equals("senator")){
                    senatorList.add(person);
                }
                else if (person.position.equals("us representative")){
                    representativeList.add(person);
                }
                else if (person.position.equals("governor")){
                    governorList.add(person);
                }
            }
            return ok(ballot.render(electionID, senatorList,representativeList, governorList, ballotForm));
        }
        else if (elec.electionType.equals("LocalElection")){
            for (Candidate person : candidates){
                if (person.position.equals("mayor")){
                    mayorList.add(person);
                }
            }
            return ok(localballot.render(electionID, mayorList, ballotForm));
        }
        else{
            for (Candidate person : candidates){
                if (person.position.equals("president")){
                    presidentList.add(person);
                }
            }
            return ok(presidentballot.render(electionID, presidentList, ballotForm));
        }
    }

    public Result saveVote() {
        Form<Ballots> ballotForm = formFactory.form(Ballots.class).bindFromRequest();
        Ballots ballotInfo = ballotForm.get();
        String senatorID = "";
        String usRepresentativeID = "";
        String mayorID = "";
        String governorID = "";
        String electionID = "";
        String presidentID = "";

        senatorID = ballotInfo.senator;
        System.out.println("Senator = " + senatorID);
        usRepresentativeID = ballotInfo.usRepresentative;
        mayorID = ballotInfo.mayor;
        governorID = ballotInfo.governor;
        presidentID = ballotInfo.president;


        Candidate candidate = Candidate.find.query().where().eq("candidate_id", senatorID).findUnique();
        if (candidate != null){
            System.out.println("saveVote Candidate if Statement");
            candidate.votes += 1;
            candidate.save();
            electionID = candidate.electionID;
        }


        candidate = Candidate.find.query().where().eq("candidate_id", usRepresentativeID).findUnique();
        if (candidate != null){
            candidate.votes += 1;
            candidate.save();
            electionID = candidate.electionID;
        }

        candidate = Candidate.find.query().where().eq("candidate_id", mayorID).findUnique();
        if (candidate != null){
            candidate.votes += 1;
            candidate.save();
            electionID = candidate.electionID;
        }

        candidate = Candidate.find.query().where().eq("candidate_id", governorID).findUnique();
        if (candidate != null){
            candidate.votes += 1;
            candidate.save();
            electionID = candidate.electionID;
        }

        candidate = Candidate.find.query().where().eq("candidate_id", presidentID).findUnique();
        if (candidate != null){
            candidate.votes += 1;
            candidate.save();
            electionID = candidate.electionID;
        }

        String user = session("connected");
        if(!electionID.equals("")){
            VoterRegistration voter = VoterRegistration.find.query().where().eq("username", user).findUnique();
            voter.electionsVotedIn += electionID + " ";
            voter.save();
        }

        //return redirect("/");
        return ok(positivefeedback.render("You have Successfully Voted for your Candidate!!!"));
    }
}