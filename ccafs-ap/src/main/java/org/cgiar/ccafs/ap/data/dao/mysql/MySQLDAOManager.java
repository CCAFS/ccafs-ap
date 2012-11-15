package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.util.PropertiesManager;

import java.sql.Connection;
import java.sql.DriverManager;
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
      e.printStackTrace();
      return -1;
    }
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
      e.printStackTrace();
      return null;
    }
  }

  @Override
  protected boolean registerDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    } catch (InstantiationException e) {

      e.printStackTrace();
    } catch (IllegalAccessException e) {

      e.printStackTrace();
    }
    return true;
  }


}
