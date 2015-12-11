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
 * ***************************************************************
 */
package org.cgiar.ccafs.utils.db;

import org.cgiar.ccafs.utils.PropertiesManager;
import org.cgiar.ccafs.utils.db.mysql.MySQLDAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal.
 */
@ImplementedBy(MySQLDAOManager.class)
public abstract class DAOManager {

  private PropertiesManager properties;

  public DAOManager(PropertiesManager properties) {
    this.properties = properties;
    // this.registerDriver();
  }

  /**
   * This method close the conection with the database and frees resources.
   * 
   * @deprecated The new java version (7) implements the interface java.lang.AutoCloseable which can be used
   *             automatically in the try-catch block.
   * @param connection
   * @return true if all were ok, and false otherwise.
   */
  @SuppressWarnings("unused")
  @Deprecated
  private boolean closeConnection(Connection connection) {
    try {
      connection.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;

  }

  /**
   * Fix a string that contains simple quotes.
   * 
   * @param string that contains quotes.
   * @return a fixed string.
   */
  public String correctStringToQuery(String string) {
    return null;
  }

  /**
   * This method deletes one or more records from the database.
   * 
   * @param preparedUpdateQuery is the prepared String to be executed.
   * @param values are the list of values to be inserted.
   * @return the number of records deleted, or -1 if an error occurs.
   */
  public int delete(String preparedUpdateQuery, Object[] values) {
    return -1;
  }

  /**
   * This method deletes all the records (is_active = 0) that have foreign keys relations to a specific table and column
   * name.
   * ****** PLEASE USE THIS METHOD CAREFULLY *******
   * It uses the following Query in SQL:
   * SELECT * FROM information_schema.KEY_COLUMN_USAGE
   * WHERE REFERENCED_TABLE_NAME = 'tableName'
   * AND REFERENCED_COLUMN_NAME = 'columnValue';
   * 
   * @param tableName - the table name.
   * @param columnName - the column name
   * @param columnValue - is the value of the record that is going to be deleted.
   * @param userID - is the user who is making the change.
   * @param justification - is the justification statement
   * @return true if the deletion process finished successfully, false otherwise.
   */
  public boolean deleteOnCascade(String tableName, String columnName, Object columnValue, int userID,
    String justification) {
    // This method is defined in MySQLDAOManager.
    return true;
  }

  /**
   * Create a connection to the database depending on the credential information added in the properties configuration
   * file.
   * 
   * @return A new object connection.
   */
  public Connection getConnection() throws SQLException {
    return null;
  }

  /**
   * Return a properties object that connects to the properties configuration file.
   * 
   * @return A Properties object.
   */
  public final PropertiesManager getProperties() {
    return properties;
  }

  /**
   * This method make a change in the database. This query has to start with
   * the word UPDATE or INSERT. If you want to make a query, you have to use the
   * method named "makeQuery".
   * 
   * @param updateQuery
   *        - SQL code to make an insert or an update.
   * @return The number of affected rows after the query. -1 in case an error ocurrs.
   */
  public int makeChange(String updateQuery, Connection connection) {
    return -1;
  }

  /**
   * This method executes a query. The query string must start with the word
   * "SELECT".
   * 
   * @param query
   *        - SQL code to take data from the database.
   * @return ResulSet object which corresponds to the query result. Or null if
   *         there was an error.
   */
  public ResultSet makeQuery(String query, Connection connection) {
    return null;
  }

  /**
   * open the connection to the database
   * 
   * @param user
   * @param password
   * @return A Connection object type
   */
  protected Connection openConnection(String user, String password, String ip, String port, String databaseName)
    throws SQLException {
    return null;
  }

  /**
   * Initialize database driver
   * 
   * @return false if the driver was not found or any other problem occurs.
   */
  protected boolean registerDriver() {
    return false;
  }

  /**
   * This method saves or updates any record into the database.
   * 
   * @param preparedUpdateQuery is the prepared String to be executed.
   * @param values are the list of values to be inserted.
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveData(String preparedUpdateQuery, Object[] values) {
    return -1;
  }

}
