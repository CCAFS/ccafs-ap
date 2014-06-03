package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLMilestoneStatusDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLMilestoneStatusDAO.class)
public interface MilestoneStatusDAO {

  /**
   * Get from the DAO the milestone status
   * 
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getMilestoneStatus();
}
