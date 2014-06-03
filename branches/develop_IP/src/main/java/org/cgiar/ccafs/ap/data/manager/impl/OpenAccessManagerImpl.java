package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OpenAccessDAO;
import org.cgiar.ccafs.ap.data.manager.OpenAccessManager;
import org.cgiar.ccafs.ap.data.model.OpenAccess;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OpenAccessManagerImpl implements OpenAccessManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OpenAccessManagerImpl.class);
  private OpenAccessDAO openAccessDAO;

  @Inject
  public OpenAccessManagerImpl(OpenAccessDAO openAccessDAO) {
    this.openAccessDAO = openAccessDAO;
  }

  @Override
  public OpenAccess getOpenAccess(String id) {
    Map<String, String> oaData = openAccessDAO.getOpenAccess(id);
    OpenAccess openAccess = new OpenAccess();
    openAccess.setId(Integer.parseInt(oaData.get("id")));
    openAccess.setName(oaData.get("name"));
    return openAccess;
  }

  @Override
  public OpenAccess[] getOpenAccessList() {
    List<Map<String, String>> openAccessDataList = openAccessDAO.getOpenAccessOptions();
    OpenAccess[] openAccessList = new OpenAccess[openAccessDataList.size()];
    for (int i = 0; i < openAccessDataList.size(); i++) {
      openAccessList[i] = new OpenAccess();
      openAccessList[i].setId(Integer.parseInt(openAccessDataList.get(i).get("id")));
      openAccessList[i].setName(openAccessDataList.get(i).get("name"));
    }
    return openAccessList;
  }

}
