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

import org.cgiar.ccafs.ap.db.LogDatabaseManager;
import org.cgiar.ccafs.ap.db.LogTableManager;
import org.cgiar.ccafs.ap.db.LogTriggersManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class V2_1_2_20150717_1102__update_all_triggers implements JdbcMigration {

  private Flyway flyway;
  private static Logger LOG = LoggerFactory.getLogger(V2_1_2_20150717_1102__update_all_triggers.class);

  public V2_1_2_20150717_1102__update_all_triggers() {
    flyway = new Flyway();
  }

  @Override
  public void migrate(Connection connection) throws Exception {
    Statement statement = connection.createStatement();
    String[] tableNames =
      {"deliverable_partnerships", "deliverables", "ip_project_contribution_overviews", "ip_project_contributions",
        "ip_project_indicators", "next_users", "project_budgets", "project_crp_contributions", "project_focuses",
        "project_locations", "project_other_contributions", "project_outcomes", "project_partners", "projects"};

    String query = "SELECT DATABASE() as dbName ;";
    String dbName = "";

    ResultSet rs = statement.executeQuery(query);
    if (rs.next()) {
      dbName = rs.getString("dbName");
    } else {
      String msg = "There was an error getting the current database name";
      LOG.error(msg);
      throw new Exception(msg);
    }

    LogDatabaseManager dbManager = new LogDatabaseManager(connection, dbName);
    LogTableManager tableManager = new LogTableManager(connection, dbName);
    LogTriggersManager triggerManager;

    try {

      if (!dbManager.isLogDatabaseAvailable()) {
        // And create the database again
        dbManager.createHistoryDatabase();
      }

      for (String tableName : tableNames) {

        triggerManager = new LogTriggersManager(connection, dbName, tableName);

        triggerManager.createTrigger("insert");
        triggerManager.createTrigger("update");
      }
    } catch (SQLException e) {
      LOG.error("There was an error running the migration.");
      throw e;
    }
  }
}
