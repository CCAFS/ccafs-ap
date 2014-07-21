package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectPartnerDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLProjectPartnerDAO.class)
public interface ProjectPartnerDAO {

  /**
   * This method create into the database a new Project Partner
   * 
   * @param projectPartnerData - Information to be saved
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error recorded.
   */
  public int createProjectPartner(Map<String, Object> projectPartnerData);

  /**
   * This method deletes the project partner given the project Id and the institution Id
   * and which are of the same type given
   * 
   * @param projectId
   * @param institutionId
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteProjectPartner(int projectId, int institutionId);


}
