package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLThemeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLThemeDAO.class)
public interface ThemeDAO {

  /**
   * Get all themes.
   * 
   * @return a list of maps with the information of those themes
   */
  public List<Map<String, String>> getThemes();

  /**
   * Get all the themes that belong to a given logframe id.
   * 
   * @param logframeId - The logframe identifier
   * @return a list of maps with the information of those themes
   */
  public List<Map<String, String>> getThemes(int logframeId);
}
