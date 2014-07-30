package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPElementRelationManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPElementRelationManagerImpl.class)
public interface IPElementRelationManager {

  /**
   * This method removes the relations between the ipElements received
   * 
   * @param parentElement
   * @param childElement
   * @return true if the relation was succesfully removed. False otherwise.
   */
  public boolean deleteIPElementsRelation(IPElement parentElement, IPElement childElement);

  /**
   * This method removes from the database all records when the childElement
   * has the same value as received as parameter.
   * 
   * @param childElement - ip Element
   * @return true if the relations were removed. False otherwise.
   */
  public boolean deleteRelationsByChildElement(IPElement childElement);
}
