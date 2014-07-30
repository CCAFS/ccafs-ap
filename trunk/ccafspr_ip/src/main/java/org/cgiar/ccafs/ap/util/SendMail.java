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

import org.cgiar.ccafs.ap.config.APConfig;

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

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendMail {

  // Managers
  private APConfig config;

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(SendMail.class);

  @Inject
  public SendMail(APConfig config) {
    this.config = config;
  }

  public void send(String toEmail, String subject, String messageContent) {

    // Get a Properties object
    Properties properties = System.getProperties();

    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    // Un-comment this line to watch javaMail debug
    // properties.put("mail.debug", "true");


    Session session = Session.getInstance(properties, new Authenticator() {

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(config.getGmailUsername(), config.getGmailPassword());
      }
    });

    // Create a new message
    Message msg = new MimeMessage(session);

    // Set the FROM and TO fields
    try {
      msg.setFrom(new InternetAddress(config.getGmailUsername().contains("@") ? config.getGmailUsername() : config
        .getGmailUsername() + "@gmail.com"));
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
      msg.setSubject(subject);
      msg.setText(messageContent);
      msg.setSentDate(new Date());
      Transport.send(msg);
      LOG.info("Message sent.");

    } catch (MessagingException e) {
      LOG.error("There was an error sending a message", e);
    }
  }
}
