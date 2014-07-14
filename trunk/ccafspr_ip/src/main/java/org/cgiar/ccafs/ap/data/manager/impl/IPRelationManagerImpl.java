package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPRelationshipDAO;
import org.cgiar.ccafs.ap.data.manager.IPRelationManager;
import org.cgiar.ccafs.ap.data.model.IPElement;

import com.google.inject.Inject;


public class IPRelationManagerImpl implements IPRelationManager {

  private IPRelationshipDAO ipRelationshipDAO;

  @Inject
  public IPRelationManagerImpl(IPRelationshipDAO ipRelationshipDAO) {
    this.ipRelationshipDAO = ipRelationshipDAO;
  }

  @Override
  public boolean saveIpRelation(IPElement parentElement, IPElement childElement) {
    return ipRelationshipDAO.saveIPRelation(parentElement.getId(), childElement.getId());
  }

}
