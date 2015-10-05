/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.util;

import org.cgiar.ccafs.utils.APConfig;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
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

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendMail {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(SendMail.class);

  // Managers
  private APConfig config;

  @Inject
  public SendMail(APConfig config) {
    this.config = config;
  }

  /**
   * This method send an email from the main email system.
   * 
   * @param toEmail is the email or the list of emails separated by a single space. This parameter can be null.
   * @param ccEmail is the email or the list of emails separated by a single space that will be as CC. This parameter
   *        can be null.
   * @param bbcEmail is the email or the list of emails separated by a single space that will be in BBC. This parameter
   *        can be null.
   * @param subject is the email title.
   * @param messageContent the content of the email
   */
  public void send(String toEmail, String ccEmail, String bbcEmail, String subject, String messageContent) {

    // Get a Properties object
    Properties properties = System.getProperties();

    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.ssl.trust", config.getEmailHost());
    properties.put("mail.smtp.host", config.getEmailHost());
    properties.put("mail.smtp.port", config.getEmailPort());

    // Un-comment this line to watch javaMail debug
    // properties.put("mail.debug", "true");


    Session session = Session.getInstance(properties, new Authenticator() {

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(config.getEmailUsername(), config.getEmailPassword());
      }
    });

    // Create a new message
    Message msg = new MimeMessage(session);

    // Set the FROM and TO fields
    try {
      msg.setFrom(new InternetAddress(config.getEmailUsername()));
      if (toEmail != null) {
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
      }
      if (ccEmail != null) {
        msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
      }
      if (bbcEmail != null) {
        msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bbcEmail, false));
      }
      msg.setSubject(subject);
      msg.setText(messageContent);
      msg.setSentDate(new Date());
      Transport.send(msg);
      LOG.info("Message sent.");

    } catch (MessagingException e) {
      LOG.error("There was an error sending a message", e);
    }
  }

  public void sendMailWithAttachment(String toEmail, String subject, String messageContent, String filePath,
    String fileName) {

    // Get a Properties object
    Properties properties = System.getProperties();

    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", config.getEmailHost());
    properties.put("mail.smtp.port", config.getEmailPort());

    // Un-comment this line to watch javaMail debug
    // properties.put("mail.debug", "true");


    Session session = Session.getInstance(properties, new Authenticator() {

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(config.getEmailUsername(), config.getEmailPassword());
      }
    });

    // Create a new message
    Message msg = new MimeMessage(session);

    // Set the FROM and TO fields
    try {
      // Headers
      msg.setFrom(new InternetAddress(config.getEmailUsername()));
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
      msg.setSubject(subject);
      msg.setSentDate(new Date());

      // Message body
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(messageContent, "text/html");

      // Attaching file
      MimeBodyPart attachPart = new MimeBodyPart();
      DataSource source = new FileDataSource(filePath);

      attachPart.setDataHandler(new DataHandler(source));
      attachPart.setFileName(fileName);

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(attachPart);
      multipart.addBodyPart(messageBodyPart);

      msg.setContent(multipart);

      Transport.send(msg);
      LOG.info("Message sent.");

    } catch (MessagingException e) {
      LOG.error("There was an error sending a message", e);
    }
  }
}
