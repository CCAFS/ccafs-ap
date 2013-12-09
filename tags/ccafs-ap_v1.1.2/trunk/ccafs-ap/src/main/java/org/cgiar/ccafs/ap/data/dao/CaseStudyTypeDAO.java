package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCaseStudyTypeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCaseStudyTypeDAO.class)
public interface CaseStudyTypeDAO {

  /**
   * Get a list with all the case study types
   * 
   * @return a Map with the types of case studies
   */
  public List<Map<String, String>> getCaseStudyTypes();

  /**
   * Get a list with all the case study types related to a case study given
   * 
   * @param caseStudyId - The case study identifier
   * @return a Map with the types of case studies
   */
  public List<Map<String, String>> getCaseStudyTypes(int caseStudyId);

  /**
   * Save into the database a list of types related to the case study given
   * 
   * @param caseStudyId - the case study identifier
   * @param typeList - The list of case study type identifier
   * @return True if the elements was successfully saved, false otherwise
   */
  public boolean saveCaseStudyTypes(int caseStudyId, int[] caseStudyTypeIds);
}
