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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityKeywordManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityKeyword;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityKeywordManagerImpl.class)
public interface ActivityKeywordManager {

  /**
   * Get the keywords related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list o keyword objects with the information
   */
  public List<ActivityKeyword> getKeywordList(int activityID);

  /**
   * Delete all the keywords related to the activity given from the database.
   * 
   * @param activityID - Activity identifier
   * @return true if the records were successfully removed. False otherwise.
   */
  public boolean removeActivityKeywords(int activityID);

  /**
   * Save a list of activity keywords into the database
   * 
   * @param keywords - The information to be saved
   * @param activityID - activity identifier
   * @return true if ALL the ActivityKeyword was successfully saved
   */
  public boolean saveKeywordList(List<ActivityKeyword> keywords, int activityID);
}
