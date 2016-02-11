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


package org.cgiar.ccafs.ap.data.dao.mysqlhiberate;


import org.cgiar.ccafs.ap.data.dao.DeliverablePublicationMetadataDAO;
import org.cgiar.ccafs.ap.data.model.DeliverablePublicationMetadata;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeliverablePublicationMetaDataMySQLDAO implements DeliverablePublicationMetadataDAO {

  private static Logger LOG = LoggerFactory.getLogger(DeliverablePublicationMetaDataMySQLDAO.class); // TODO To review

  private StandardDAO dao;

  @Inject
  public DeliverablePublicationMetaDataMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public DeliverablePublicationMetadata findDeliverablePublicationMetadata(int deliverableId) {
    String sql = "from " + DeliverablePublicationMetadata.class.getName() + " where deliverable_id=" + deliverableId;
    List<DeliverablePublicationMetadata> listDissemination = dao.findAll(sql);
    if (listDissemination.size() > 0) {
      return listDissemination.get(0);
    }
    return null;
  }

  @Override
  public int save(DeliverablePublicationMetadata dissemination) {
    dao.saveOrUpdate(dissemination);
    return dissemination.getId(); // TODO To review
  }
}