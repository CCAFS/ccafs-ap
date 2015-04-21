package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityRegionDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityRegionDAO.class)
public interface ActivityRegionDAO {

  /**
   * Delete all the regions related to the activity given
   * 
   * @param activityID - activity identifier
   * @return true if the regions was successfully deleted. False otherwise.
   */
  public boolean deleteActivityRegions(int activityID);

  /**
   * Get all the region locations related to the activity given
   * 
   * @param activityID - Activity identifier
   * @return a List of maps with the information
   */
  public List<Map<String, String>> getActivityRegions(int activityID);

  /**
   * Save the activity region given into the database.
   * 
   * @param activityID - The activity identifier.
   * @param countryID - The region identifier.
   * @return true if the data was successfully saved. False otherwise
   */
  public boolean saveActivityRegion(int activityID, String regionID);
}
