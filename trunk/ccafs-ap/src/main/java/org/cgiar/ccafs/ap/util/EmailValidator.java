package org.cgiar.ccafs.ap.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailValidator {

  /*
   * EMAIL_PATTERN checks a basic email format, examples
   */

  // Pattern to validate email
  static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  /**
   * Validate email with regular expression
   * 
   * @param email The email address to validate
   * @return true if is a valid address, false in another way
   */

  public static boolean isValidEmail(String email) {
    Pattern pattern;
    Matcher matcher;

    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);

    return matcher.matches();
  }

}
