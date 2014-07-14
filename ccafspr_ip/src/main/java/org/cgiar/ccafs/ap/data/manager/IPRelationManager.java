package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPRelationManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPRelationManagerImpl.class)
public interface IPRelationManager {

  public boolean saveIpRelation(IPElement parentElement, IPElement childElement);
}
