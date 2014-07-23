package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import com.google.inject.Inject;


public class IPIndicatorManagerImpl implements IPIndicatorManager {

  private IPIndicatorDAO indicatorDAO;

  @Inject
  public IPIndicatorManagerImpl(IPIndicatorDAO indicatorDAO) {
    this.indicatorDAO = indicatorDAO;
  }

  @Override
  public boolean removeElementIndicators(IPElement element, IPProgram program) {
    return indicatorDAO.removeIpElementIndicators(element.getId(), program.getId());
  }

}
