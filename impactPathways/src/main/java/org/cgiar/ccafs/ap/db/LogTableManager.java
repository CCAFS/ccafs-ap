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

package org.cgiar.ccafs.ap.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class LogTableManager {

  private static Logger LOG = LoggerFactory.getLogger(LogTableManager.class);

  private Connection connection;
  private String tableName;
  private String originalDbName;

  public LogTableManager(Connection connection, String dbName, String tableName) {
    this.connection = connection;
    this.originalDbName = dbName;
    this.tableName = tableName;
  }


  /**
   * This method creates a copy of the table databaseName.tableName into the table
   * logDatabaseName.tableName and add the columns required to record the changes in
   * the original table.
   * 
   * @throws SQLException
   */
  public void createLogTable() throws SQLException {
    Statement statement = connection.createStatement();
    StringBuilder query = new StringBuilder();

    // First, create a copy of the original table in the log db
    query.append("CREATE TABLE IF NOT EXISTS ");
    query.append(tableName);
    query.append(" LIKE ");
    query.append(originalDbName + "." + tableName);
    query.append(";");

    // Then add the additional fields using an stored procedure to keep the script idempotent
    query.append("DROP PROCEDURE IF EXISTS adjust_history_table; ");
    query.append("DELIMITER $$ ");

    query.append("-- Create the stored procedure to perform the adjustments ");
    query.append("CREATE PROCEDURE adjust_history_table() ");
    query.append("BEGIN ");

    // Validate that the column `record_id` doesn't exists to add it to the table.
    query.append("IF NOT EXISTS  ");
    query.append("((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='");
    query.append(tableName);
    query.append("' AND column_name='record_id')) THEN ");
    query.append(" ALTER TABLE `");
    query.append(tableName);
    query.append("` ADD `record_id` BIGINT NOT NULL AFTER `id`; ");
    query.append("END IF; ");

    // Validate that the column `created_by` exists to drop it
    query.append("IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='");
    query.append(tableName);
    query.append("' AND column_name='created_by')) THEN ");
    query.append("ALTER TABLE `");
    query.append(tableName);
    query.append("` DROP `created_by`; ");
    query.append("END IF; ");

    // Validate the column active since is present to update it
    query.append("IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='");
    query.append(tableName);
    query.append("' AND column_name='active_since')) THEN ");
    query.append("ALTER TABLE `");
    query.append(tableName);
    query.append("` CHANGE `active_since` `active_since` DATETIME NOT NULL; ");
    query.append("END IF; ");

    // Validate if the column `active_until` exists to create it if needed
    query
      .append("IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='");
    query.append(tableName);
    query.append("' AND column_name='active_until')) THEN ");
    query.append("ALTER TABLE `");
    query.append(tableName);
    query.append("` ADD `active_until` DATETIME NULL AFTER `active_since`;");
    query.append("END IF; ");

    // Validate if the column `action` exists to create it if needed
    query
      .append("IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='");
    query.append(tableName);
    query.append("' AND column_name='action')) THEN ");
    query.append("ALTER TABLE `");
    query.append(tableName);
    query.append("` ADD `action` varchar(15) NOT NULL; ");
    query.append("END IF; ");

    // Close the procedure definition
    query.append("END $$ ");
    query.append("DELIMITER ; ");

    // Call the procedure and close it.
    query.append("CALL adjust_history_table(); ");
    query.append("DROP PROCEDURE IF EXISTS adjust_history_table; ");

    try {
      statement.execute(query.toString());
    } catch (SQLException e) {
      LOG.error("Exception raised trying to create the history table {}.", tableName);
      throw e;
    } finally {
      statement.close();
    }
  }

  /**
   * This method drop the table tableName of the history database.
   * 
   * @throws SQLException
   */
  public void dropLogTable() throws SQLException {
    Statement statement = connection.createStatement();
    StringBuilder query = new StringBuilder();

    query.append("DROP TABLE IF EXISTS ");
    query.append(tableName);
    query.append("; ");

    try {
      statement.execute(query.toString());
    } catch (SQLException e) {
      LOG.error("Exception raise trying to drop the table {} from the history database.", tableName);
      throw e;
    } finally {
      statement.close();
    }
  }

  /**
   * This method verifies if the table named with the value received by parameter exists.
   * 
   * @param tableName
   * @param databaseName
   * @return true if tableName exists. False otherwise
   */
  public boolean isTableAvailable(String tableName, String databaseName) {
    Statement statement = null;
    StringBuilder query = new StringBuilder();
    query.append("SHOW TABLES LIKE '");
    query.append("SELECT COUNT(*) FROM information_schema.tables  ");
    query.append("WHERE table_schema = '");
    query.append(databaseName);
    query.append("' AND ");
    query.append("table_name = '");
    query.append(tableName);
    query.append("'; ");

    try {
      statement = connection.createStatement();
      if (statement.executeQuery(query.toString()).next()) {
        return true;
      }
      statement.close();
    } catch (SQLException e) {
      LOG.error("Exception raised trying to verify if the table {}.{} exists",
        new Object[] {databaseName, tableName, e});
    } finally {
    }

    return false;
  }

  public void setDatabaseName(String databaseName) {
    this.originalDbName = databaseName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

}
