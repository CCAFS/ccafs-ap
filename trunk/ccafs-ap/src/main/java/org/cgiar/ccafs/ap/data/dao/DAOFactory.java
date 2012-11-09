package org.cgiar.ccafs.ap.data.dao;


public class DAOFactory {

  private static DAOFactory instance;

  private ActivityDAO activityDAO; // inject dependencies here.

  private DAOFactory() {
  }

  public static DAOFactory getInstance() {
    if (instance == null) {
      instance = new DAOFactory();
    }
    return instance;
  }

  public ActivityDAO getActivityDAO() {
    return activityDAO;
  }
}
