/*****************************************************************
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
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPProgramDAO;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.IPProgramType;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

/**
 * @author Hernán David Carvajal
 * @author Héctor Fabio Tobón R.
 */
public class IPProgramManagerImpl implements IPProgramManager {

  // DAOs
  private IPProgramDAO ipProgramDAO;

  @Inject
  public IPProgramManagerImpl(IPProgramDAO ipProgramDAO) {
    this.ipProgramDAO = ipProgramDAO;
  }

  @Override
  public boolean deleteProjectFocus(int projectId, int ipProgramID) {
    return ipProgramDAO.deleteProjectFocus(projectId, ipProgramID);
  }

  @Override
  public IPProgram getIPProgramById(int ipProgramID) {
    Map<String, String> ipProgramData = ipProgramDAO.getIPProgramById(ipProgramID);
    if (!ipProgramData.isEmpty()) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(ipProgramData.get("id")));
      ipProgram.setName(ipProgramData.get("name"));
      ipProgram.setAcronym(ipProgramData.get("acronym"));

      // Program Type Object
      IPProgramType programType = new IPProgramType();
      programType.setId(Integer.parseInt(ipProgramData.get("type_id")));
      programType.setName(ipProgramData.get("type_name"));
      ipProgram.setType(programType);

      // Region Object
      Region region = new Region();
      if (ipProgramData.get("region_id") != null) {
        region.setId(Integer.parseInt(ipProgramData.get("region_id")));
        ipProgram.setRegion(region);
      }

      return ipProgram;
    }
    return null;
  }


  @Override
  public IPProgram getIPProgramByProjectId(int projectID) {
    Map<String, String> ipProgramData = ipProgramDAO.getIPProgramByProjectId(projectID);
    if (!ipProgramData.isEmpty()) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(ipProgramData.get("id")));
      ipProgram.setName(ipProgramData.get("name"));
      ipProgram.setAcronym(ipProgramData.get("acronym"));

      // Program Type Object
      IPProgramType programType = new IPProgramType();
      programType.setId(Integer.parseInt(ipProgramData.get("type_id")));
      programType.setName(ipProgramData.get("type_name"));
      ipProgram.setType(programType);

      // Region Object
      Region region = new Region();
      if (ipProgramData.get("region_id") != null) {
        region.setId(Integer.parseInt(ipProgramData.get("region_id")));
        ipProgram.setRegion(region);
      }

      return ipProgram;
    }
    return null;
  }

  @Override
  public List<IPProgram> getProgramsByType(int ipProgramTypeID) {
    List<IPProgram> programs = new ArrayList<>();
    List<Map<String, String>> programsDataList = ipProgramDAO.getProgramsByType(ipProgramTypeID);

    for (Map<String, String> iData : programsDataList) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(iData.get("id")));
      ipProgram.setName(iData.get("name"));
      ipProgram.setAcronym(iData.get("acronym"));

      // Program Type Object
      IPProgramType ipProgramType = new IPProgramType();
      ipProgramType.setId(Integer.parseInt(iData.get("type_id")));
      ipProgramType.setName(iData.get("type_name"));
      ipProgram.setType(ipProgramType);

      // Region Object
      Region region = new Region();
      if (iData.get("region_id") != null) {
        region.setId(Integer.parseInt(iData.get("region_id")));
        ipProgram.setRegion(region);
      }

      programs.add(ipProgram);
    }
    return programs;
  }


  @Override
  public List<IPProgram> getProjectFocuses(int projectID, int typeID) {
    List<Map<String, String>> projectFocusesDataList = ipProgramDAO.getProjectFocuses(projectID, typeID);
    List<IPProgram> projectFocusesList = new ArrayList<>();

    for (Map<String, String> projectFocusesData : projectFocusesDataList) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(projectFocusesData.get("program_id")));
      ipProgram.setName(projectFocusesData.get("program_name"));
      ipProgram.setAcronym(projectFocusesData.get("program_acronym"));
      ipProgram.setCreated(Long.parseLong(projectFocusesData.get("active_since")));
      // Region
      if (projectFocusesData.get("region_id") != null) {
        Region region = new Region();
        region.setId(Integer.parseInt(projectFocusesData.get("region_id")));
        region.setName(projectFocusesData.get("region_name"));
        region.setCode(projectFocusesData.get("region_code"));
        ipProgram.setRegion(region);
      }

      ipProgram.setType(new IPProgramType(typeID));

      projectFocusesList.add(ipProgram);
    }
    return projectFocusesList;
  }

  @Override
  public boolean saveProjectFocus(int projectID, int programID, User user, String justification) {
    Map<String, Object> elementData = new HashMap<>();
    elementData.put("project_id", projectID);
    elementData.put("program_id", programID);
    elementData.put("user_id", user.getId());
    elementData.put("justification", justification);
    return ipProgramDAO.saveProjectFocuses(elementData);
  }

}
