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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableMetadataDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableMetadataManager;
import org.cgiar.ccafs.ap.data.model.DeliverableMetadata;
import org.cgiar.ccafs.ap.data.model.Metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableMetadataManagerImpl implements DeliverableMetadataManager {

  public static Logger LOG = LoggerFactory.getLogger(DeliverableMetadataManagerImpl.class);
  private DeliverableMetadataDAO deliverableMetadataDAO;

  @Inject
  public DeliverableMetadataManagerImpl(DeliverableMetadataDAO deliverableMetadataDAO) {
    this.deliverableMetadataDAO = deliverableMetadataDAO;
  }

  @Override
  public List<DeliverableMetadata> getDeliverableMetadata(int deliverableID) {
    List<DeliverableMetadata> metadataList = new ArrayList<>();
    List<Map<String, String>> metadataInfo = deliverableMetadataDAO.getDeliverableMetadata(deliverableID);

    for (Map<String, String> mInfo : metadataInfo) {
      DeliverableMetadata dMetadata = new DeliverableMetadata();
      Metadata metadata = new Metadata();
      metadata.setId(Integer.parseInt(mInfo.get("metadata_id")));
      metadata.setName(mInfo.get("metadata_name"));

      dMetadata.setMetadata(metadata);
      dMetadata.setValue(mInfo.get("value"));

      metadataList.add(dMetadata);
    }

    return metadataList;
  }

  @Override
  public boolean saveDeliverableMetadata(List<DeliverableMetadata> deliverableMetadata, int deliverableID) {
    boolean saved = true;

    int c = 0;
    for (DeliverableMetadata dMetadata : deliverableMetadata) {
      System.out.println(c);
      if (dMetadata.getValue() == null || dMetadata.getValue().isEmpty()) {
        continue;
      }

      Map<String, Object> dMetadataInfo = new HashMap<>();
      dMetadataInfo.put("deliverable_id", deliverableID);
      dMetadataInfo.put("metadata_id", dMetadata.getMetadata().getId());
      dMetadataInfo.put("value", dMetadata.getValue());

      saved = saved && deliverableMetadataDAO.saveDeliverableMetadata(dMetadataInfo);
      c++;
    }

    return saved;
  }
}
