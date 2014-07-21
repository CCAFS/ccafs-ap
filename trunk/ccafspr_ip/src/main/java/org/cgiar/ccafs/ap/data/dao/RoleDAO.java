package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLRoleDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLRoleDAO.class)
public interface RoleDAO {

  /**
   * This method gets the user role according to the institution given
   * 
   * @param userID
   * @param institutionID
   * @return a Map with the information of the role
   */
  public Map<String, String> getRole(int userID, int institutionID);
}
