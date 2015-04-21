package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
import org.cgiar.ccafs.ap.data.model.PublicationType;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class PublicationTypeConverter extends StrutsTypeConverter {

  private PublicationTypeManager publicationTypeManager;

  @Inject
  public PublicationTypeConverter(PublicationTypeManager publicationTypeManager) {
    this.publicationTypeManager = publicationTypeManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == PublicationType.class) {
      String id = values[0];
      return publicationTypeManager.getPublicationType(id);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    PublicationType type = (PublicationType) o;
    return type.getId() + "";
  }

}
