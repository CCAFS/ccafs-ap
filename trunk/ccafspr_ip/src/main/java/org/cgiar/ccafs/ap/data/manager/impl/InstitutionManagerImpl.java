/*
 * ****************************************************************
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
 * ****************************************************************
 */
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.InstitutionDAO;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

/**
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 * @author Javier Andres Gallego B.
 */
public class InstitutionManagerImpl implements InstitutionManager {


  private InstitutionDAO institutionDAO;

  @Inject
  public InstitutionManagerImpl(InstitutionDAO institutionDAO) {
    this.institutionDAO = institutionDAO;
  }


  public List<Institution> getAllInstitutions() {
    List<Institution> institutions = new ArrayList<>();
    List<Map<String, String>> institutionDataList = institutionDAO.getAllInstitutions();
    for (Map<String, String> iData : institutionDataList) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setContactPersonName(iData.get("contactPersonName"));
      institution.setContactPersonEmail(iData.get("contactPersonEmail"));

      // Institution Type - just the Id.
      InstitutionType type = new InstitutionType();
      type.setId(Integer.parseInt(iData.get("institution_type_id")));
      institution.setType(type);

      // IPProgram - just the Id.
      IPProgram program = new IPProgram();
      if (iData.get("program_id") != null) {
        program.setId(Integer.parseInt(iData.get("program_id")));
        institution.setProgram(program);
      }
      // Adding object to the array.
      institutions.add(institution);
    }
    return institutions;
  }

  // TODO - Pending to be implemented.
  @Override
  public Institution getInstitution(int institutionId) {
    Map<String, String> iData = institutionDAO.getInstitution(institutionId);
    if (!iData.isEmpty()) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setContactPersonName(iData.get("contactPersonName"));
      institution.setContactPersonEmail(iData.get("contactPersonEmail"));

      // TODO ask if in this method is necessary to call the program name and the institution type name
      // InstitutionType Object
      InstitutionType type = new InstitutionType();
      if (iData.get("institution_type_id") != null) {
        type.setId(Integer.parseInt(iData.get("institution_type_id")));
        institution.setType(type);
      }
      // Program Object
      IPProgram program = new IPProgram();
      if (iData.get("program_id") != null) {
        program.setId(Integer.parseInt(iData.get("program_id")));
        institution.setProgram(program);
      }


      return institution;
    }
    return null;
  }

  @Override
  public List<Institution> getInstitutionsByUser(User user) {
    List<Institution> institutions = new ArrayList<>();
    List<Map<String, String>> institutionDataList = institutionDAO.getInstitutionsByUser(user.getId());
    for (Map<String, String> iData : institutionDataList) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));

      // Program Object
      IPProgram program = new IPProgram();
      if (iData.get("program_id") != null) {
        program.setId(Integer.parseInt(iData.get("program_id")));
        program.setName(iData.get("program_name"));
        program.setAcronym(iData.get("program_acronym"));
        institution.setProgram(program);
      }

      // Institution type object
      InstitutionType it = new InstitutionType();
      it.setId(Integer.parseInt(iData.get("institution_type_id")));
      it.setAcronym(iData.get("institution_type_acronym"));
      institution.setType(it);

      institutions.add(institution);
    }
    return institutions;
  }

  @Override
  public Institution getUserMainInstitution(User user) {
    Map<String, String> iData = institutionDAO.getUserMainInstitution(user.getId());
    if (!iData.isEmpty()) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));

      // Program Object
      IPProgram program = new IPProgram();
      program.setId(Integer.parseInt(iData.get("program_id")));
      program.setName(iData.get("program_name"));
      program.setAcronym(iData.get("program_acronym"));
      institution.setProgram(program);

      // Institution type object
      InstitutionType it = new InstitutionType();
      it.setId(Integer.parseInt(iData.get("institution_type_id")));
      it.setAcronym(iData.get("institution_type_acronym"));
      institution.setType(it);

      return institution;
    }
    return null;
  }

}
