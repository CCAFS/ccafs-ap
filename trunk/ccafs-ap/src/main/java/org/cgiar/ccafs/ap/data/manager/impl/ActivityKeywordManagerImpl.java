package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityKeywordDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityKeywordManager;
import org.cgiar.ccafs.ap.data.model.ActivityKeyword;
import org.cgiar.ccafs.ap.data.model.Keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityKeywordManagerImpl implements ActivityKeywordManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityKeywordManagerImpl.class);
  private ActivityKeywordDAO keywordDAO;

  @Inject
  public ActivityKeywordManagerImpl(ActivityKeywordDAO keywordDAO) {
    this.keywordDAO = keywordDAO;
  }

  @Override
  public List<ActivityKeyword> getKeywordList(int activityID) {
    List<ActivityKeyword> activityKeywords = new ArrayList<>();
    List<Map<String, String>> activityKeywordDataList = keywordDAO.getKeywordList(activityID);
    for (Map<String, String> activityKeywordData : activityKeywordDataList) {
      Keyword keywordTemp = new Keyword();
      keywordTemp.setId(Integer.parseInt(activityKeywordData.get("keyword_id")));
      keywordTemp.setName(activityKeywordData.get("keyword_name"));

      ActivityKeyword activityKeywordTemp = new ActivityKeyword();
      activityKeywordTemp.setId(Integer.parseInt(activityKeywordData.get("id")));
      activityKeywordTemp.setOther(activityKeywordData.get("other"));
      activityKeywordTemp.setKeyword(keywordTemp);

      activityKeywords.add(activityKeywordTemp);
    }
    return activityKeywords;
  }

}
