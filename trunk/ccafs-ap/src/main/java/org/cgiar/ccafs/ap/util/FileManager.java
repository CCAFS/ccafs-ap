package org.cgiar.ccafs.ap.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileManager {

  private static Logger LOG = LoggerFactory.getLogger(FileManager.class);

  /**
   * Copy one file to another location
   * 
   * @param source specifies the file to be copied
   * @param destination specifies the directory and filename for the
   *        new file
   * @return true if the process of copy was successful. False otherwise
   */
  public static boolean copyFile(File source, String destination) {
    File destinationFile = new File(destination);
    try {
      FileUtils.copyFile(source, destinationFile);
    } catch (IOException e) {
      String msg = "There was an error copying file from " + source + " to " + destination;
      LOG.error(msg, e);
      return false;
    }
    return true;
  }

  /**
   * Delete one file from the hard disk
   * 
   * @param fileName specifies the file to be deleted.
   * @return true if the delete process was successful. False otherwise
   */
  public static boolean deleteFile(String fileName) {
    // First, load the image as object
    File deleteFile = new File(fileName);
    // Make sure the file or directory exists and isn't write protected
    if (!deleteFile.exists()) {
      LOG.warn("Delete: no such file or directory: " + fileName);
      // Return true because the file doesn't exists
      return true;
    }
    if (!deleteFile.canWrite()) {
      LOG.error("Delete: write protected: " + fileName);
      return false;
    }
    // Attempt to delete it
    return deleteFile.delete();
  }
}
