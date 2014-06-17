/*
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
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.PublicationTypeDAO;
import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
import org.cgiar.ccafs.ap.data.model.PublicationType;

import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PublicationTypeManagerImpl implements PublicationTypeManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PublicationTypeManagerImpl.class);
  private PublicationTypeDAO publicationTypeDAO;

  @Inject
  public PublicationTypeManagerImpl(PublicationTypeDAO publicationTypeDAO) {
    this.publicationTypeDAO = publicationTypeDAO;
  }

  @Override
  public PublicationType getPublicationType(String id) {
    Map<String, String> pubTypeData = publicationTypeDAO.getPublicationType(id);
    if (pubTypeData.size() > 0) {
      PublicationType type = new PublicationType();
      type.setId(Integer.parseInt(pubTypeData.get("id")));
      type.setName(pubTypeData.get("name"));
      return type;
    }
    LOG.warn("Type wasn't found for publication {}", id);
    return null;
  }

  @Override
  public PublicationType[] getPublicationTypes() {
    Map<String, String> typeData = publicationTypeDAO.getPublicationTypes();
    PublicationType[] pubTypes = new PublicationType[typeData.keySet().size()];
    int c = 0;
    for (String typeId : typeData.keySet()) {
      PublicationType type = new PublicationType();
      type.setId(Integer.parseInt(typeId));
      type.setName(typeData.get(typeId));
      pubTypes[c] = type;
      c++;
    }
    return pubTypes;
  }
}
