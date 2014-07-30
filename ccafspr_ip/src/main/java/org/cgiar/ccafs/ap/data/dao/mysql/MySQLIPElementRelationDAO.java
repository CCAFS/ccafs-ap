package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPElementRelationDAO;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLIPElementRelationDAO implements IPElementRelationDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLIPElementRelationDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLIPElementRelationDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }


  @Override
  public boolean deleteIpElementsRelation(int parentElementID, int childElementID) {
    LOG.debug(">> deleteIpElementsRelation(parentElementID={}, childElementID={})", parentElementID, childElementID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM ip_relationships ");
    query.append("WHERE parent_id = ? AND child_id = ?");

    int rowsDeleted = daoManager.delete(query.toString(), new Object[] {parentElementID, childElementID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteIpElementsRelation():{}", true);
      return true;
    }
    LOG.debug("<< deleteIpElementsRelation():{}", false);
    return false;
  }


  @Override
  public boolean deleteRelationsByChildElement(int childElementID) {
    LOG.debug(">> removeRelationsByChildElement(childElementID={})", childElementID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM ip_relationships ");
    query.append("WHERE child_id = ?");

    int rowsDeleted = daoManager.delete(query.toString(), new Object[] {childElementID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< removeRelationsByChildElement():{}", true);
      return true;
    }
    LOG.debug("<< removeRelationsByChildElement():{}", false);
    return false;
  }


}
