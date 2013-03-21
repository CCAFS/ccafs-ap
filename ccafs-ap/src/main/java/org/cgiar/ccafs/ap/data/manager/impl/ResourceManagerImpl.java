package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ResourceDAO;
import org.cgiar.ccafs.ap.data.manager.ResourceManager;
import org.cgiar.ccafs.ap.data.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceManagerImpl implements ResourceManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ResourceManagerImpl.class);
  private ResourceDAO resourceDAO;

  @Inject
  public ResourceManagerImpl(ResourceDAO resourceDAO) {
    this.resourceDAO = resourceDAO;
  }

  @Override
  public List<Resource> getResources(int activityID) {
    List<Map<String, String>> resourcesDataList = resourceDAO.getResources(activityID);
    List<Resource> resources = new ArrayList<>();
    for (int c = 0; c < resourcesDataList.size(); c++) {
      Resource temp = new Resource();
      temp.setId(Integer.parseInt(resourcesDataList.get(c).get("id")));
      temp.setName(resourcesDataList.get(c).get("name"));
      resources.add(temp);
    }
    return resources;
  }

}
