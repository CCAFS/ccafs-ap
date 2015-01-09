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

import org.cgiar.ccafs.ap.data.dao.MetadataDAO;
import org.cgiar.ccafs.ap.data.manager.MetadataManager;
import org.cgiar.ccafs.ap.data.model.Metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class MetadataManagerImpl implements MetadataManager {

  private MetadataDAO metadataDAO;

  @Inject
  public MetadataManagerImpl(MetadataDAO metadataDAO) {
    this.metadataDAO = metadataDAO;
  }

  @Override
  public List<Metadata> getMetadataList() {
    List<Metadata> metadataList = new ArrayList<>();
    List<Map<String, String>> dataList = metadataDAO.getMetadataList();

    for (Map<String, String> data : dataList) {
      Metadata metadata = new Metadata();
      metadata.setId(Integer.parseInt(data.get("id")));
      metadata.setDescription(data.get("description"));
      metadata.setName(data.get("name"));

      metadataList.add(metadata);
    }

    return metadataList;
  }

}
