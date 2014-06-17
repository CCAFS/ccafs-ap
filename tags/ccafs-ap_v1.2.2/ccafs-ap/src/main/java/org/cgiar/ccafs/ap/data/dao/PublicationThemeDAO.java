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
