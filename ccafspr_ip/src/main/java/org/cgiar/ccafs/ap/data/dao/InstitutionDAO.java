package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLInstitutionDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLInstitutionDAO.class)
public interface InstitutionDAO {

  /**
   * This method return all the Institutions
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */

  public List<Map<String, String>> getAllInstitutions();

  /**
   * This method return a all the IP elements which belongs to the program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */

  public Map<String, String> getInstitution(int institutionID);

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
