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
import org.cgiar.ccafs.ap.db.LogTriggersManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class V3_0_1_20151019_0902__Update_triger_to_project_outcomes_without_removing_records implements JdbcMigration {

  private static Logger LOG =
    LoggerFactory.getLogger(V3_0_1_20151019_0902__Update_triger_to_project_outcomes_without_removing_records.class);

  @Override
  public void migrate(Connection connection) throws Exception {
    Statement statement = connection.createStatement();
    String[] tableNames = {"project_outcomes"};

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
    // LogTableManager tableManager = new LogTableManager(connection, dbName);
    LogTriggersManager triggerManager;

    try {

      // if (!dbManager.isLogDatabaseAvailable()) {
      // And create the database again
      // dbManager.createHistoryDatabase(); NOT NEEDED for this process in particular.
      // }


      for (String tableName : tableNames) {
        try {
          dbManager.useHistoryDatabase();
          // Not going to delete the LOG TABLE.
          // tableManager.dropLogTable(tableName);
          // tableManager.createLogTable(tableName);

          triggerManager = new LogTriggersManager(connection, dbName, tableName);

          // Update triggers as per the deletion of the two columns in project_outcomes table, please refer to the
          // following SQL migration file:
          // V3_0_1_20151019_0845__Removing_gender_column_in_project_outcomes_table.sql.
          triggerManager.createTrigger("insert");
          triggerManager.createTrigger("update");
        } catch (Exception e) {
          throw e;

        }
      }
    } catch (SQLException e) {
      LOG.error("There was an error running the migration.");
      throw e;
    }

  }
}