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
      for (byte element : b) {
        md5HashCode += Integer.toString((element & 0xff) + 0x100, 16).substring(1);
      }
      return md5HashCode;
    } catch (NoSuchAlgorithmException e) {
      LOG.error("There was a problem trying to encript the string. ", e);
    }
    return null;
  }
}
