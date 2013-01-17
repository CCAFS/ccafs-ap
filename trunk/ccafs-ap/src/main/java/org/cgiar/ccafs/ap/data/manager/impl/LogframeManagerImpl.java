package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.LogframeDAO;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.Map;

import com.google.inject.Inject;


public class LogframeManagerImpl implements LogframeManager {

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
    return null;
  }

  @Override
  public Logframe getLogframeByYear(int year) {
    Map<String, String> logframeDB = logframeDAO.getLogframeByYear(year);
    return new Logframe(Integer.parseInt(logframeDB.get("id")), Integer.parseInt(logframeDB.get("year")),
      logframeDB.get("name"));
  }

}
