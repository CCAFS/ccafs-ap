package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDAOManager;
import org.cgiar.ccafs.ap.util.PropertiesManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDAOManager.class)
public abstract class DAOManager {

  private PropertiesManager properties;

  public DAOManager(PropertiesManager properties) {
    this.properties = properties;
    registerDriver();
  }

  /**
   * This method close the conection with the database and frees resources.
   * 
   * @deprecated The new java version (7) implements the interface java.lang.AutoCloseable which can be used
   *             automatically in the try-catch block.
   * @param connection
   * @return true if all were ok, and false otherwise.
   */
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
   * Create a connection to the database depending on the credential information added in the properties configuration
   * file.
   * 
   * @return A new object connection.
   */
  public Connection getConnection() {
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
   * Execute an update statement in a secure way avoiding SQL Injections and other vulnerabilities.
   * 
   * @param conn - a SQL Connection object which represents the connection to the DAO.
   * @param preparedUpdateQuery - Secure query without values defined on it.
   * @param values - An array of Objects values.
   * @return an integer representing the number of affected rows or -1 if any problem appear.
   */
  public int makeChangeSecure(Connection conn, String preparedUpdateQuery, Object[] values) {
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
  protected Connection openConnection(String user, String password, String ip, String port, String databaseName) {
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


}
