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
 * @author Hernán David Carvajal
 */

public class V2_1_2_20150624_1045__improve_log_history_triggers implements JdbcMigration {

  private Flyway flyway;
  private static Logger LOG = LoggerFactory.getLogger(V2_1_2_20150624_1045__improve_log_history_triggers.class);

  public V2_1_2_20150624_1045__improve_log_history_triggers() {
    flyway = new Flyway();
  }

  @Override
  public void migrate(Connection connection) throws Exception {
    Statement statement = connection.createStatement();
    String[] tableNames = new String[] {"projects", "project_partners"};

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
      // As I am sure that we haven't used the database previously, I am going to drop it
      dbManager.dropHistoryDatabase();

      // And create the database again
      dbManager.createHistoryDatabase();

      // We don't need to validate if the table exists since we recently created the database
      for (String tableName : tableNames) {
        // Don't forget to ensure the history database before create the new table
        dbManager.useHistoryDatabase();

        tableManager.createLogTable(tableName);
      }
    } catch (SQLException e) {
      LOG.error("There was an error running the migration.");
      throw e;
    }
  }
}
