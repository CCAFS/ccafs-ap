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
package org.cgiar.ccafs.ap.action.preplanning;

import java.util.ArrayList;
import java.util.List;

import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.action.BaseAction;
import com.google.inject.Inject;
import org.cgiar.ccafs.ap.config.APConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage the Project Partners section in the pre-planning step.
 *
 * @author Héctor Tobón
 */
public class ProjectPartnersAction extends BaseAction {

  private static final long serialVersionUID = -2678924292464949934L;

  public static Logger LOG = LoggerFactory.getLogger(ProjectPartnersAction.class);

  // TODO - Create Managers and assign them to the constructor below.

  // Model
  private int projectId;
  private List<Institution> partners;
  private Institution leader;
  // allPartners will be used to list all the partners that have the system.
  private List<Institution> allPartners;

  @Inject
  public ProjectPartnersAction(APConfig config) {
    super(config);
    // TODO Managers.
  }

  public List<Institution> getPartners() {
    return partners;
  }

  public int getProjectId() {
    return projectId;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // partners = projectPartnerManager.getPartners(projectId);

    // Creating fake Project Partners.
    partners = new ArrayList<Institution>();
    Institution inst1 = new Institution();
    inst1.setId(124);
    inst1.setName("El partner uno");
    inst1.setContactPersonEmail("email@email.com");
    inst1.setContactPersonName("Jhon Doe");
    Institution inst2 = new Institution();
    inst2.setId(159);
    inst2.setContactPersonEmail("email2@email.com");
    inst2.setContactPersonName("Jhon 2 Doe");
    inst2.setName("El partner DOS");
    partners.add(inst1);
    partners.add(inst2);

    // Creating fake Project leader.
    leader = new Institution();
    leader.setId(321);
    leader.setName("Institution - Partner Leader");
    leader.setContactPersonEmail("leader@email.com");
    leader.setContactPersonName("Leader - Jhon Doe");

    // Creating fake partners with all the objects above.
    allPartners = new ArrayList<Institution>();
    allPartners.add(inst1);
    allPartners.add(inst2);
    allPartners.add(leader);


  }


}
