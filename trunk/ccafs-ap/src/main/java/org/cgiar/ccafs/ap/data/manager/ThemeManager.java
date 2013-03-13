package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ThemeManagerImpl;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Theme;

import com.google.inject.ImplementedBy;

@ImplementedBy(ThemeManagerImpl.class)
public interface ThemeManager {

  /**
   * Get all the themes that exist in a given logframe.
   * 
   * @param logframe - the logframe that belong the theme array.
   * @return an array of themes that belong to the given logframe
   */
  public Theme[] getThemes(Logframe logframe);
}
