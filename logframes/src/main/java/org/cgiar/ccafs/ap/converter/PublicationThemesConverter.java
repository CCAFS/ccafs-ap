package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.PublicationThemeManager;
import org.cgiar.ccafs.ap.data.model.PublicationTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class PublicationThemesConverter extends StrutsTypeConverter {

  private PublicationThemeManager publicationThemeManager;

  @Inject
  public PublicationThemesConverter(PublicationThemeManager publicationThemeManager) {
    this.publicationThemeManager = publicationThemeManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    PublicationTheme[] pubThemes = publicationThemeManager.getPublicationThemes(values);
    return pubThemes;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<PublicationTheme> themesArray = (List<PublicationTheme>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (PublicationTheme t : themesArray) {
      temp.add(String.valueOf(t.getId()));
    }
    // TODO
    return temp.toString();
  }
}
