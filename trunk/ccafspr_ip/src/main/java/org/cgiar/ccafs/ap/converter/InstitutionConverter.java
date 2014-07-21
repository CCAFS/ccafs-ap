package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Institution;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class InstitutionConverter extends StrutsTypeConverter {

  private InstitutionManager institutionManager;

  @Inject
  public InstitutionConverter(InstitutionManager institutionManager) {
    this.institutionManager = institutionManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Institution.class) {
      String id = values[0];
      // *********** TEMPORAL ****************
      Institution inst = new Institution();
      inst.setId(100);
      inst.setName("Institution Name");
      inst.setAcronym("-NN-");
      System.out.println("Running InstitutionConverter.java... (convertFromString)");
      return inst;
      // **************************************
      // return institutionManager.getInstitution(Integer.parseInt(id));
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Institution institution = (Institution) o;
    return institution.getId() + "";
  }

}
