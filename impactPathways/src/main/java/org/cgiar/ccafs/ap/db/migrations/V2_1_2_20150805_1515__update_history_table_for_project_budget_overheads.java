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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal Bastidas
 */

public class V2_1_2_20150805_1515__update_history_table_for_project_budget_overheads implements JdbcMigration {

  private static Logger LOG = LoggerFactory
    .getLogger(V2_1_2_20150805_1515__update_history_table_for_project_budget_overheads.class);
  private Flyway flyway;

  public V2_1_2_20150805_1515__update_history_table_for_project_budget_overheads() {
    flyway = new Flyway();
  }

  @Override
  public void migrate(Connection connection) throws Exception {
    Statement statement = connection.createStatement();
    String tableName = "project_budget_overheads";

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

    try {
      // Don't forget to ensure the history database before create the new table
      dbManager.useHistoryDatabase();

      tableManager.dropLogTable(tableName);
      tableManager.createLogTable(tableName);

      Statement stmnt = connection.createStatement();
      stmnt.addBatch("USE " + dbName);
      stmnt
        .addBatch("ALTER TABLE `project_budget_overheads` ADD UNIQUE INDEX `UK_project_budget_overheads` (`project_id` ASC);");
      stmnt.executeBatch();

    } catch (SQLException e) {
      LOG.error("There was an error running the migration.");
      throw e;
    }
  }
}
