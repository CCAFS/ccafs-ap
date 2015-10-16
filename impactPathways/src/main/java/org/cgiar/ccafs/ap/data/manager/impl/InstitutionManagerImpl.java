/**
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
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 * @author Javier Andrés Gallego B.
 */
public class InstitutionManagerImpl implements InstitutionManager {

  // LOG
  public static Logger LOG = LoggerFactory.getLogger(InstitutionManagerImpl.class);

  private InstitutionDAO institutionDAO;

  @Inject
  public InstitutionManagerImpl(InstitutionDAO institutionDAO) {
    this.institutionDAO = institutionDAO;
  }

  @Override
  public boolean deleteProjectPartnerContributeInstitution(int projectPartnerID, int institutionID) {
    return institutionDAO.deleteProjectPartnerContributeInstitution(projectPartnerID, institutionID);
  }


  @Override
  public boolean deleteProjectPartnerContributeInstitution(ProjectPartner projectPartner, Institution institution) {
    return institutionDAO.deleteProjectPartnerContributeInstitution(projectPartner.getId(), institution.getId());
  }

  @Override
  public List<Institution> getAllInstitutions() {
    List<Institution> institutions = new ArrayList<>();
    List<Map<String, String>> institutionDataList = institutionDAO.getAllInstitutions();
    for (Map<String, String> iData : institutionDataList) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setPPA(Boolean.parseBoolean(iData.get("is_ppa")));
      institution.setWebsiteLink(iData.get("website_link"));

      // InstitutionType Object
      InstitutionType type = new InstitutionType();
      if (iData.get("institution_type_id") != null) {
        type.setId(Integer.parseInt(iData.get("institution_type_id")));
        type.setName(iData.get("institution_type_name"));
        type.setAcronym(iData.get("institution_type_acronym"));
        institution.setType(type);
      }
      // Program Object
      IPProgram program = new IPProgram();
      if (iData.get("program_id") != null) {
        program.setId(Integer.parseInt(iData.get("program_id")));
        program.setName(iData.get("program_name"));
        program.setAcronym(iData.get("program_acronym"));
        institution.setProgram(program);
      }
      // Location Object
      Country country = new Country();
      if (iData.get("loc_elements_id") != null) {
        country.setId(Integer.parseInt(iData.get("loc_elements_id")));
        country.setName(iData.get("loc_elements_name"));
        country.setCode(iData.get("loc_elements_code"));
        institution.setCountry(country);
      }

      // Adding object to the array.
      institutions.add(institution);
    }
    return institutions;
  }

  @Override
  public List<InstitutionType> getAllInstitutionTypes() {
    List<InstitutionType> institutiontypes = new ArrayList<>();
    List<Map<String, String>> institutiontypeDataList = institutionDAO.getAllInstitutionTypes();
    for (Map<String, String> iData : institutiontypeDataList) {
      InstitutionType institution = new InstitutionType();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      // Adding object to the array.
      institutiontypes.add(institution);
    }
    return institutiontypes;
  }


  @Override
  public List<Institution> getAllPPAInstitutions() {
    List<Institution> institutions = new ArrayList<>();
    List<Map<String, String>> institutionDataList = institutionDAO.getAllPPAInstitutions();
    for (Map<String, String> iData : institutionDataList) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setPPA(Boolean.parseBoolean(iData.get("is_ppa")));
      institution.setWebsiteLink(iData.get("website_link"));

      // InstitutionType Object
      InstitutionType type = new InstitutionType();
      if (iData.get("institution_type_id") != null) {
        type.setId(Integer.parseInt(iData.get("institution_type_id")));
        type.setName(iData.get("institution_type_name"));
        type.setAcronym(iData.get("institution_type_acronym"));
        institution.setType(type);
      }
      // Program Object
      IPProgram program = new IPProgram();
      if (iData.get("program_id") != null) {
        program.setId(Integer.parseInt(iData.get("program_id")));
        program.setName(iData.get("program_name"));
        program.setAcronym(iData.get("program_acronym"));
        institution.setProgram(program);
      }
      // Location Object
      Country country = new Country();
      if (iData.get("loc_elements_id") != null) {
        country.setId(Integer.parseInt(iData.get("loc_elements_id")));
        country.setName(iData.get("loc_elements_name"));
        country.setCode(iData.get("loc_elements_code"));
        institution.setCountry(country);
      }

      // Adding object to the array.
      institutions.add(institution);
    }
    return institutions;
  }

  @Override
  public Institution getInstitution(int institutionId) {
    Map<String, String> iData = institutionDAO.getInstitution(institutionId);
    if (!iData.isEmpty()) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setPPA(Boolean.parseBoolean(iData.get("is_ppa")));
      institution.setWebsiteLink(iData.get("website_link"));

      // InstitutionType Object
      InstitutionType type = new InstitutionType();
      if (iData.get("institution_type_id") != null) {
        type.setId(Integer.parseInt(iData.get("institution_type_id")));
        type.setName(iData.get("institution_type_name"));
        type.setAcronym(iData.get("institution_type_acronym"));
        institution.setType(type);
      }
      // Program Object
      IPProgram program = new IPProgram();
      if (iData.get("program_id") != null) {
        program.setId(Integer.parseInt(iData.get("program_id")));
        program.setName(iData.get("program_name"));
        program.setAcronym(iData.get("program_acronym"));
        institution.setProgram(program);
      }
      // Location Object
      Country country = new Country();
      if (iData.get("loc_elements_id") != null) {
        country.setId(Integer.parseInt(iData.get("loc_elements_id")));
        country.setName(iData.get("loc_elements_name"));
        country.setCode(iData.get("loc_elements_code"));
        institution.setCountry(country);
      }
      return institution;
    }
    return null;
  }


  private Institution getInstitutionData(Map<String, String> iData) {
    Institution institution = new Institution();
    institution.setId(Integer.parseInt(iData.get("id")));
    institution.setName(iData.get("name"));
    institution.setAcronym(iData.get("acronym"));
    institution.setPPA(Boolean.parseBoolean(iData.get("is_ppa")));
    institution.setWebsiteLink(iData.get("website_link"));

    // InstitutionType Object
    InstitutionType type = new InstitutionType();
    if (iData.get("institution_type_id") != null) {
      type.setId(Integer.parseInt(iData.get("institution_type_id")));
      type.setName(iData.get("institution_type_name"));
      type.setAcronym(iData.get("institution_type_acronym"));
      institution.setType(type);
    }

    // Location Object
    Country country = new Country();
    if (iData.get("loc_elements_id") != null) {
      country.setId(Integer.parseInt(iData.get("loc_elements_id")));
      country.setName(iData.get("loc_elements_name"));
      country.setCode(iData.get("loc_elements_code"));
      institution.setCountry(country);
    }
    return institution;
  }


  @Override
  public List<Institution> getInstitutionsByTypeAndCountry(InstitutionType type, Country country) {
    List<Institution> institutions = new ArrayList<>();
    List<Map<String, String>> institutionDataList =
      institutionDAO.getInstitutionsByTypeAndCountry(type.getId(), country.getId());
    for (Map<String, String> iData : institutionDataList) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setPPA(Boolean.parseBoolean(iData.get("is_ppa")));
      institution.setWebsiteLink(iData.get("website_link"));

      // InstitutionType Object
      InstitutionType _type = new InstitutionType();
      if (iData.get("institution_type_id") != null) {
        _type.setId(Integer.parseInt(iData.get("institution_type_id")));
        _type.setName(iData.get("institution_type_name"));
        _type.setAcronym(iData.get("institution_type_acronym"));
        institution.setType(_type);
      }
      // Program Object
      IPProgram program = new IPProgram();
      if (iData.get("program_id") != null) {
        program.setId(Integer.parseInt(iData.get("program_id")));
        program.setName(iData.get("program_name"));
        program.setAcronym(iData.get("program_acronym"));
        institution.setProgram(program);
      }
      // Location Object
      Country _country = new Country();
      if (iData.get("loc_elements_id") != null) {
        _country.setId(Integer.parseInt(iData.get("loc_elements_id")));
        _country.setName(iData.get("loc_elements_name"));
        _country.setCode(iData.get("loc_elements_code"));
        institution.setCountry(_country);
      }

      // Adding object to the array.
      institutions.add(institution);
    }
    return institutions;
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
      institution.setWebsiteLink(iData.get("website_link"));

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
  public InstitutionType getInstitutionType(int institutionTypeId) {
    Map<String, String> iData = institutionDAO.getInstitutionType(institutionTypeId);
    if (!iData.isEmpty()) {
      InstitutionType institutionType = new InstitutionType();
      institutionType.setId(Integer.parseInt(iData.get("id")));
      institutionType.setName(iData.get("name"));
      institutionType.setAcronym(iData.get("acronym"));
      // Institution Type Object
      return institutionType;

    }
    return null;
  }

  @Override
  public List<Map<String, Object>> getProjectLeadingInstitutions() {
    return institutionDAO.getProjectLeadingInstitutions();
  }

  @Override
  public List<Map<String, Object>> getProjectPartnerInstitutions() {
    return institutionDAO.getProjectPartnerInstitutions();
  }

  @Override
  public Institution getUserMainInstitution(User user) {
    Map<String, String> iData = institutionDAO.getUserMainInstitution(user.getId());
    if (!iData.isEmpty()) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setWebsiteLink(iData.get("website_link"));

      // Program Object
      if (iData.get("program_id") != null) {
        IPProgram program = new IPProgram();
        program.setId(Integer.parseInt(iData.get("program_id")));
        program.setName(iData.get("program_name"));
        institution.setProgram(program);
      }

      // Institution type object
      InstitutionType it = new InstitutionType();
      it.setId(Integer.parseInt(iData.get("institution_type_id")));
      it.setAcronym(iData.get("institution_type_acronym"));
      it.setName(iData.get("institution_type_name"));
      institution.setType(it);

      return institution;
    }
    return null;
  }

  @Override
  public int saveProjectPartnerContributeInstitution(int projectPartnerID, int institutionID) {
    Map<String, Object> contributionData = new HashMap<>();

    // Project partners must have an institution associated.
    if (institutionID == -1 || projectPartnerID == -1) {
      return -1;
    }

    contributionData.put("project_partner_id", projectPartnerID);
    contributionData.put("contribution_institution_id", institutionID);

    int result = institutionDAO.saveProjectPartnerContributeInstitution(contributionData);
    if (result == 0) {
      LOG
        .debug(
          "saveProjectPartnerContributeInstitution > New Project Partner Contribution added with projectPartnerID={}, institutionID={} ",
          projectPartnerID, institutionID);
    } else {
      LOG
        .error(
          "saveProjectPartnerContributeInstitution > There was an error trying to save/update a project partner contribution from projectPartnerID={} and institutionID={}",
          projectPartnerID, institutionID);
    }

    return result;
  }

  @Override
  public boolean
    saveProjectPartnerContributeInstitutions(int projectPartnerID, List<Institution> contributeInstitutions) {
    boolean allSaved = true;
    int result;
    for (Institution institution : contributeInstitutions) {
      result = this.saveProjectPartnerContributeInstitution(projectPartnerID, institution.getId());
      if (result == -1) {
        allSaved = false;
      }
    }
    return allSaved;
  }

  @Override
  public boolean validateLastOneInstitution(int projectPartnerID) {
    return institutionDAO.validateLastOneInstitution(projectPartnerID);
  }

}
