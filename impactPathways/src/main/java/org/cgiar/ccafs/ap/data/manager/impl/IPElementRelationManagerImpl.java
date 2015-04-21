package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPElementRelationDAO;
import org.cgiar.ccafs.ap.data.manager.IPElementRelationManager;
import org.cgiar.ccafs.ap.data.model.IPElement;

import com.google.inject.Inject;


public class IPElementRelationManagerImpl implements IPElementRelationManager {

  private IPElementRelationDAO relationDAO;

  @Inject
  public IPElementRelationManagerImpl(IPElementRelationDAO relationDAO) {
    this.relationDAO = relationDAO;
  }

  @Override
  public boolean deleteIPElementsRelation(IPElement parentElement, IPElement childElement) {
    return relationDAO.deleteIpElementsRelation(parentElement.getId(), childElement.getId());
  }

  @Override
  public boolean deleteRelationsByChildElement(IPElement childElement) {
    return relationDAO.deleteRelationsByChildElement(childElement.getId());
  }

}
