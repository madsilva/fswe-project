//package controllers;
//
//import org.apache.commons.mail.*;
//
//import play.*;
//import play.libs.Mail;
//import play.mvc.*;
//import java.util.*;
//
//public class mails extends Mailer {
//
//    public static void message(String address, String message) {
//        try {
//
//            SimpleEmail email = new SimpleEmail();
//            email.setFrom(address);
//            email.addTo("marti1125@gmail.com");
//            email.setSubject("Question");
//            email.setMsg(message);
//            Mail.send(email);
//            System.out.println("Send message was successful");
//
//        } catch (Exception e) {
//            System.out.println("Error...");
//            System.out.println(e);
//        }
//
//    }
//
//}