package org.cgiar.ccafs.ap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MD5Convert {

  private static Logger LOG = LoggerFactory.getLogger(MD5Convert.class);

  public static String stringToMD5(String value) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
      byte[] b = md.digest(value.getBytes());
      String md5HashCode = "";
      for (int i = 0; i < b.length; i++) {
        md5HashCode += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
      }
      return md5HashCode;
    } catch (NoSuchAlgorithmException e) {
      // TODO - report exception into the logging system.
      e.printStackTrace();
    }
    return null;
  }
}
