package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.KeywordManagerImpl;
import org.cgiar.ccafs.ap.data.model.Keyword;

import com.google.inject.ImplementedBy;

@ImplementedBy(KeywordManagerImpl.class)
public interface KeywordManager {

  /**
   * Get the keyword object corresponding with the given id
   * 
   * @param id - keyword identifier
   * @return Keyword object with the information
   */
  public Keyword getKeyword(String id);

  /**
   * Get the list of keywords from the database.
   * 
   * @return a list of Keyword objects with the information
   */
  public Keyword[] getKeywordList();
}
