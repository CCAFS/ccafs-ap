package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class ThemesConverter extends StrutsTypeConverter {

  private ThemeManager themeManager;

  @Inject
  public ThemesConverter(ThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    Theme[] themes = themeManager.getThemes(values);
    return themes;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<Theme> themesArray = (List<Theme>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (Theme t : themesArray) {
      temp.add(String.valueOf(t.getId()));
    }
    // TODO
    return temp.toString();
  }
}
