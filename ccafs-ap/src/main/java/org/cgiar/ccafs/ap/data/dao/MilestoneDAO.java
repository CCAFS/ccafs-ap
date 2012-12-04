package org.cgiar.ccafs.ap.data.dao;

import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLMilestoneDAO;

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

}
