package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.PublicationThemeManagerImpl;
import org.cgiar.ccafs.ap.data.model.PublicationTheme;

import com.google.inject.ImplementedBy;

@ImplementedBy(PublicationThemeManagerImpl.class)
public interface PublicationThemeManager {

  /**
   * This function gets all the publication themes available.
   * 
   * @return an array of PublicationTheme objects with the information.
   */
  public PublicationTheme[] getPublicationThemes();

  /**
   * This function gets all the publication themes identified by the ids given.
   * 
   * @return an array of PublicationTheme objects with the information.
   */
  public PublicationTheme[] getPublicationThemes(String[] ids);
}
