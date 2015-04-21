package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.LogframeDAO;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogframeManagerImpl implements LogframeManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(LogframeManagerImpl.class);
  private LogframeDAO logframeDAO;

  @Inject
  public LogframeManagerImpl(LogframeDAO logframeDAO) {
    this.logframeDAO = logframeDAO;
  }

  @Override
  public Logframe getLogframe(int id) {
    Map<String, String> logframeDB = logframeDAO.getLogframe(id);
    if (logframeDB != null) {
      Logframe logframe = new Logframe();
      logframe.setId(Integer.parseInt(logframeDB.get("id")));
      logframe.setYear(Integer.parseInt(logframeDB.get("year")));
      return logframe;
    }
    LOG.warn("Logframe {} wasn't found.", id);
    return null;
  }

  @Override
  public Logframe getLogframeByYear(int year) {
    Map<String, String> logframeDB = logframeDAO.getLogframeByYear(year);
    Logframe logframe = new Logframe();

    if (logframeDB.size() > 0) {
      logframe.setId(Integer.parseInt(logframeDB.get("id")));
      logframe.setYear(Integer.parseInt(logframeDB.get("year")));
      logframe.setName(logframeDB.get("name"));
      return logframe;
    }

    return null;
  }

}
