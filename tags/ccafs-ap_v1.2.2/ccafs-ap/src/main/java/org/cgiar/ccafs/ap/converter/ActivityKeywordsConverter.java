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

package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.KeywordManager;
import org.cgiar.ccafs.ap.data.model.ActivityKeyword;
import org.cgiar.ccafs.ap.data.model.Keyword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class ActivityKeywordsConverter extends StrutsTypeConverter {

  private KeywordManager keywordManager;

  @Inject
  public ActivityKeywordsConverter(KeywordManager keywordManager) {
    this.keywordManager = keywordManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      List<ActivityKeyword> keywords = new ArrayList<>();

      for (Keyword keyword : keywordManager.getKeywordList()) {
        // If the keyword is found, create the activity keyword object
        if (Arrays.asList(values).contains(String.valueOf(keyword.getId()))) {
          ActivityKeyword ak = new ActivityKeyword();
          ak.setKeyword(keyword);
          keywords.add(ak);
        }
      }
      return keywords;
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<ActivityKeyword> akArray = (List<ActivityKeyword>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (ActivityKeyword ak : akArray) {
      temp.add(ak.getId() + "");
    }
    return temp.toString();
  }

}
