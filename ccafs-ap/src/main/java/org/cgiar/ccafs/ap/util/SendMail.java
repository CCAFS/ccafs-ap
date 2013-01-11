package org.cgiar.ccafs.ap.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail {


  public static void send(String toEmail, String subject, String messageContent) {

    // Get a Properties object
    Properties properties = System.getProperties();

    properties.setProperty("proxySet", "true");
    properties.setProperty("socksProxyHost", "proxy4.ciat.cgiar.org");
    properties.setProperty("socksProxyPort", "8080");

    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.socketFactory.port", "465");
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.port", "465");

    properties.put("mail.debug", "true");

    final String username = "mail@gmail.com";
    final String password = "password";

    Session session = Session.getDefaultInstance(properties, new Authenticator() {

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    // -- Create a new message --
    Message msg = new MimeMessage(session);

    // -- Set the FROM and TO fields --
    try {
      msg.setFrom(new InternetAddress("carvajal.hernandavid@gmail.com"));
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("carvajal.hernandavid@gmail.com", false));
      msg.setSubject("Hello");
      msg.setText("How are you");
      msg.setSentDate(new Date());
      Transport.send(msg);
      System.out.println("Message sent.");

    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}
