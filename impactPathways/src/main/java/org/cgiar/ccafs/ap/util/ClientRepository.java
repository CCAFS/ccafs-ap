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


package org.cgiar.ccafs.ap.util;

import java.util.List;

import org.dom4j.Element;
import se.kb.oai.pmh.OaiPmhServer;
import se.kb.oai.pmh.Record;

/**
 * @author Christian David Garcia Oviedo
 */
public class ClientRepository {


  public Metadata getMetadata(String linkRequest, String id) {
    Metadata metadataObject = new Metadata();


    try {

      OaiPmhServer server = new OaiPmhServer(linkRequest);
      Record record = server.getRecord(id, "oai_dc");
      Element metadata = record.getMetadata();

      List<Element> elements = metadata.elements();
      for (Element element : elements) {
        switch (element.getQName().getName()) {
          case "title":
            if (metadataObject.getTitle() == null) {
              metadataObject.setTitle(element.getStringValue());
            } else {
              metadataObject.setTitle(metadataObject.getTitle() + ", " + element.getStringValue());
            }
            break;
          case "creator":
            if (metadataObject.getAuthors() == null) {
              metadataObject.setAuthors(element.getStringValue());
            } else {
              metadataObject.setAuthors(metadataObject.getAuthors() + ", " + element.getStringValue());
            }
            break;
          case "subject":
            if (metadataObject.getSubject() == null) {
              metadataObject.setSubject(element.getStringValue());
            } else {
              metadataObject.setSubject(metadataObject.getSubject() + ", " + element.getStringValue());
            }
            break;
          case "description":
            if (metadataObject.getDescription() == null) {
              metadataObject.setDescription(element.getStringValue());
            } else {
              metadataObject.setDescription(metadataObject.getDescription() + ", " + element.getStringValue());
            }
            break;
          case "date":
            if (metadataObject.getPublication() == null) {
              metadataObject.setPublication(element.getStringValue());
            }
            break;
          case "identifier":
            if (metadataObject.getIdentifier() == null) {
              metadataObject.setIdentifier(element.getStringValue());
            }
            break;
          case "language":
            if (metadataObject.getLanguage() == null) {
              metadataObject.setLanguage(element.getStringValue());
            }
            break;

        }

      }

    } catch (Exception e) {
      metadataObject = null;
      e.printStackTrace();
    }

    return metadataObject;
  }


}
