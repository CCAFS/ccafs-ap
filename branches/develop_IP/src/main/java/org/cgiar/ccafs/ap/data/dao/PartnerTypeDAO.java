package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPartnerTypeDAO;

@ImplementedBy(MySQLPartnerTypeDAO.class)
public interface PartnerTypeDAO {

  /**
   * Get all the partner types from the DAO.
   * 
   * @return a List of Maps with the information.
   */
  public List<Map<String, String>> getPartnerTypeList();

}
