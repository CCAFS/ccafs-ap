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

package org.cgiar.ccafs.ap.db.migrations;

import org.cgiar.ccafs.ap.config.APModule;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Carlos Alberto Martínez M.
 */

public class V2_1_2_20150731_0947__validate_project_partners implements JdbcMigration {

  private static Logger LOG = LoggerFactory.getLogger(V2_1_2_20150731_0947__validate_project_partners.class);

  private ProjectManager projectManager;
  private ProjectPartnerManager projectPartnerManager;


  public V2_1_2_20150731_0947__validate_project_partners() {
    Injector in = Guice.createInjector(new APModule());
    this.projectManager = in.getInstance(ProjectManager.class);
    this.projectPartnerManager = in.getInstance(ProjectPartnerManager.class);

  }

  @Override
  public void migrate(Connection connection) throws Exception {

    PreparedStatement updateStatement = null;

    try {

      List<Project> projects = projectManager.getAllProjectsBasicInfo();
      // Going through each project.
      for (Project project : projects) {

        List<ProjectPartner> projectPartners = projectPartnerManager.getProjectPartners(project.getId());
        boolean user;
        // Setting responsibilities null to empty strings.
        for (int x = 0; x < projectPartners.size(); x++) {
          if (projectPartners.get(x).getResponsabilities() == null) {
            projectPartners.get(x).setResponsabilities("");
          }
        }
        for (int c = 0; c < projectPartners.size(); c++) {
          for (int j = c + 1; j < projectPartners.size(); j++) {
            user = true;
            // We do not do anything with those users that are null.
            if (projectPartners.get(c).getUser() == null || projectPartners.get(j).getUser() == null) {
              user = false;
            }
            // Looking for partners that have same user and institutions.
            if (user
              && projectPartners.get(c).getInstitution().getId() == projectPartners.get(j).getInstitution().getId()
              && projectPartners.get(c).getUser().getId() == projectPartners.get(j).getUser().getId()) {
              if (!projectPartners.get(c).getResponsabilities().equals("@#$")) {
                if (projectPartners.get(j).getType().equals("PL")) {
                  if (projectPartners.get(j).getResponsabilities().equals("")) {
                    projectPartners.get(j).setResponsabilities(projectPartners.get(c).getResponsabilities());
                    projectPartners.get(c).setResponsabilities("@#$");
                  } else {
                    projectPartners.get(j).setResponsabilities(
                      projectPartners.get(j).getResponsabilities() + "\n"
                        + projectPartners.get(c).getResponsabilities());
                    projectPartners.get(c).setResponsabilities("@#$");
                  }
                } else {
                  if (projectPartners.get(c).getResponsabilities().equals("")) {
                    projectPartners.get(c).setResponsabilities(projectPartners.get(j).getResponsabilities());
                    projectPartners.get(j).setResponsabilities("@#$");
                  } else {
                    projectPartners.get(c).setResponsabilities(
                      projectPartners.get(c).getResponsabilities() + "\n"
                        + projectPartners.get(j).getResponsabilities());
                    projectPartners.get(j).setResponsabilities("@#$");
                  }
                }
              }
            }
          }
        }
        Iterator<ProjectPartner> iter = projectPartners.iterator();
        while (iter.hasNext()) {
          ProjectPartner pp = iter.next();
          if (pp.getResponsabilities().equals("@#$")) {
            // Updating the activities with same leader
            for (ProjectPartner ppReal : projectPartners) {
              if (!ppReal.getResponsabilities().equals("@#$")
                && ppReal.getInstitution().getId() == pp.getInstitution().getId() && ppReal.getUser() != null
                && ppReal.getUser().getId() == pp.getUser().getId()) {
                updateStatement =
                  connection.prepareStatement("UPDATE activities a SET a.leader_id = " + ppReal.getId()
                    + " WHERE leader_id = " + pp.getId());
                updateStatement.execute();
              }
            }
            updateStatement = connection.prepareStatement("DELETE FROM project_partners WHERE id = " + pp.getId());
            updateStatement.execute();
            iter.remove();
          }
        }

        // Saving grouped responsibilities into the database.
        for (int i = 0; i < projectPartners.size(); i++) {
          PreparedStatement stm =
            connection.prepareStatement("UPDATE project_partners SET responsabilities = ? WHERE id = ?");
          stm.setString(1, projectPartners.get(i).getResponsabilities());
          stm.setInt(2, projectPartners.get(i).getId());
          stm.executeUpdate();
        }

      }


    } catch (SQLException e) {
      LOG.error("Error running the script.", e);
      throw e;
    }
  }

}
