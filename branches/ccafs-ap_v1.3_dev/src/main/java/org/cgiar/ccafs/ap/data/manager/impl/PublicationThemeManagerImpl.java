package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.PublicationThemeDAO;
import org.cgiar.ccafs.ap.data.manager.PublicationThemeManager;
import org.cgiar.ccafs.ap.data.model.PublicationTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class PublicationThemeManagerImpl implements PublicationThemeManager {

  private PublicationThemeDAO publicationThemeDAO;

  @Inject
  public PublicationThemeManagerImpl(PublicationThemeDAO publicationThemeeDAO) {
    this.publicationThemeDAO = publicationThemeeDAO;
  }

  @Override
  public PublicationTheme[] getPublicationThemes() {
    PublicationTheme[] publicationThemes;
    List<Map<String, String>> publicationThemeDataList = publicationThemeDAO.getPublicationThemes();

    publicationThemes = new PublicationTheme[publicationThemeDataList.size()];
    for (int c = 0; c < publicationThemeDataList.size(); c++) {
      PublicationTheme pubTheme = new PublicationTheme();
      pubTheme.setId(Integer.parseInt(publicationThemeDataList.get(c).get("id")));
      pubTheme.setCode(publicationThemeDataList.get(c).get("code"));
      pubTheme.setName(publicationThemeDataList.get(c).get("name"));

      publicationThemes[c] = pubTheme;
    }

    return publicationThemes;
  }

  @Override
  public PublicationTheme[] getPublicationThemes(String[] ids) {
    List<PublicationTheme> pubThemes = new ArrayList<>();
    for (PublicationTheme pubTheme : getPublicationThemes()) {
      for (String id : ids) {
        if (id.equals(String.valueOf(pubTheme.getId()))) {
          pubThemes.add(pubTheme);
        }
      }
    }

    return pubThemes.toArray(new PublicationTheme[0]);
  }
}
