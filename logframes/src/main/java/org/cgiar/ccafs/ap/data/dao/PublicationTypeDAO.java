package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPublicationTypeDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLPublicationTypeDAO.class)
public interface PublicationTypeDAO {

  /**
   * Get a publication type information identified with the given id.
   * 
   * @param id - identifier.
   * @return a Map with the id and name of the publication type.
   */
  public Map<String, String> getPublicationType(String id);

  /**
   * Get a Map of publication types.
   */
  public Map<String, String> getPublicationTypes();
}
