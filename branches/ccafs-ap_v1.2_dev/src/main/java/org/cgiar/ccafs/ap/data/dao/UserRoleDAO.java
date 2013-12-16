package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLUserRoleDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLUserRoleDAO.class)
public interface UserRoleDAO {

  /**
   * This function search for a role corresponding to the given name.
   * 
   * @param name = role to search
   * @return a map with the information or null if not exists.
   */
  public Map<String, String> getUserRole(String name);
}
