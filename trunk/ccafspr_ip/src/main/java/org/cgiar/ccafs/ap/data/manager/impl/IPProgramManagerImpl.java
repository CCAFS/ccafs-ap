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
  public List<IPProgram> getProgramsByType(int ipProgramTypeID) {
    List<IPProgram> programs = new ArrayList<>();
    List<Map<String, String>> programsDataList = ipProgramDAO.getProgramsByType(ipProgramTypeID);

    for (Map<String, String> iData : programsDataList) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(iData.get("id")));
      ipProgram.setName(iData.get("name"));
      ipProgram.setAcronym(iData.get("acronym"));

      // Program Type Object
      IPProgramTypes programType = new IPProgramTypes();
      programType.setTypeId(Integer.parseInt(iData.get("type_id")));

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


}
