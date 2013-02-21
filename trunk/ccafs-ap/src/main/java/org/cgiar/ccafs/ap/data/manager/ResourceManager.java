package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ResourceManagerImpl;
import org.cgiar.ccafs.ap.data.model.Resource;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ResourceManagerImpl.class)
public interface ResourceManager {

  /**
   * Get the resources information related to the activity given
   * 
   * @param activityID - activity identifier
   * @return a list of Resources objects with the information
   */
  public List<Resource> getResources(int activityID);
}
