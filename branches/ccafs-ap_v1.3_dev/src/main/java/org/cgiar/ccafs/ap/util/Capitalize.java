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
