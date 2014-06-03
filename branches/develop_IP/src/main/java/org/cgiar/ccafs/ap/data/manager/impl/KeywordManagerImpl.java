package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.KeywordDAO;
import org.cgiar.ccafs.ap.data.manager.KeywordManager;
import org.cgiar.ccafs.ap.data.model.Keyword;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class KeywordManagerImpl implements KeywordManager {

  private KeywordDAO keywordDAO;

  @Inject
  public KeywordManagerImpl(KeywordDAO keywordDAO) {
    this.keywordDAO = keywordDAO;
  }

  @Override
  public Keyword getKeyword(String id) {
    Map<String, String> keywordData = keywordDAO.getKeywordInformation(id);

    Keyword keyword = new Keyword();
    keyword.setId(Integer.parseInt(keywordData.get("id")));
    keyword.setName(keywordData.get("name"));

    return keyword;
  }

  @Override
  public Keyword[] getKeywordList() {
    List<Map<String, String>> keywordDataList = keywordDAO.getKeywordList();
    Keyword[] keywords = new Keyword[keywordDataList.size()];

    for (int c = 0; c < keywordDataList.size(); c++) {
      Keyword keyword = new Keyword();
      keyword.setId(Integer.parseInt(keywordDataList.get(c).get("id")));
      keyword.setName(keywordDataList.get(c).get("name"));

      keywords[c] = keyword;
    }

    return keywords;
  }

}
