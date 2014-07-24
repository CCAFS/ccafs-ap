package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPRelationshipDAO;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLIPRelationshipDAO implements IPRelationshipDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLIPRelationshipDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLIPRelationshipDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean saveIPRelation(int parentElementID, int childElementID, int relationTypeID) {
    LOG.debug(">> saveIPRelation(parentElementID={}, childElementID={}, relationTypeID = {})", new int[] {
      parentElementID, childElementID, relationTypeID});

    StringBuilder query = new StringBuilder();
    boolean result = false;

    query.append("INSERT INTO ip_relationships (parent_id, child_id, relation_type_id) VALUES (?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE parent_id = parent_id ");
    Object[] values = new Object[3];
    values[0] = parentElementID;
    values[1] = childElementID;
    values[2] = relationTypeID;

    try (Connection con = databaseManager.getConnection()) {
      int ipRelationAdded = databaseManager.makeChangeSecure(con, query.toString(), values);
      if (ipRelationAdded > 0) {
        result = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveIPRelation() > There was a problem saving information into the database. \n{}", e);
    }

    LOG.debug("<< saveIPRelation():{}", result);
    return result;
  }

}
