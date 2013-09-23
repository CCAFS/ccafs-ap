package org.cgiar.ccafs.ap.action;

import org.cgiar.ccafs.ap.action.reporting.activities.PartnersSaveReportingAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.util.SendMail;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UnhandledExceptionAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersSaveReportingAction.class);
  private static final long serialVersionUID = 1095057952669633270L;

  // Model
  private Exception exception;

  @Inject
  public UnhandledExceptionAction(APConfig config, LogframeManager logframeManager) {
    super(config, logframeManager);
  }

  @Override
  public String execute() throws Exception {
    // Print the exception in the log
    LOG.error("There was an unexpected exception", exception);
    sendExceptionMessage();
    return super.execute();
  }

  public String getExceptionMessage() {
    StringWriter writer = new StringWriter();
    exception.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }

  public void sendExceptionMessage() {
    String subject;
    StringBuilder message = new StringBuilder();

    StringWriter writer = new StringWriter();
    exception.printStackTrace(new PrintWriter(writer));

    subject = "Exception occurred in CCAFS P&R";
    message.append("The user " + getCurrentUser().getName() + " ");
    message.append("has experienced an exception on the platform. \n");
    message.append("This execption occurs in the server: " + config.getBaseUrl() + ".\n");
    message.append("The exception message was: \n\n");
    message.append(writer.toString());

    SendMail sendMail = new SendMail(this.config);
    sendMail.send(config.getGmailUsername(), subject, message.toString());
    LOG.info("sendExceptionMessage() > The platform has sent a message reporting a exception.", getCurrentUser()
      .getEmail());
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }
}
