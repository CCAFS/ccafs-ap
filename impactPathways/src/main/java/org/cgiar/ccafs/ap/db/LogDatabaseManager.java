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

public class LogDatabaseManager {

  private static Logger LOG = LoggerFactory.getLogger(LogDatabaseManager.class);

  private Connection connection;
  private String databaseName;
  private String logDatabaseName;

  public LogDatabaseManager(Connection connection, String dbName) {
    this.connection = connection;
    this.databaseName = dbName;
    this.logDatabaseName = dbName + "_history";
  }

  private void addLogDatabaseProcedures() throws SQLException {
    Statement statement = connection.createStatement();

    StringBuilder query = new StringBuilder();
    query.append("DROP PROCEDURE IF EXISTS updateActiveUntilDate; ");
    query.append("DELIMITER $$ ");

    query.append("CREATE PROCEDURE updateActiveUntilDate(IN tableName varchar(255), IN id BIGINT) ");
    query.append("BEGIN ");
    query.append("SET @query = CONCAT('UPDATE ', tableName, ' SET active_until=NOW() WHERE record_id = ', id, ");
    query.append(" ' ORDER BY active_since DESC LIMIT 1'); ");
    query.append("PREPARE stmt FROM @query; ");
    query.append("EXECUTE stmt; ");
    query.append("DEALLOCATE PREPARE stmt; ");

    query.append("END$$ ");
    query.append("DELIMITER ; ");

    try {
      statement.execute(query.toString());
    } catch (SQLException e) {
      LOG.error("Exception raised trying to create the stored procedures in the database {}.", logDatabaseName);
      throw e;
    } finally {
      statement.close();
    }
  }

  /**
   * This method creates the history database if not exists.
   * 
   * @throws SQLException
   */
  public void createHistoryDatabase() throws SQLException {
    Statement statement = connection.createStatement();

    StringBuilder query = new StringBuilder();
    query.append("CREATE DATABASE IF NOT EXISTS ");
    query.append(logDatabaseName);
    query.append("; ");

    try {
      statement.execute(query.toString());
      this.addLogDatabaseProcedures();
    } catch (SQLException e) {
      LOG.error("Exception raised trying to create the database {}.", logDatabaseName);
      throw e;
    } finally {
      statement.close();
    }
  }

  /**
   * This method drops the history database if exists.
   * 
   * @throws SQLException
   */
  public void dropHistoryDatabase() throws SQLException {
    Statement statement = connection.createStatement();

    StringBuilder query = new StringBuilder();
    query.append("DROP DATABASE IF EXISTS ");
    query.append(logDatabaseName);
    query.append("; ");

    try {
      statement.execute(query.toString());
      this.addLogDatabaseProcedures();
    } catch (SQLException e) {
      LOG.error("Exception raised trying to drop the database {}.", logDatabaseName);
      throw e;
    } finally {
      statement.close();
    }
  }

  /**
   * This method verifies if the log database exists.
   * 
   * @param tableName
   * @param databaseName
   * @return true if tableName exists. False otherwise
   */
  public boolean isLogDatabaseAvailable() {
    Statement statement = null;
    StringBuilder query = new StringBuilder();
    query.append("SELECT schema_name FROM INFORMATION_SCHEMA.schemata WHERE schema_name = '");
    query.append(databaseName);
    query.append("'; ");

    try {
      statement = connection.createStatement();
      if (statement.executeQuery(query.toString()).next()) {
        return true;
      }
      statement.close();
    } catch (SQLException e) {
      LOG.error("Exception raised trying to verify if the database {} exists", databaseName, e);
    }
    return false;
  }

  /**
   * This method set the log database as default.
   * 
   * @param databaseName
   * @throws SQLException
   */
  public void useHistoryDatabase() throws SQLException {
    Statement statement = connection.createStatement();

    StringBuilder query = new StringBuilder();
    query.append("USE ");
    query.append(logDatabaseName);
    query.append("; ");

    try {
      // Use the history database
      statement.execute(query.toString());
    } catch (SQLException e) {
      LOG.error("Exception raised trying to use database {}.", logDatabaseName);
      throw e;
    }
  }
}
