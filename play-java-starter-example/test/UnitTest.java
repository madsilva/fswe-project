import akka.actor.ActorSystem;
import controllers.AdminController;
import controllers.HomeController;
import controllers.ElectionController;
import org.junit.Test;
import org.junit.Before;
import play.mvc.Http;
import play.mvc.Result;
import scala.concurrent.ExecutionContextExecutor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;

import static org.awaitility.Awaitility.await;
import static play.test.Helpers.contentAsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;


import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Unit testing does not require Play application start up.
 *
 * https://www.playframework.com/documentation/latest/JavaTest
 */
public class UnitTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

//    // Unit test a controller
//    @Test
//    public void testCount() {
//        final CountController controller = new CountController(() -> 49);
//        Result result = controller.count();
//        assertThat(contentAsString(result)).isEqualTo("49");
//    }
//
//    // Unit test a controller with async return
//    @Test
//    public void testAsync() {
//        final ActorSystem actorSystem = ActorSystem.create("test");
//        try {
//            final ExecutionContextExecutor ec = actorSystem.dispatcher();
//            final AsyncController controller = new AsyncController(actorSystem, ec);
//            final CompletionStage<Result> future = controller.message();
//
//            // Block until the result is completed
//            await().until(() -> {
//                assertThat(future.toCompletableFuture()).isCompletedWithValueMatching(result -> {
//                    return contentAsString(result).equals("Hi!");
//                });
//            });
//        } finally {
//            actorSystem.terminate();
//        }
//    }

    //Unit test for Admin Controller Update method for updating the approval of the voter
    @Test
    public void testUpdateToken(){
        final AdminController controller = new AdminController();
        Result result = controller.update("nabeel-khan@uiowa.edu");
        assertEquals(OK, result.status());
    }

    //Unit test for Admin Controller approval method
    @Test
    public void testApproval(){
        final AdminController controller = new AdminController();
        Result result = controller.approval();

        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Unapproved Voter Registrations"));
    }

    // Unit test for Admin Controller admin method
//    @Test
//    public void testAdmin(){
//        final AdminController controller = new AdminController();
//
//        // Use something to simulate session, as session is giving error while running this test.
//        Result result = controller.admin();
//
//        assertEquals(OK, result.status());
//    }

//    @Test
//    public void testUpdate(){
//        final AdminController controller = new AdminController();
//        Result result = controller.update("nabeelahmadkh@gmail.com");
//
//        assertEquals(OK, result.status());
//    }
//        assertEquals(OK, result.status());
//    }

    /***************************************************
     * Unit Tests for ElecitonController
     * @author jpohlman
     ***************************************************/
    @Test
    public void testElection(){
        final ElectionController controller = new ElectionController();
        Result result = controller.election();

        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Create an Election"));
    }

    @Test
    public void testElectionList(){
        final ElectionController controller = new ElectionController();
        Result result = controller.electionList();

        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Available Elections"));
    }

    @Test
    public void testElectionResults(){
        final ElectionController controller = new ElectionController();
        Result result = controller.electionresults();

        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Select the Election ID to display the results"));
    }

    @Test
    public void testElectionResultsDisplay(){
        final ElectionController controller = new ElectionController();
        Result result = controller.displayelectionresults();

        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Election Results"));
    }

    @Test
    public void testElectionVerification(){
        final ElectionController controller = new ElectionController();
        Result result = controller.electionVerification();

        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Election Results"));
    }

    @Test
    public void testElectionVoterView(){
        final ElectionController controller = new ElectionController();
        Result result = controller.voterElectionsView();

        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Upcoming Elections"));
    }
}