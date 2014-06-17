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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityKeywordDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityKeywordDAO.class)
public interface ActivityKeywordDAO {

  /**
   * Get the list of keywords related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getKeywordList(int activityID);

  /**
   * Remove all activity keywords related to the activity given.
   * 
   * @param activityID - Activity identifier
   * @return true if the records were successfully saved. False otherwise.
   */
  public boolean removeActivityKeywords(int activityID);

  /**
   * Save the keyword information into the database
   * 
   * @param activityID - Activity identifier
   * @param keywordID - Keyword identifier
   * @return true if the information was successfully saved. False otherwise
   */
  public boolean saveKeyword(Map<String, String> keywordData);
}
