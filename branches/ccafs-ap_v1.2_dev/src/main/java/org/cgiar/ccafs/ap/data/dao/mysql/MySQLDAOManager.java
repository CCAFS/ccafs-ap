package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.util.PropertiesManager;

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


public class MySQLDAOManager extends DAOManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLDAOManager.class);

  @Inject
  public MySQLDAOManager(PropertiesManager properties) {
    super(properties);
  }

  @Override
  public Connection getConnection() throws SQLException {
    return openConnection(this.getProperties().getPropertiesAsString(APConfig.MYSQL_USER), this.getProperties()
      .getPropertiesAsString(APConfig.MYSQL_PASSWORD), this.getProperties().getPropertiesAsString(APConfig.MYSQL_HOST),
      this.getProperties().getPropertiesAsString(APConfig.MYSQL_PORT),
      this.getProperties().getPropertiesAsString(APConfig.MYSQL_DATABASE));
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

  @Override
  public int makeChangeSecure(Connection conn, String preparedUpdateQuery, Object[] values) {
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
}
