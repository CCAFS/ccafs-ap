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
