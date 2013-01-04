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

import com.google.inject.Inject;


public class MySQLDAOManager extends DAOManager {

  @Inject
  public MySQLDAOManager(PropertiesManager properties) {
    super(properties);
  }

  @Override
  public Connection getConnection() {
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
      // TODO Auto-generated catch block
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public int makeChangeSecure(Connection conn, String preparedUpdateQuery, Object[] values) {
    int rowsChanged = -1;
    try (PreparedStatement stm = conn.prepareStatement(preparedUpdateQuery)) {
      for (int c = 0; c < values.length; c++) {
        if (values[c] instanceof String) {
          stm.setString((c + 1), (String) values[c]);
        } else if (values[c] instanceof Integer) {
          stm.setInt((c + 1), (int) values[c]);
        } else if (values[c] instanceof Boolean) {
          stm.setBoolean((c + 1), (boolean) values[c]);
        } else {
          stm.setObject((c + 1), values[c]);
        }
      }
      rowsChanged = stm.executeUpdate();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rowsChanged;
  }

  @Override
  public ResultSet makeQuery(String query, Connection conn) {
    try {
      Statement stm = conn.createStatement();
      return stm.executeQuery(query);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected Connection openConnection(String user, String password, String ip, String port, String databaseName) {
    try {
      Connection conexion =
        DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + databaseName, user, password);
      return conexion;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  @Override
  protected boolean registerDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return false;
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }


}
