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


public class Capitalize {

  /**
   * This function capitalize the first word of each
   * sentence in the string received
   *
   * @param text
   * @return text capitalized
   */
  public static String capitalizeString(String text) {
    int pos = 0;
    boolean capitalize = true;
    StringBuilder sb = new StringBuilder(text.toLowerCase());

    while (pos < sb.length()) {
      if (sb.charAt(pos) == '.') {
        capitalize = true;
      } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
        sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
        capitalize = false;
      }
      pos++;
    }

    return sb.toString();
  }
}
