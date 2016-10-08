package za.ac.kidsfountain.utils;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by tlharihani on 9/23/16.
 */
public class SendEmail {
    public String sendEmail(String userEmail,String subject,String bodyMessage){
        try {

            String to = userEmail;

            String from = "tlharihani50@gmail.com";
            final String username = from;
            final String password = "212340057";

            String host = "smtp.gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject(subject);
            String body = bodyMessage ;
            message.setText(body);

            Transport.send(message);



          String mess = "Message sent successfully....";
            System.out.println(mess);
            return mess;

        } catch (Exception e){
            String message = ("An error occurred while sending email to the user lol ai: " + e.getLocalizedMessage());
            System.out.println(message);
           return message;

        }


    }
}
