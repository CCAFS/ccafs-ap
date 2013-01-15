package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutputDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutputDAO.class)
public interface OutputDAO {

  /**
   * Get all the outputs of each activity that belongs to a given
   * activity leader
   * 
   * @param activityLeaderId - The activity leader identifier
   * @return a list with the information
   */
  public List<Map<String, String>> getOutputsList(int activityLeaderId);

}
