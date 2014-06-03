package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OpenAccessManagerImpl;
import org.cgiar.ccafs.ap.data.model.OpenAccess;

import com.google.inject.ImplementedBy;

@ImplementedBy(OpenAccessManagerImpl.class)
public interface OpenAccessManager {

  /**
   * Return the information related to the open access identified by id
   * 
   * @param id - the open access identifier
   * @return an openAccess object
   */
  public OpenAccess getOpenAccess(String id);

  /**
   * Get all the open access option
   * 
   * @return an array of openAccess objects
   */
  public OpenAccess[] getOpenAccessList();
}
