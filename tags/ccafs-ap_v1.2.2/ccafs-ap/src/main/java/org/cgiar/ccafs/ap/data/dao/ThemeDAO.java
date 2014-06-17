/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

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

  /**
   * Get the themes on which partner works.
   * 
   * @param partnerId - The partner identifier
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getThemesByPartner(int partnerId);
}
