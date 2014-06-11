package org.cgiar.ccafs.ap.validation;

import javax.mail.internet.InternetAddress;

import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseValidator extends ActionSupport {

  private static final long serialVersionUID = 4980405185777382143L;
  private static final Logger LOG = LoggerFactory.getLogger(BaseValidator.class);

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
}
