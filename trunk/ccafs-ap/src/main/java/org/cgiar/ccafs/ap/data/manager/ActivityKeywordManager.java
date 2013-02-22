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
}
