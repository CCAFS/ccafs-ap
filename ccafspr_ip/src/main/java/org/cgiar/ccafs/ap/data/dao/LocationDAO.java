package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLocationDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLLocationDAO.class)
public interface LocationDAO {

  /**
   * This method return the information from an specific location given by the type, and the location
   * 
   * @param typeID, identifier of the location element type
   * @param locationID, identifier of the location
   * @return a map with the information of the location returned.
   */

  public Map<String, String> getLocation(int typeID, int locationID);

  /**
   * This method return all the Locations given by a type
   * 
   * @param typeID, identifier of the location element type
   * @return a list of maps with the information of all locations returned.
   */

  public List<Map<String, String>> getLocationsByType(int typeID);


}
