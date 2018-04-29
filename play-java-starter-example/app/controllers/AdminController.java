package controllers;

import java.io.*;
import java.util.*;

import play.api.mvc.MultipartFormData;
import play.mvc.*;
import scala.xml.Null;
import views.html.*;
import models.*;
import javax.inject.Inject;
import play.data.*;
import java.util.Date;
import java.time.LocalDate;

import play.api.mvc.MultipartFormData;
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

    public Result manager(){
        String user = session("connected");
        String account = session("manager");

        System.out.println("Manager hit"+user);
        if((user != null) && (account != null)) {
            //Form<VoterRegistration> voterForm = formFactory.form(VoterRegistration.class).bindFromRequest();
            //VoterRegistration voterRegistrationInfo = VoterRegistration.find.query().where().eq("username", user).findUnique();
            //System.out.println("Approved query is "+voterRegistrationInfo.username+voterRegistrationInfo.approved);
            System.out.println("Manager hit if case");
            return ok(manager.render(user));
        } else {
            System.out.println("Manager hit else case");
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
        System.out.println("Username is : "+username);
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

    public Result updateCandidatePage(int candidateID){
        Form<Candidate> candidateForm = formFactory.form(Candidate.class);
        System.out.println("Update Function hit");

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

        return ok(candidateUpdate.render(candidateForm, electionid, precinctid, candidateID));
    }

    public Result updateCandidate(int candidateID){
        Form<Candidate> candidateForm = formFactory.form(Candidate.class).bindFromRequest();
        Candidate formInfo = candidateForm.get();

        System.out.println("Candidate is : " + candidateID);
        Candidate candidateUpdate = Candidate.find.query().where().eq("candidate_id", candidateID).findUnique();
        formInfo.candidateID = candidateID;

        candidateUpdate.delete();
        formInfo.save();
        System.out.println("Candidate "+candidateUpdate.firstname + " " + candidateUpdate.lastname + " " + candidateUpdate.position);

        List<Candidate> candidates = Candidate.find.all();
        return ok(candidateList.render(candidates));
    }


    public Result candidateList() {
        List<Candidate> candidates = Candidate.find.all();
        System.out.println("Candidates"+candidates);
        return ok(candidateList.render(candidates));
    }

    public Result searchUsers(){
        Form<Search> searchForm = formFactory.form(Search.class);
        System.out.println("Search Users Function hit");

        List<String> voterNames = new ArrayList<String>();

        return ok(userSearch.render(searchForm, voterNames));
    }

    public Result searchCandidates(){
        Form<Search> searchForm = formFactory.form(Search.class);
        System.out.println("Search Candidates Function hit");

        List<String> voterNames = new ArrayList<String>();

        return ok(userSearch.render(searchForm, voterNames));
    }

    public Result candidateDemographics(){
        Form<Search> searchForm = formFactory.form(Search.class).bindFromRequest();
        Search searchInfo = searchForm.get();

        String criteria = searchInfo.criteria;
        String sqlColumn = searchInfo.sqlColumn;
        LocalDate today = LocalDate.now();

        List<VoterRegistration> voterInfo = new ArrayList<>();

        if (sqlColumn.equals("election id")){
            sqlColumn = "election_id";
            voterInfo = VoterRegistration.find.query().where().eq(sqlColumn, criteria).findList();
        }
        else{
            voterInfo = VoterRegistration.find.query().where().eq(sqlColumn, criteria).findList();
        }

        List<String> voterNames = new ArrayList<String>();

        for(VoterRegistration voter : voterInfo){
            voterNames.add(voter.username);
        }

        return ok(userSearch.render(searchForm,voterNames));
    }

    public Result search(){
        Form<Search> searchForm = formFactory.form(Search.class).bindFromRequest();
        Search searchInfo = searchForm.get();

        String criteria = searchInfo.criteria;
        String sqlColumn = searchInfo.sqlColumn;
        LocalDate today = LocalDate.now();

        List<VoterRegistration> voterInfo = new ArrayList<>();

        if (sqlColumn.equals("approved") && (criteria.equals("true") || criteria.equals("yes"))){
            voterInfo = VoterRegistration.find.query().where().eq("approved", true).findList();
        }
        else if (sqlColumn.equals("approved") && (criteria.equals("false") || criteria.equals("no"))){
            voterInfo = VoterRegistration.find.query().where().eq("approved", false).findList();
        }
        else if (sqlColumn.equals("zip code")){
            sqlColumn = "zip_code";
            voterInfo = VoterRegistration.find.query().where().eq(sqlColumn, criteria).findList();
        }
        else if (sqlColumn.equals("age")){
            sqlColumn = "date_of_birth";
            int age = Integer.parseInt(criteria);
            age = age * 365;

            voterInfo = VoterRegistration.find.query().where().eq(sqlColumn, today.minusDays(age)).findList();
        }
        else{
            voterInfo = VoterRegistration.find.query().where().eq(sqlColumn, criteria).findList();
        }

        List<String> voterNames = new ArrayList<String>();

        for(VoterRegistration voter : voterInfo){
            voterNames.add(voter.username);
        }

        return ok(userSearch.render(searchForm,voterNames));
    }

    public Result uploadCandidate(){
        return ok(uploadCandidate.render(" "));
    }

    public Result saveCandidateList(){
//        DynamicForm df = formFactory.form().bindFromRequest();
//        Http.MultipartFormData<Object> candidateData = request().body().asMultipartFormData();
//        String candidateList = df.get("candidatelist");

        Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> candidates = formData.getFile("candidatelist");
        if (candidates != null) {
            String fileName = candidates.getFilename();
            String contentType = candidates.getContentType();
            File file = candidates.getFile();

            System.out.println("Candidate List is \n"+fileName+"\n"+contentType+"\n");


            InputStream istream;
            OutputStream ostream;
            String candidateInfo;
            final int EOF = -1;
            ostream = System.out;
            try {
                istream = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(istream);
                BufferedReader breader = new BufferedReader(isr);
                try {
                    while ((candidateInfo = breader.readLine()) != null) {
                        System.out.println("\nTESTING "+candidateInfo+" END");
                        String[] candidateInfoSplit = candidateInfo.split(",");
                        System.out.println("\nCANDIDATE NAME "+candidateInfoSplit[0]+candidateInfoSplit[1]+" PARTY "+ candidateInfoSplit[2]+"  PRECINCT "+candidateInfoSplit[3]+ "  ELECTION ID"+candidateInfoSplit[4]+ "  POSITION"+ candidateInfoSplit[5]);
                        Candidate newCandidate = new Candidate();

                        newCandidate.setFirstname(candidateInfoSplit[0]);
                        newCandidate.setLastname(candidateInfoSplit[1]);
                        newCandidate.setParty(candidateInfoSplit[2]);
                        newCandidate.setPrecinct(candidateInfoSplit[3]);
                        newCandidate.setElectionID(candidateInfoSplit[4]);
                        newCandidate.setPosition(candidateInfoSplit[5]);
                        newCandidate.save();
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    try {
                        istream.close();
                        ostream.close();
                    } catch (IOException e) {
                        System.out.println("File did not close");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }

            return ok(uploadCandidate.render("Election is Created, Candidates Saved Successfully!!!"));
        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }

    public Result createadmin(){
        Form<LoginData> userForm = formFactory.form(LoginData.class);

        return ok(createadmin.render(userForm));
    }

    public Result saveadmin(){
        Form<UserID> userForm = formFactory.form(UserID.class).bindFromRequest();
        UserID user = userForm.get();

        System.out.println(user.password);

        if (user.password.equals(user.confPassword)){
            LoginData loginCredentials = new LoginData();
            loginCredentials.setUsername(user.username);
            loginCredentials.setFirstname(user.firstName);
            loginCredentials.setLastname(user.lastName);
            loginCredentials.setPriviledge(user.priviledge);
            System.out.println("Firstname & Lastname are : "+user.firstName+user.lastName);
            loginCredentials.setPassword(DigestUtils.md5Hex(user.password));
            loginCredentials.save();
            System.out.println("hashed password saved : "+DigestUtils.md5Hex(user.password));
        }
        else{
            System.out.println("Passwords do not match");
            return ok(error.render("Passwords do not match"));
        }

        String loggedUser = session("connected");
        return ok(admin.render(loggedUser));
    }
}

