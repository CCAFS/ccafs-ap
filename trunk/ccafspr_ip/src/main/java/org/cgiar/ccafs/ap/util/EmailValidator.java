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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailValidator {

  /*
   * EMAIL_PATTERN checks a basic email format, examples
   */

  // Pattern to validate email
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
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
