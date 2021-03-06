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


import org.cgiar.ccafs.ap.data.dao.DeliverableDisseminationDAO;
import org.cgiar.ccafs.ap.data.model.DeliverableDissemination;
import org.cgiar.ccafs.ap.data.model.DeliverableMetadataElements;
import org.cgiar.ccafs.ap.data.model.MetadataElements;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeliverableDisseminationMySQLDAO implements DeliverableDisseminationDAO {

  private static Logger LOG = LoggerFactory.getLogger(DeliverableDisseminationMySQLDAO.class); // TODO To review

  private StandardDAO dao;

  @Inject
  public DeliverableDisseminationMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public DeliverableDissemination findDeliverableDissemination(int deliverableId) {
    String sql = "from " + DeliverableDissemination.class.getName() + " where deliverable_id=" + deliverableId;
    List<DeliverableDissemination> listDissemination = dao.findAll(sql);
    if (listDissemination.size() > 0) {
      return listDissemination.get(0);
    }

    return null;
  }

  @Override
  public List<DeliverableMetadataElements> findDeliverableElements(int deliverableId) {
    String sql = "from " + DeliverableMetadataElements.class.getName() + " where deliverable_id=" + deliverableId;
    List<DeliverableMetadataElements> listMetada = dao.findAll(sql);
    for (DeliverableMetadataElements deliverableMetadataElements : listMetada) {
      deliverableMetadataElements
        .setMetadataElement(this.findMetadaElement(deliverableMetadataElements.getElementId()));
    }

    return listMetada;
  }

  @Override
  public DeliverableMetadataElements findDeliverableMetadata(int deliverableId, int field) {

    String sql = "from " + DeliverableMetadataElements.class.getName() + " where deliverable_id=" + deliverableId
      + " and element_id=" + field;
    List<DeliverableMetadataElements> listMetada = dao.findAll(sql);

    if (listMetada.size() > 0) {
      listMetada.get(0).setMetadataElement(this.findMetadaElement(listMetada.get(0).getElementId()));
      return listMetada.get(0);
    }

    DeliverableMetadataElements to_ret = new DeliverableMetadataElements();
    to_ret.setDeliverableId(deliverableId);
    to_ret.setElementId(field);
    to_ret.setMetadataElement(this.findMetadaElement(field));
    return to_ret;
  }

  public MetadataElements findMetadaElement(int elementID) {
    MetadataElements element = dao.find(MetadataElements.class, elementID);
    return element;
  }

  @Override
  public List<MetadataElements> findMetadataFields(int deliverableId) {

    List<MetadataElements> fields = dao.findEveryone(MetadataElements.class);
    return fields;

  }

  @Override
  public int save(DeliverableDissemination dissemination) {
    dao.saveOrUpdate(dissemination);
    return dissemination.getId(); // TODO To review
  }

  @Override
  public int saveMetadataElement(DeliverableMetadataElements element) {

    dao.saveOrUpdate(element);
    return element.getId();
  }
}