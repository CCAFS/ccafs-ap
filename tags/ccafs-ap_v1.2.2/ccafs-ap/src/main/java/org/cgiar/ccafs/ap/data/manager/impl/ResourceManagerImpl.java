/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ResourceDAO;
import org.cgiar.ccafs.ap.data.manager.ResourceManager;
import org.cgiar.ccafs.ap.data.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
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

  @Override
  public boolean removeResources(int activityID) {
    return resourceDAO.removeResources(activityID);
  }

  @Override
  public boolean saveResources(List<Resource> resources, int activityID) {
    boolean saved = true;

    for (Resource resource : resources) {
      Map<String, String> resourceData = new HashMap<String, String>();
      if (resource.getId() == -1) {
        resourceData.put("id", null);
      } else {
        resourceData.put("id", String.valueOf(resource.getId()));
      }
      resourceData.put("name", resource.getName());
      resourceData.put("activity_id", String.valueOf(activityID));

      if (!resourceDAO.saveResource(resourceData)) {
        saved = false;
      }
    }
    return saved;
  }

}
