package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Partner;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class PartnerConverter extends StrutsTypeConverter {

  private PartnerManager partnerManager;

  @Inject
  public PartnerConverter(PartnerManager partnerManager) {
    this.partnerManager = partnerManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Partner.class) {
      String id = values[0];
      return partnerManager.getPartner(Integer.parseInt(id));
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Partner partner = (Partner) o;
    return partner.getId() + "";
  }

}
