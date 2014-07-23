package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectPartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLProjectPartnerDAO.class)
public interface ProjectPartnerDAO {

  /**
   * This method deletes the project partner given the project Id and the institution Id
   * 
   * @param projectId
   * @param partnerId
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteProjectPartner(int projectId, int partnerId);

  /**
   * This method gets the project partners information given the project Id
   * 
   * @param projectId - is the id of a project
   * @return true if the elements were deleted successfully. If an error occurs, a NULL will be returned.
   */
  public List<Map<String, String>> getProjectPartners(int projectId);

  /**
   * This method saves into the database a new Project Partner
   * 
   * @param projectPartnerData - Information to be saved
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error recorded.
   */
  public int saveProjectPartner(Map<String, Object> projectPartnerData);


}
