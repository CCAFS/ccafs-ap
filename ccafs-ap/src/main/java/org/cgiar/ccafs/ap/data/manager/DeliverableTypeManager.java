package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableTypeManagerImpl.class)
public interface DeliverableTypeManager {


  /**
   * Get all the deliverables sub types
   * 
   * @return a List whit all the deliverable sub types.
   */
  public DeliverableType[] getDeliverableSubTypes();

  /**
   * Get the deliverable type identified with the given id.
   * 
   * @param id - Identifier.
   * @return a DeliverableType object or null if nothing was found.
   */
  public DeliverableType getDeliverableType(String id);

  /**
   * Get the deliverable type parent (if exists) of the deliverable type
   * parent received as parameter.
   * 
   * @param deliverableSubTypeID - Deliverable subType identifier
   * @return the deliverable type parent if exists. Null otherwise.
   */
  public DeliverableType getDeliverableTypeBySubType(int deliverableSubTypeID);

  /**
   * Get all the deliverables types (only the categories of the types)
   * 
   * @return a List whit all the deliverable types.
   */
  public DeliverableType[] getDeliverableTypes();

  /**
   * Get all the deliverables types and subtypes
   * 
   * @return a List whit all the deliverable types and subtypes.
   */
  public DeliverableType[] getDeliverableTypesAndSubtypes();
}
