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

import org.cgiar.ccafs.ap.data.dao.LiaisonInstitutionDAO;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class LiaisonInstitutionManagerImpl implements LiaisonInstitutionManager {

  private LiaisonInstitutionDAO liaisonInstitutionDAO;

  @Inject
  public LiaisonInstitutionManagerImpl(LiaisonInstitutionDAO liaisonInstitutionDAO) {
    this.liaisonInstitutionDAO = liaisonInstitutionDAO;
  }

  @Override
  public LiaisonInstitution getLiaisonInstitution(int liaisionInstitutionID) {
    LiaisonInstitution liaisonInstitution = new LiaisonInstitution();
    Map<String, String> liaisonInstitutionData = liaisonInstitutionDAO.getLiaisonInstitution(liaisionInstitutionID);

    liaisonInstitution.setId(Integer.parseInt(liaisonInstitutionData.get("id")));
    liaisonInstitution.setName(liaisonInstitutionData.get("name"));
    liaisonInstitution.setAcronym(liaisonInstitutionData.get("acronym"));
    liaisonInstitution.setIpProgram(liaisonInstitutionData.get("ip_program"));
    return liaisonInstitution;
  }

  @Override
  public LiaisonInstitution getLiaisonInstitutionByUser(int userID) {
    LiaisonInstitution liaisonInstitution = new LiaisonInstitution();
    Map<String, String> liaisonInstitutionData = liaisonInstitutionDAO.getLiaisonInstitutionByUser(userID);

    if (liaisonInstitutionData.isEmpty()) {
      return null;
    }

    liaisonInstitution.setId(Integer.parseInt(liaisonInstitutionData.get("id")));
    liaisonInstitution.setName(liaisonInstitutionData.get("name"));
    liaisonInstitution.setAcronym(liaisonInstitutionData.get("acronym"));
    return liaisonInstitution;
  }

  @Override
  public List<LiaisonInstitution> getLiaisonInstitutions() {
    List<LiaisonInstitution> liaisonInstitutions = new ArrayList<>();
    List<Map<String, String>> liaisonInsitutionsData = liaisonInstitutionDAO.getLiaisonInstitutions();

    for (Map<String, String> liData : liaisonInsitutionsData) {
      LiaisonInstitution liaisonInstitution = new LiaisonInstitution();
      liaisonInstitution.setId(Integer.parseInt(liData.get("id")));
      liaisonInstitution.setName(liData.get("name"));
      liaisonInstitution.setAcronym(liData.get("acronym"));

      liaisonInstitutions.add(liaisonInstitution);
    }

    return liaisonInstitutions;
  }

  @Override
  public List<LiaisonInstitution> getLiaisonInstitutionsCenter() {
    List<LiaisonInstitution> liaisonInstitutions = new ArrayList<>();
    List<Map<String, String>> liaisonInsitutionsData = liaisonInstitutionDAO.getLiaisonInstitutionsCenter();

    for (Map<String, String> liData : liaisonInsitutionsData) {
      LiaisonInstitution liaisonInstitution = new LiaisonInstitution();
      liaisonInstitution.setId(Integer.parseInt(liData.get("id")));
      liaisonInstitution.setName(liData.get("name"));
      liaisonInstitution.setAcronym(liData.get("acronym"));

      liaisonInstitutions.add(liaisonInstitution);
    }

    return liaisonInstitutions;
  }

  @Override
  public List<LiaisonInstitution> getLiaisonInstitutionsCrpsIndicator() {
    List<LiaisonInstitution> liaisonInstitutions = new ArrayList<>();
    List<Map<String, String>> liaisonInsitutionsData = liaisonInstitutionDAO.getLiaisonInstitutionsCrps();

    for (Map<String, String> liData : liaisonInsitutionsData) {
      LiaisonInstitution liaisonInstitution = new LiaisonInstitution();
      liaisonInstitution.setId(Integer.parseInt(liData.get("id")));
      liaisonInstitution.setName(liData.get("name"));
      liaisonInstitution.setAcronym(liData.get("acronym"));

      liaisonInstitutions.add(liaisonInstitution);
    }

    return liaisonInstitutions;
  }

  @Override
  public List<LiaisonInstitution> getLiaisonInstitutionSynthesis() {
    List<LiaisonInstitution> liaisonInstitutions = new ArrayList<>();
    List<Map<String, String>> liaisonInsitutionsData = liaisonInstitutionDAO.getLiaisonInstitutionsSynthesis();

    for (Map<String, String> liData : liaisonInsitutionsData) {
      LiaisonInstitution liaisonInstitution = new LiaisonInstitution();
      liaisonInstitution.setId(Integer.parseInt(liData.get("id")));
      liaisonInstitution.setName(liData.get("name"));
      liaisonInstitution.setAcronym(liData.get("acronym"));
      liaisonInstitution.setIpProgram(liData.get("ip_program"));

      liaisonInstitutions.add(liaisonInstitution);
    }

    return liaisonInstitutions;
  }

}
