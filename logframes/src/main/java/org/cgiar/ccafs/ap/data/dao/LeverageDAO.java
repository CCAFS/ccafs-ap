package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLeverageDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLLeverageDAO.class)
public interface LeverageDAO {

  /**
   * Get all the leverages that belongs to the given leader and
   * corresponding to the given logframe.
   * 
   * @param leader_id
   * @param logframe_id
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getLeverages(int leader_id, int logframe_id);

  /**
   * Remove the leverages that belongs to the given leader for the corresponding
   * logframe from the database.
   * 
   * @param leader_id
   * @param logframe_id
   * @return true if the leverages were successfully removed. False otherwise.
   */
  public boolean removeLeverages(int leader_id, int logframe_id);

  /**
   * Save the leverages of the leader for the given logframe.
   * 
   * @param leverages - List of leverages
   * @param leader
   * @param logframe
   * @return true if the information was successfully saved. False otherwise.
   */
  public boolean saveLeverages(List<Map<String, String>> leverages, int activity_leader_id);
}
