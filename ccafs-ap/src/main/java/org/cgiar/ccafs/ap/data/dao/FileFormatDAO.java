package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLFileFormatDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLFileFormatDAO.class)
public interface FileFormatDAO {

  /**
   * Add a set of file formats related to a given deliverable.
   * 
   * @param deliverableId - Deliverable identifier to which the set of file formats belongs.
   * @param fileFormatsIds
   * @return
   */
  public boolean addFileFormats(int deliverableId, ArrayList<String> fileFormatsIds);

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

  /**
   * Remove the old list of file formats related to the specified deliverable and add the new list identified by the
   * second parameter.
   * 
   * @param deliverableId - deliverable id.
   * @param fileFormatIds - list of file format identifiers.
   * @return
   */
  public boolean setFileFormats(int deliverableId, int[] fileFormatIds);
}
