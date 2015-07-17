package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLRegionDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLRegionDAO.class)
public interface RegionDAO {

  /**
   * Get all the regions from the DAO
   * 
   * @return a List of Maps with the information
   */
  public List<Map<String, String>> getRegionsList();

}
