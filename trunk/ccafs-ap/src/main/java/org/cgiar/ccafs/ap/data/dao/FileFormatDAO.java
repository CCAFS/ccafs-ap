package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLFileFormatDAO;

@ImplementedBy(MySQLFileFormatDAO.class)
public interface FileFormatDAO {

  /**
   * Get a list whit all file formats
   * 
   * @return a List of Maps that contains all file formats.
   */
  public List<Map<String, String>> getFileFormats();

  /**
   * Get a list whit file formats used by an activity
   * 
   * @param deliverableID the deliverable identifier.
   * @return a List of Maps that contains the file formats used
   *         by the deliverable whit the given identifier or null
   *         if no exists
   */
  public List<Map<String, String>> getFileFormats(int deliverableID);
}
