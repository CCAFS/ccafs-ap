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
