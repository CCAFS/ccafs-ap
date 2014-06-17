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

import org.cgiar.ccafs.ap.data.manager.CaseStudyTypeManager;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class CaseStudyTypesConverter extends StrutsTypeConverter {

  private CaseStudyTypeManager caseStudyTypeManager;

  @Inject
  public CaseStudyTypesConverter(CaseStudyTypeManager caseStudyTypeManager) {
    this.caseStudyTypeManager = caseStudyTypeManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    List<CaseStudyType> caseStudyTypeList = new ArrayList<>();
    if (toClass == List.class) {
      caseStudyTypeList = caseStudyTypeManager.getCaseStudyTypeList(values);
    }
    return caseStudyTypeList;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<CaseStudyType> caseStudyTypesArray = (List<CaseStudyType>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (CaseStudyType cst : caseStudyTypesArray) {
      temp.add(String.valueOf(cst.getId()));
    }
    return temp.toString();
  }
}
