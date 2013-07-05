package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.PublicationManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Publication;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(PublicationManagerImpl.class)
public interface PublicationManager {

  /**
   * Get a list of publications that belong to a specific leader in a certain logframe.
   * 
   * @param leader - Leader that owns the publication list.
   * @param logframe - Logframe in which the publications were added.
   * @return An Array of Publication objects.
   */
  public List<Publication> getPublications(Leader leader, Logframe logframe);

  /**
   * Remove all the publications that belong to a specific leader in a certain logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object
   * @return true if the remove was successfully made, false if any problem occur.
   */
  public boolean removeAllPublications(Leader leader, Logframe logframe);

  /**
   * Save a list of publications into the database.
   * 
   * @param publications - List of Publication objects.
   * @param logframe - Logframe object
   * @param leaer - Leader object.
   * @return true if all the publications were successfully saved, or false if any problem occur.
   */
  public boolean savePublications(List<Publication> publications, Logframe logframe, Leader leaer);

}
