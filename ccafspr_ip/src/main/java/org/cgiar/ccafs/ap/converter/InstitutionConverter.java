package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Institution;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class InstitutionConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(InstitutionConverter.class);

  // Manager
  private InstitutionManager institutionManager;

  @Inject
  public InstitutionConverter(InstitutionManager institutionManager) {
    this.institutionManager = institutionManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Institution.class) {
      String id = values[0];
      try {
        LOG.debug(">> convertFromString > id = {} ", id);
        return institutionManager.getInstitution(Integer.parseInt(id));
      } catch (NumberFormatException e) {
        // Do Nothing
        LOG.error("Problem to convert Institution from String (convertFromString) for user_id = {} ", id,
          e.getMessage());
      }
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Institution institution = (Institution) o;
    LOG.debug(">> convertToString > id = {} ", institution.getId());
    return institution.getId() + "";
  }

}
