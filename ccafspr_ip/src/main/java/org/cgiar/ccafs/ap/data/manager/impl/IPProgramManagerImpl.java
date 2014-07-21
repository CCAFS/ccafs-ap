package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPProgramDAO;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.IPProgramTypes;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class IPProgramManagerImpl implements IPProgramManager {

  // DAOs
  private IPProgramDAO ipProgramDAO;

  @Inject
  public IPProgramManagerImpl(IPProgramDAO ipProgramDAO) {
    this.ipProgramDAO = ipProgramDAO;
  }


  @Override
  public List<IPProgram> getProgramsTypeFlagship() {
    List<IPProgram> programsTypeFlagship = new ArrayList<>();
    List<Map<String, String>> programTypeFlagshipDataList = ipProgramDAO.getProgramsTypeFlagship();
    for (Map<String, String> iData : programTypeFlagshipDataList) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(iData.get("id")));
      ipProgram.setName(iData.get("name"));
      ipProgram.setAcronym(iData.get("acronym"));

      IPProgramTypes programType = new IPProgramTypes();
      programType.setTypeId(Integer.parseInt(iData.get("type_id")));

      Region region = new Region();
      region.setId(Integer.parseInt(iData.get("region_id")));

      programsTypeFlagship.add(ipProgram);
    }
    return programsTypeFlagship;
  }

  @Override
  public List<IPProgram> getProgramsTypeRegion() {
    List<IPProgram> programsTypeRegion = new ArrayList<>();
    List<Map<String, String>> programTypeRegionDataList = ipProgramDAO.getProgramsTypeRegion();
    for (Map<String, String> iData : programTypeRegionDataList) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(iData.get("id")));
      ipProgram.setName(iData.get("name"));
      ipProgram.setAcronym(iData.get("acronym"));

      IPProgramTypes programType = new IPProgramTypes();
      programType.setTypeId(Integer.parseInt(iData.get("type_id")));

      Region region = new Region();
      region.setId(Integer.parseInt(iData.get("region_id")));

      programsTypeRegion.add(ipProgram);
    }
    return programsTypeRegion;
  }

}
