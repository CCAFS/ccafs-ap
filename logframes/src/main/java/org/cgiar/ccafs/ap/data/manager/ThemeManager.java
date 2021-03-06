package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ThemeManagerImpl;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.Theme;

import com.google.inject.ImplementedBy;

@ImplementedBy(ThemeManagerImpl.class)
public interface ThemeManager {

  /**
   * Get the theme object corresponding to the given id
   * 
   * @param id - Theme identifier
   * @return Theme objects
   */
  public Theme getTheme(String id);

  /**
   * Get the theme list.
   * 
   * @return an array of Theme objects or null if no data found
   */
  public Theme[] getThemes();

  /**
   * Get all the themes that exist in a given logframe.
   * 
   * @param logframe - the logframe that belong the theme array.
   * @return an array of themes that belong to the given logframe
   */
  public Theme[] getThemes(Logframe logframe);

  /**
   * Get a list of themes object corresponding to the given array of ids
   * 
   * @param ids - Array of theme identifiers
   * @return a list of Theme objects
   */
  public Theme[] getThemes(String[] ids);

  /**
   * Get the themes on which partner works.
   * 
   * @param partner
   * @return an array of themes objects or null if there is no themes related.
   */
  public Theme[] getThemesByPartner(Partner partner);
}
