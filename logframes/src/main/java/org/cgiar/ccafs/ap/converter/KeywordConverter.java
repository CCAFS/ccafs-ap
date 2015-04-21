package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.KeywordManager;
import org.cgiar.ccafs.ap.data.model.Keyword;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class KeywordConverter extends StrutsTypeConverter {

  private KeywordManager keywordManager;

  @Inject
  public KeywordConverter(KeywordManager keywordManager) {
    this.keywordManager = keywordManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Keyword.class) {
      return keywordManager.getKeyword(values[0]);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Keyword keyword = (Keyword) o;
    return keyword.getId() + "";
  }


}
