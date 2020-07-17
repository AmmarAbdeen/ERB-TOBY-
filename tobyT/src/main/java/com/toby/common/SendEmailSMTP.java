/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.common;

import com.toby.entity.TobyUser;
import com.sun.mail.smtp.SMTPTransport;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author amr
 */
public class SendEmailSMTP {
    // for example, smtp.mailgun.org

    private static final String SMTP_SERVER = "mail.toby-it.com";
//    private static final String USERNAME = "errorerp@toby-it.com";
//    private static final String PASSWORD = "123456";

//    private static final String EMAIL_FROM = "errorerp@toby-it.com";
//    private static final String EMAIL_TO = "errorerp@toby-it.com";
    private static final String EMAIL_TO_CC = "";

    public void sendEmail(TobyUser tobyUser , String error , String  userName , String password) {
        StringBuilder EMAIL_TEXT = new StringBuilder();
        String EMAIL_SUBJECT = "Error branchId :> "+ tobyUser.getBranchId().getId() +" name : "+tobyUser.getBranchId().getNameAr();
        
        EMAIL_TEXT.append("UserId -> ").append(tobyUser.getId()).append("\n");
        EMAIL_TEXT.append("UserName -> ").append(tobyUser.getName()).append("\n");
        EMAIL_TEXT.append("branchId -> ").append(tobyUser.getBranchId().getId()).append("\n");
        EMAIL_TEXT.append("branchName -> ").append(tobyUser.getBranchId().getNameAr()).append("\n");
        EMAIL_TEXT.append(error);

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(userName));

            // to 
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userName, false));

            // cc
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(EMAIL_TO_CC, false));

            // subject
            msg.setSubject(EMAIL_SUBJECT);

            // content 
            msg.setText(EMAIL_TEXT.toString());

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, userName, password);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
       
}

    }
        
