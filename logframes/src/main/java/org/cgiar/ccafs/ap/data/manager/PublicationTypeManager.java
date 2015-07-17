package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.PublicationTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.PublicationType;

import com.google.inject.ImplementedBy;

@ImplementedBy(PublicationTypeManagerImpl.class)
public interface PublicationTypeManager {

  /**
   * Get a publication identified with the given id.
   * 
   * @param id - identifier.
   * @return a PublicationType object or null if nothing was found.
   */
  public PublicationType getPublicationType(String id);

  /**
   * Get all the publications types as an array.
   * 
   * @return an Array of PublicationType objects.
   */
  public PublicationType[] getPublicationTypes();
}
