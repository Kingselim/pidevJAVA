package com.esprit.controllers.AssociationController;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Mailing {
    public static void sendMail(String reciepent,String subject,String text) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        String myAcountEmail="bkpower55@gmail.com";
        String myAcountPassword="vlsgxycpupgqfosg";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAcountEmail, myAcountPassword);
            }
        });
        session.setDebug(true);

        Message message =prepareMessage(session,myAcountEmail,reciepent,subject,text);
        System.out.println(message);

        Transport.send(message);
        System.out.println("Message send succ");
    }

    private static Message prepareMessage(Session session , String myAccountEmail,String recipient, String subject,String text) throws MessagingException {
        Message message= new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            System.out.println(message.getFrom());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            System.out.println(message.getRecipients(Message.RecipientType.TO));
            message.setSubject(subject);
            System.out.println(message.getSubject());
            message.setText(text);
            System.out.println();
        } catch (AddressException ex) {
            Logger.getLogger(Mailing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    public static void main(String[] args) throws Exception {

        sendMail("youssef.bkhayatia@gmail.com","test","tessssst");

    }
}





