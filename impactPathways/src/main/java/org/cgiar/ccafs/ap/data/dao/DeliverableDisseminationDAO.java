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


package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.DeliverableDisseminationMySQLDAO;
import org.cgiar.ccafs.ap.data.model.DeliverableDissemination;
import org.cgiar.ccafs.ap.data.model.DeliverableMetadataElements;
import org.cgiar.ccafs.ap.data.model.MetadataElements;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableDisseminationMySQLDAO.class)
public interface DeliverableDisseminationDAO {

  public DeliverableDissemination findDeliverableDissemination(int deliverableId);

  public List<DeliverableMetadataElements> findDeliverableElements(int deliverableId);

  public DeliverableMetadataElements findDeliverableMetadata(int deliverableId, int field);


  public List<MetadataElements> findMetadataFields(int deliverableId);

  public int save(DeliverableDissemination dissemination);

  public int saveMetadataElement(DeliverableMetadataElements element);

}
