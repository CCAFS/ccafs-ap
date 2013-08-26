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

  /**
   * Delete all the resources related to the activity given from the database.
   * 
   * @param activityID - Activity identifier
   * @return true if the records were successfully removed. False otherwise.
   */
  public boolean removeResources(int activityID);

  /**
   * Save the resources list into the database.
   * 
   * @param resources - The data to be saved.
   * @param activityID - activity identifier
   * @return true if the data was successfully saved. False otherwise
   */
  public boolean saveResources(List<Resource> resources, int activityID);
}
