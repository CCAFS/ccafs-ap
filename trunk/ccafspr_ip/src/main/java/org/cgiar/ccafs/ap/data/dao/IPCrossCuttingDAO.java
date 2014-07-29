package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPCrossCuttingDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIPCrossCuttingDAO.class)
public interface IPCrossCuttingDAO {

  /**
   * This Method return the information of a IP Cross Cutting Theme by a given ID of a Theme
   * 
   * @param ipThemeID - is the Id of a IP Cross Cutting Theme
   * @return a Map with the information of a IP Cross Cutting Theme
   */
  public Map<String, String> getIPCrossCutting(int iD);

  /**
   * This method return the information of a IP Cross Cutting Theme by a given Project ID
   * 
   * @param projectID
   * @return a List of Map with the information of IP Cross Cutting Themes
   */
  public List<Map<String, String>> getIPCrossCuttingByProject(int projectID);

  /**
   * This method return a all the IP Cross Cutting Themes
   * 
   * @return a list of maps with the information of all IP Cross Cutting Themes.
   */
  public List<Map<String, String>> getIPCrossCuttings();

}
