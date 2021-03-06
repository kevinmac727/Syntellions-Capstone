
package services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;

public class EmailService
{

    public static void sendEmail(String email, String message, int type)
    {
        String messageEmail = null;
        String subjectEmail = null;
        
        if (type == 1)
        {
            messageEmail = "Your verification code is:\n ";
            subjectEmail = "Verification Email";
        }
        else
        {
            messageEmail = "Thank you for choosing Lion Express!\n " + "Your receipt can be found below:\n ";
            subjectEmail = "Order Receipt";
        }
       
        try
        {
            String host ="smtp.gmail.com" ;
            String user = "LionExpressFood@gmail.com";
            String pass = "syntellions231";
            String to = email;
            String from = "testingjavasyntel@gmail.com";
            String subject = subjectEmail;
            String messageText = (messageEmail + " " + message);
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println("Email sent successfully to: " + to + "\n");
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }

    }
    
    
   
}
