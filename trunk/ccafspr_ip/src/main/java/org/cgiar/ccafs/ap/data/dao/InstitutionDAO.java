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
   * @return a list of maps with the information of all Institutions returned .
   */

  public List<Map<String, String>> getAllInstitutions();

  /**
   * This method returns all the information from the Institutions Type
   * 
   * @return a list of map with the information of the institution
   */
  public List<Map<String, String>> getAllInstitutionTypes();

  /**
   * This method return the information from Institution give by an institutionID
   * 
   * @param institutionID, identifier of the institution
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
   * This method return the information of an Institution Type given by and InstitutionType ID
   * 
   * @param institutionTypeID
   * @return a map with the information
   */
  public Map<String, String> getInstitutionType(int institutionTypeID);

  /**
   * This method return the information of the institution marked as
   * main for the user identified with the id given
   * 
   * @param userID - User identifier
   * @return a list of maps with the information
   */
  public Map<String, String> getUserMainInstitution(int userID);
}
