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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class LogTriggersManager {

  private static Logger LOG = LoggerFactory.getLogger(LogTriggersManager.class);

  private Connection connection;
  private String databaseName;
  private String logDatabaseName;
  private String tableName;

  public LogTriggersManager(Connection connection, String dbName, String tableName) {
    this.connection = connection;
    this.databaseName = dbName;
    this.logDatabaseName = dbName + "_history";
    this.tableName = tableName;
  }


  public void createTrigger(String triggerAction) throws SQLException {
    Statement statement = connection.createStatement();
    StringBuilder query = new StringBuilder();

    // We don't need to insert the id in the history table, that is why we remove it from the list of columns
    String columnsToInsert = this.getLogTableColumns().replace("`id`,", "");
    String valuesToInsert = this.getTriggerValues();

    query.append("DROP TRIGGER IF EXISTS after_" + tableName + "_" + triggerAction + "; ");

    query.append("CREATE TRIGGER after_" + tableName + "_" + triggerAction + " ");
    query.append("AFTER UPDATE ON " + tableName);
    query.append("FOR EACH ROW BEGIN ");

    // Declare the action
    query.append("DECLARE trigger_action VARCHAR(50);");
    // Set the value of the variable according to the trigger and the action

    if (triggerAction.equals("insert")) {
      query.append("SET @trigger_action := 'insert'; ");
    } else if (triggerAction.equals("update")) {
      // If the trigger is updating the record we need to check if the record is being deleted.
      query.append("IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 ");
      query.append("  SET @trigger_action := 'delete'; ");
      query.append("ELSE ");
      query.append("  SET @trigger_action := 'update'; ");
      query.append("END IF; ");
    }

    // In the log table update the active_until of the last row with the same record_id with the value of NOW()
    query.append("CALL updateActiveUntilDate('" + tableName + "', OLD.`id`); ");

    // Insert the new record into the log table
    query.append("INSERT INTO ( ");
    query.append(columnsToInsert);
    query.append(") VALUES ( ");
    query.append(valuesToInsert);
    query.append("); ");

    query.append("SET @query = CONCAT('INSERT INTO ( " + columnsToInsert + " ) VALUES (" + valuesToInsert
      + ", \'', @trigger_action, '\' );");
    query.append("PREPARE stmt FROM @query; ");
    query.append("EXECUTE stmt; ");
    query.append("DEALLOCATE PREPARE stmt; ");

    query.append("END; ");

    try {
      statement.execute(query.toString());
    } catch (SQLException e) {
      LOG.error("Exception raised trying to create the trigger after_{}_{}.", tableName, triggerAction);
      throw e;
    } finally {
      statement.close();
    }

  }

  /**
   * This method returns a String with all the columns that has the table referenced in the property
   * tableName.
   * 
   * @return a string with the columns present in the table tableName.
   * @throws SQLException
   */
  private String getLogTableColumns() throws SQLException {
    Statement statement = connection.createStatement();

    StringBuilder query = new StringBuilder();
    query.append("SELECT GROUP_CONCAT( DISTINCT CONCAT('`', column_name, '`') SEPARATOR ', ') as columnNames ");
    query.append("FROM INFORMATION_SCHEMA.COLUMNS ");
    query.append("WHERE TABLE_SCHEMA='");
    query.append(logDatabaseName);
    query.append("' AND TABLE_NAME = '");
    query.append(tableName);
    query.append("'; ");

    try {
      ResultSet rs = statement.executeQuery(query.toString());
      if (rs.next()) {
        return rs.getString("columnNames");
      }
    } catch (SQLException e) {
      LOG.error("Exception raised trying to get the columns of the table {}.", tableName);
      throw e;
    } finally {
      statement.close();
    }

    return null;
  }


  /**
   * This method returns the values to be inserted by the trigger that populates the history tables. Keep in mind that
   * the 'action' column is filled by the trigger in charge of insert the values.
   * 
   * @param triggerAction - ['insert', 'update']
   * @return an string with the values to be inserted by the trigger.
   * @throws SQLException
   */
  private String getTriggerValues() throws SQLException {
    Statement statement = connection.createStatement();

    StringBuilder query = new StringBuilder();
    query.append("SELECT GROUP_CONCAT( DISTINCT ");
    query.append("          CASE column_name ");
    query.append("          WHEN 'active_since' THEN 'NOW()' ");
    query.append("          WHEN 'active_until' THEN 'NULL' ");
    query.append("          ELSE CONCAT('OLD.`', column_name, '`') ");
    query.append("          END ");
    query.append("       SEPARATOR ', ') as tableValues ");
    query.append("FROM INFORMATION_SCHEMA.COLUMNS ");
    query.append("WHERE TABLE_SCHEMA='");
    query.append(logDatabaseName);
    query.append("' AND TABLE_NAME = '");
    query.append(tableName);
    query.append("'; ");

    try {
      ResultSet rs = statement.executeQuery(query.toString());
      if (rs.next()) {
        return rs.getString("tableValues");
      }
    } catch (SQLException e) {
      LOG.error("Exception raised trying to get the columns of the table {}.", tableName);
      throw e;
    } finally {
      statement.close();
    }

    return null;
  }

}
