package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPIndicatorDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIPIndicatorDAO.class)
public interface IPIndicatorDAO {

  /**
   * This function returns all the indicators corresponding to the given
   * ip element
   * 
   * @param ipProgramElementID - IP Program Element identifier
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getIndicatorsByIpProgramElementID(int ipProgramElementID);

  /**
   * This method removes all the indicators related with the ipElement
   * identified by the parameter received.
   * 
   * @param ipElementID - ipElement identifier
   * @return true if the information was successfully removed, false otherwise.
   */
  public boolean removeIpElementIndicators(int ipElementID, int ipProgramID);

  /**
   * This method save the information of the indicator.
   * 
   * @param indicatorData - the information to be saved
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error occurred.
   */
  public int saveIndicator(Map<String, Object> indicatorData);
}
