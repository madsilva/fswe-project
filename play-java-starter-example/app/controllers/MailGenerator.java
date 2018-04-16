/**
package controllers;

import akka.routing.FromConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//import constants.SurveyConstants;
//import controller.CrispController;

public class MailGenerator {

    public static final String FROM_EMAIL = "nabeel-khan@uiowa.edu";

    public static final String EMAIL_USERNAME = "nakhn@uiowa.edu";

    public static final String EMAIL_PASSWORD = "";

    public static final String EMAIL_HOST = "smtp.office365.com";

    public static final String EMAIL_PORT = "587";

    public MailGenerator() {
        //processEmail(CrispController.emailMap);

    }

    private void processEmail(HashMap<String, String> emailMap) {
        /*for (String key : emailMap.keySet()) {
            String value[] = emailMap.get(key).split(",");
            ArrayList<Object> temp = checkDate(value);
            boolean itIsTime = false;
            int pairingNumber = 0;
            if (temp.size() == 2) {
                itIsTime = (boolean) temp.get(0);
                pairingNumber = (int) temp.get(1);
            }
            if (itIsTime) {
                String attachmentLocation = SurveyConstants.PAIR_INPUT_LOCATION.replace("Input", "Output") + "\\" + key
                        + ".csv";
                String emailTO = value[0];
                String emailCC = value[1];
                String salutation = "Hi " + (emailTO.charAt(0) + "").toUpperCase()
                        + emailTO.substring(1, emailTO.indexOf("-"));
                String subject = "Pairs#" + pairingNumber + " for " + key;
                String filename = key + ".csv";
                sendEmail(emailTO, emailCC, salutation, subject, attachmentLocation, filename);
            }
        }*/
/**
    }

    public boolean sendEmail(String emailTO, String emailCC, String salutation, String subject,
                              String attachmentLocation, String filename) {

        boolean result = false;

        //String from = SurveyConstants.FROM_EMAIL;
        String from = FROM_EMAIL;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", EMAIL_HOST);
        props.put("mail.smtp.port", EMAIL_PORT);

        String emailBody = "<html><body style=\"font-family:Palatino Linotype;font-size:14;\">"
                + "<p>" + salutation + ",</p>"
                + "<p> Password Reset Link,</p>"
                + "<p>"+filename+"</p>"
                + "<p style=\"color:#005580;\">Regards,<br/>Election System.<br/><br/></p>"
                + "</body></html>";

        // Get the Session object.
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTO));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailCC));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(from));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            // messageBodyPart.setText(SurveyConstants.EMAIL_BODY);
            messageBodyPart.setContent(emailBody, "text/html; charset=utf-8");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentLocation);
            messageBodyPart.setDataHandler(new DataHandler(source));
            //messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            result = true;

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            result = false;
        }

        return result;
    }

    private ArrayList<Object> checkDate(String pairDates[]) {
        Calendar c = Calendar.getInstance();
        ArrayList<Object> result = new ArrayList<>();
        try {
            for (int i = 2; i < pairDates.length; i++) {
                Date temp = new SimpleDateFormat("dd-MMM-yy").parse(pairDates[i]);
                Calendar cTemp = Calendar.getInstance();
                cTemp.setTime(temp);
                long diff = Math.abs(cTemp.getTimeInMillis() - c.getTimeInMillis());
                int days = Math.abs((int) (diff / 1000 / 60 / 60 / 24));
                if (days > -3 && days < 3) {
                    result.add(true);
                    result.add(i-1);
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}**/
