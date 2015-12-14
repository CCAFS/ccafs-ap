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
package org.cgiar.ccafs.utils.db.mysql;

import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.PropertiesManager;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to deal with all MySQL connections and transactions.
 * 
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal.
 */
public class MySQLDAOManager extends DAOManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLDAOManager.class);

  @Inject
  public MySQLDAOManager(PropertiesManager properties) {
    super(properties);
  }

  @Override
  public int delete(String preparedUpdateQuery, Object[] values) {
    try (Connection conn = this.getConnection()) {
      return this.makeChangeSecure(conn, preparedUpdateQuery, values);
    } catch (SQLException e) {
      LOG.error("Connection to the database couldn't be established.", e);
      return -1;
    }
  }

  @Override
  public boolean deleteOnCascade(String tableName, String columnName, Object columnValue, int userID,
    String justification) {
    ResultSet rsReferences, rsColumnExist;
    StringBuilder query = new StringBuilder();
    Object[] values;
    int result;
    boolean problem = false;

    try (Connection conn = this.getConnection()) {

      // Let's find all the tables that are related to the current table.
      query.append("SELECT * FROM information_schema.KEY_COLUMN_USAGE ");
      query.append("WHERE TABLE_SCHEMA = '");
      query.append(this.getProperties().getPropertiesAsString(APConfig.MYSQL_DATABASE));
      query.append("' ");
      query.append("AND REFERENCED_TABLE_NAME = '");
      query.append(tableName);
      query.append("' ");
      query.append("AND REFERENCED_COLUMN_NAME = '");
      query.append(columnName);
      query.append("' ");
      rsReferences = this.makeQuery(query.toString(), conn);

      String table, column;
      while (rsReferences.next()) {
        // Validate that the column "is_active" exists.
        table = rsReferences.getString("TABLE_NAME");
        column = rsReferences.getString("COLUMN_NAME");

        query.setLength(0);
        query.append("SELECT COUNT(*) FROM information_schema.COLUMNS ");
        query.append("WHERE TABLE_SCHEMA = '");
        query.append(this.getProperties().getPropertiesAsString(APConfig.MYSQL_DATABASE));
        query.append("' ");
        query.append("AND TABLE_NAME = '");
        query.append(table);
        query.append("' ");
        query.append("AND COLUMN_NAME = 'is_active'");

        rsColumnExist = this.makeQuery(query.toString(), conn);
        if (rsColumnExist.next() && rsColumnExist.getInt(1) != 0) {
          // If is_active exists.

          query.setLength(0);
          query.append("UPDATE ");
          query.append(table);
          query.append(" SET is_active = 0, modified_by = ?, modification_justification = ? ");
          query.append("WHERE ");
          query.append(column);
          query.append(" = ?");

          values = new Object[3];
          values[0] = userID;
          values[1] = justification;
          values[2] = columnValue;

          // Setting is_active to 0.
          result = this.makeChangeSecure(conn, query.toString(), values);
          if (result == -1) {
            // Mark a problem.
            problem = true;
          }
        }
      }

    } catch (SQLException e) {
      LOG.error("There was a problem trying to open a connection to the database.", e);
    }

    return !problem;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return this.openConnection(this.getProperties().getPropertiesAsString(APConfig.MYSQL_USER),
      this.getProperties().getPropertiesAsString(APConfig.MYSQL_PASSWORD),
      this.getProperties().getPropertiesAsString(APConfig.MYSQL_HOST),
      this.getProperties().getPropertiesAsString(APConfig.MYSQL_PORT),
      this.getProperties().getPropertiesAsString(APConfig.MYSQL_DATABASE));
    // return null;
  }

  @Override
  public int makeChange(String updateQuery, Connection conn) {
    try {
      Statement stm = conn.createStatement();
      return stm.executeUpdate(updateQuery);
    } catch (SQLException e) {
      LOG.error("There was a problem making a change into the database. \n{}", updateQuery, e);
      return -1;
    }
  }

  /**
   * Execute an update statement in a secure way avoiding SQL Injections and other vulnerabilities.
   * 
   * @param connection - a SQL Connection object which represents the connection to the DAO.
   * @param preparedUpdateQuery - Secure query without values defined on it.
   * @param values - An array of Objects values.
   * @return an integer representing the number of affected rows or -1 if any problem appear.
   */
  private int makeChangeSecure(Connection conn, String preparedUpdateQuery, Object[] values) {
    int rowsChanged = -1;
    String query = "";
    try (PreparedStatement stm = conn.prepareStatement(preparedUpdateQuery)) {
      for (int c = 0; c < values.length; c++) {
        if (values[c] == null) {
          // Lest's try to use the same type for all columns and see what happens.
          // Some databases don't need to have a specific SQL Data Type.
          stm.setNull((c + 1), Types.VARCHAR);
        } else if (values[c] instanceof String) {
          stm.setString((c + 1), (String) values[c]);
        } else if (values[c] instanceof Integer) {
          stm.setInt((c + 1), (int) values[c]);
        } else if (values[c] instanceof Boolean) {
          stm.setBoolean((c + 1), (boolean) values[c]);
        } else {
          stm.setObject((c + 1), values[c]);
        }
      }
      query = stm.toString();
      rowsChanged = stm.executeUpdate();
    } catch (SQLException e) {
      LOG.error("There was a problem making a secure change to the database. \n{}", query, e);
    }
    return rowsChanged;

  }

  @Override
  public ResultSet makeQuery(String query, Connection conn) {
    try {
      Statement stm = conn.createStatement();
      return stm.executeQuery(query);
    } catch (SQLException e) {
      LOG.error("There was a problem making a query to the database. \n{}", query, e);
    }
    return null;
  }

  @Override
  protected Connection openConnection(String user, String password, String ip, String port, String databaseName)
    throws SQLException {
    Connection conexion;
    try {
      conexion = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + databaseName, user, password);
    } catch (SQLException e) {
      LOG.error("Connection to the database couldn't be established.", e);
      throw new SQLException("There is not conection to the database.");
    }
    return conexion;
  }

  @Override
  protected boolean registerDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (ClassNotFoundException e) {
      LOG.error("There was a problem registering database driver. System don't found 'com.mysql.jdbc.Driver'", e);
      return false;
    } catch (InstantiationException e) {
      String msg =
        "There was a problem registering database driver. It wasn't posible get an instance of 'com.mysql.jdbc.Driver'";
      LOG.error(msg, e);
    } catch (IllegalAccessException e) {
      LOG.error("There was a problem registering database driver. It wasn't posible acces to 'com.mysql.jdbc.Driver'",
        e);
    }
    return true;
  }

  @Override
  public int saveData(String preparedUpdateQuery, Object[] values) {
    int generatedId = -1;
    try (Connection conn = this.getConnection()) {
      int recordsAdded = this.makeChangeSecure(conn, preparedUpdateQuery, values);
      if (recordsAdded > 0) {
        // get the id assigned to this new record.
        try (ResultSet rs = this.makeQuery("SELECT LAST_INSERT_ID()", conn)) {
          if (rs.next()) {
            generatedId = rs.getInt(1);
          }
          rs.close();
        } catch (SQLException e1) {
          LOG.error("There was a problem getting the last inserted id.", e1);
        }
      } else if (recordsAdded == 0) {
        generatedId = 0;
      }
    } catch (SQLException e) {
      LOG.error("There was a problem trying to open a connection to the database.", e);
    }
    return generatedId;


  }
}
