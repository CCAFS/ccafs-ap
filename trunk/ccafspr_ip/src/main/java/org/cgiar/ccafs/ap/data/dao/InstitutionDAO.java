package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLInstitutionDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLInstitutionDAO.class)
public interface InstitutionDAO {

  /**
   * This method get the information of the institutions related to the user
   * given
   * 
   * @param userID - User identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getInstitutionsByUser(int userID);

  /**
   * This method return the information of the institution marked as
   * main for the user identified with the id given
   * 
   * @param userID - User identifier
   * @return a list of maps with the information
   */
  public Map<String, String> getUserMainInstitution(int userID);
}
