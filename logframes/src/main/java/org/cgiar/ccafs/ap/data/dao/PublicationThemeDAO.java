package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPublicationThemeDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLPublicationThemeDAO.class)
public interface PublicationThemeDAO {

  /**
   * Get all publication themes
   * 
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getPublicationThemes();

  /**
   * Get all publication themes related to the publication given
   * 
   * @param publicationId - Publication identifer
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getPublicationThemes(int publicationId);

  /**
   * Save the themes related to the publication given
   * 
   * @param publicationId - Publication identifier
   * @param ids - The list of theme identifiers
   * @return boolean if the themes were successfully saved, false otherwise.
   */
  public boolean savePublicationThemes(int publicationId, ArrayList<String> themeIds);
}
