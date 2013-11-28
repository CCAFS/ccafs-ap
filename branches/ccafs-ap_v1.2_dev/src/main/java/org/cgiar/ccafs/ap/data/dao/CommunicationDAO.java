package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCommunicationDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCommunicationDAO.class)
public interface CommunicationDAO {

  /**
   * Get all the communications that belongs to the given leader and corresponds
   * to the given logframe
   * 
   * @param leader_id
   * @param logframe_id
   * @return a list of maps with the information
   */
  public Map<String, String> getCommunicationReport(int leader_id, int logframe_id);

  /**
   * Save the data communication.
   * 
   * @param communicationData
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveCommunicationReport(Map<String, String> communicationData);
}
