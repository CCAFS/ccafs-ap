package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutcomeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutcomeDAO.class)
public interface OutcomeDAO {

  /**
   * Add a list of outcomes into the database.
   * 
   * @param newOutcomes - List of Maps of outcomes to be added.
   * @return true if all the outcomes were successfully added into the database, or false if any problem occurred.
   */
  public boolean addOutcomes(List<Map<String, String>> newOutcomes);

  /**
   * Get a list with information of all outcomes that belong to a given leader and logframe identifier.
   * 
   * @param activity_leader_id - Leader identifier.
   * @param logframe_id - logframe identifier.
   * @return a list of Maps with the information of each outcome.
   */
  public List<Map<String, String>> getOutcomes(int leader_id, int logframe_id);

  /**
   * Remove a list of outcomes that belong to a specific leader and logframe.
   * 
   * @param leader_id - leader identifier.
   * @param logframe_id - logframe identifier.
   * @return true if all the outcomes were successfully removed, or false otherwise.
   */
  public boolean removeOutcomes(int leader_id, int logframe_id);
}
