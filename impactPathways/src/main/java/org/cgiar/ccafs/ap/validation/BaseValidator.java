package org.cgiar.ccafs.ap.validation;

import javax.mail.internet.InternetAddress;

import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseValidator extends ActionSupport {

  private static final Logger LOG = LoggerFactory.getLogger(BaseValidator.class);

  private StringBuilder validationMessage;


  public BaseValidator() {
    validationMessage = new StringBuilder();
  }

  protected void addMessage(String message) {
    if (message.isEmpty()) {
      validationMessage.append(message);
    } else {
      validationMessage.append(", ");
      validationMessage.append(message);
    }
  }

  protected boolean isValidEmail(String email) {
    if (email != null) {
      try {
        InternetAddress internetAddress = new InternetAddress(email);
        internetAddress.validate();
        return true;
      } catch (javax.mail.internet.AddressException e) {
        email = (email == null) ? "" : email;
        LOG.debug("Email address was invalid: " + email);
      }
    }
    return false;
  }

  /**
   * This method validates that the string received is not null and is not empty.
   * 
   * @param string
   * @return true if the string is valid. False otherwise.
   */
  protected boolean isValidString(String string) {
    if (string != null) {
      return !string.trim().isEmpty();
    }
    return false;
  }
}
