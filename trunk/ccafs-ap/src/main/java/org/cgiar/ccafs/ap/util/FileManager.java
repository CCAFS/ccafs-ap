package org.cgiar.ccafs.ap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
   * Copy one directory to another location
   * 
   * @param src the folder to be copied
   * @param dest where the folder will be copied
   * @return true if the folder was copyied. False if any exception arised in the process.
   */
  public static boolean copyFolder(File src, File dest) {
    if (src.isDirectory()) {

      // if directory not exists, create it
      if (!dest.exists()) {
        dest.mkdir();
        System.out.println("Directory copied from " + src + "  to " + dest);
      }

      // list all the directory contents
      String files[] = src.list();

      for (String file : files) {
        // construct the src and dest file structure
        File srcFile = new File(src, file);
        File destFile = new File(dest, file);
        // recursive copy
        copyFolder(srcFile, destFile);
      }

    } else {
      // if file, then copy it
      // Use bytes stream to support all file types
      try {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];

        int length;
        // copy the file content in bytes
        while ((length = in.read(buffer)) > 0) {
          out.write(buffer, 0, length);
        }

        in.close();
        out.close();
      } catch (IOException e) {
        LOG.error("There was an exception copying the folder from " + src + " to " + dest, e);
        return false;
      }
    }

    return true;
  }

  public static boolean deleteDirectory(File directory) {
    if (directory.exists()) {
      File[] files = directory.listFiles();
      if (null != files) {
        for (File file : files) {
          if (file.isDirectory()) {
            deleteDirectory(file);
          } else {
            file.delete();
          }
        }
      }
    }
    return (directory.delete());
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
