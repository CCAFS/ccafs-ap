package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class ThemeConverter extends StrutsTypeConverter {

  private ThemeManager themeManager;

  @Inject
  public ThemeConverter(ThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Theme.class) {
      String id = values[0];
      return themeManager.getTheme(id);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Theme theme = (Theme) o;
    return theme.getId() + "";
  }
}
