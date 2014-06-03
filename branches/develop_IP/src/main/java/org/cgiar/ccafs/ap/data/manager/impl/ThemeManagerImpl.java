package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ThemeDAO;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThemeManagerImpl implements ThemeManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ThemeManagerImpl.class);
  private ThemeDAO themeDAO;

  @Inject
  public ThemeManagerImpl(ThemeDAO themeDAO) {
    this.themeDAO = themeDAO;
  }

  @Override
  public Theme getTheme(String id) {
    for (Theme theme : getThemes()) {
      if (String.valueOf(theme.getId()).equals(id)) {
        return theme;
      }
    }
    return null;
  }

  @Override
  public Theme[] getThemes() {
    List<Map<String, String>> themesDB = themeDAO.getThemes();
    if (themesDB.size() > 0) {
      Theme[] themes = new Theme[themesDB.size()];
      for (int c = 0; c < themesDB.size(); c++) {
        themes[c] = new Theme();
        themes[c].setId(Integer.parseInt(themesDB.get(c).get("id")));
        themes[c].setCode(themesDB.get(c).get("code"));
        themes[c].setDescription(themesDB.get(c).get("description"));
      }
      return themes;
    }
    LOG.warn("Theme list loaded is empty");
    return null;
  }

  @Override
  public Theme[] getThemes(Logframe logframe) {
    List<Map<String, String>> themesDB = themeDAO.getThemes(logframe.getId());
    if (themesDB.size() > 0) {
      Theme[] themes = new Theme[themesDB.size()];
      for (int c = 0; c < themesDB.size(); c++) {
        themes[c] = new Theme();
        themes[c].setId(Integer.parseInt(themesDB.get(c).get("id")));
        themes[c].setCode(themesDB.get(c).get("code"));
        themes[c].setDescription(themesDB.get(c).get("description"));
        themes[c].setLogframe(logframe);
      }
      return themes;
    }
    LOG.warn("Theme list for logframe {} loaded is empty", logframe.getId());
    return null;
  }

  @Override
  public Theme[] getThemes(String[] ids) {
    List<Theme> themes = new ArrayList<>();
    for (Theme theme : getThemes()) {
      for (String id : ids) {
        if (String.valueOf(theme.getId()).equals(id)) {
          themes.add(theme);
        }
      }
    }
    return themes.toArray(new Theme[themes.size()]);
  }

  @Override
  public Theme[] getThemesByPartner(Partner partner) {
    List<Map<String, String>> themesDB = themeDAO.getThemesByPartner(partner.getId());
    if (themesDB.size() > 0) {
      Theme[] themes = new Theme[themesDB.size()];
      for (int c = 0; c < themesDB.size(); c++) {
        themes[c] = new Theme();
        themes[c].setId(Integer.parseInt(themesDB.get(c).get("id")));
        themes[c].setCode(themesDB.get(c).get("code"));
        themes[c].setDescription(themesDB.get(c).get("description"));
      }
      return themes;
    }
    return null;
  }

}