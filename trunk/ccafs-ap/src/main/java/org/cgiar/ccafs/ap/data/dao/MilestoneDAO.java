package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLMilestoneDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLMilestoneDAO.class)
public interface MilestoneDAO {

  /**
   * Get the milestone data whit a given identifier.
   * 
   * @param milestoneID - milestone identifier.
   * @return the Map with the milestone data.
   */
  Map<String, String> getMilestone(int milestoneID);

  /**
   * Get the complete list of milestones that belongs to the given logframe
   * 
   * @param logframeID - Logframe identifier
   * @return a list of maps with the information
   */
  List<Map<String, String>> getMilestoneList(String logframeID);

}
