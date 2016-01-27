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


          case "subject.agrovoc":

            if (metadataObject.getSubject() == null) {
              metadataObject.setSubjectAgrovoc(element.getStringValue());
            } else {
              metadataObject.setSubjectAgrovoc(metadataObject.getSubject() + ", " + element.getStringValue());
            }
            break;


          case "subject.domain-specific":
            if (metadataObject.getSubject() == null) {
              metadataObject.setSubjectDomainSpecific(element.getStringValue());
            } else {
              metadataObject.setSubjectDomainSpecific(metadataObject.getSubject() + ", " + element.getStringValue());
            }
            break;

          case "description":
            if (metadataObject.getDescription() == null) {
              metadataObject.setDescription(element.getStringValue());
            } else {
              metadataObject.setDescription(metadataObject.getDescription() + ", " + element.getStringValue());
            }
            break;

          case "publisher":
            if (metadataObject.getPublisher() == null) {
              metadataObject.setPublisher(element.getStringValue());
            } else {
              metadataObject.setPublisher(metadataObject.getPublisher() + ", " + element.getStringValue());
            }
            break;


          case "relation":
            if (metadataObject.getRelation() == null) {
              metadataObject.setRelation(element.getStringValue());
            } else {
              metadataObject.setRelation(metadataObject.getRelation() + ", " + element.getStringValue());
            }
            break;

          case "contributorcg.contributor.center":


            if (metadataObject.getContributorcgContributorCenter() == null) {
              metadataObject.setContributorcgContributorCenter(element.getStringValue());
            } else {
              metadataObject.setContributorcgContributorCenter(
                metadataObject.getContributorcgContributorCenter() + ", " + element.getStringValue());
            }
            break;


          case "contributor.crp":


            if (metadataObject.getContributorCrp() == null) {
              metadataObject.setContributorCrp(element.getStringValue());
            } else {
              metadataObject.setContributorCrp(metadataObject.getContributorCrp() + ", " + element.getStringValue());
            }
            break;


          case "contributor.funder":


            if (metadataObject.getContributorFunder() == null) {
              metadataObject.setContributorFunder(element.getStringValue());
            } else {
              metadataObject
                .setContributorFunder(metadataObject.getContributorFunder() + ", " + element.getStringValue());
            }
            break;

          case "contributor.partnerId":


            if (metadataObject.getContributorPartnerId() == null) {
              metadataObject.setContributorPartnerId(element.getStringValue());
            } else {
              metadataObject
                .setContributorPartnerId(metadataObject.getContributorPartnerId() + ", " + element.getStringValue());
            }
            break;

          case "contributor.project":


            if (metadataObject.getContributorProject() == null) {
              metadataObject.setContributorProject(element.getStringValue());
            } else {
              metadataObject
                .setContributorProject(metadataObject.getContributorProject() + ", " + element.getStringValue());
            }
            break;


          case "contributor.project-lead-institute":

            if (metadataObject.getContributorProjectLeadinstitute() == null) {
              metadataObject.setContributorProjectLeadinstitute(element.getStringValue());
            } else {
              metadataObject.setContributorProjectLeadinstitute(
                metadataObject.getContributorProjectLeadinstitute() + ", " + element.getStringValue());
            }
            break;
          case "coverage":

            if (metadataObject.getCoverage() == null) {
              metadataObject.setCoverage(element.getStringValue());
            } else {
              metadataObject.setCoverage(metadataObject.getCoverage() + ", " + element.getStringValue());
            }
            break;


          case "coverage.region":
            if (metadataObject.getCoverageRregion() == null) {
              metadataObject.setCoverageRregion(element.getStringValue());
            } else {
              metadataObject.setCoverageRregion(metadataObject.getCoverageRregion() + ", " + element.getStringValue());
            }
            break;


          case "coverage.country":
            if (metadataObject.getCoverageCountry() == null) {
              metadataObject.setCoverageCountry(element.getStringValue());
            } else {
              metadataObject.setCoverageCountry(metadataObject.getCoverageCountry() + ", " + element.getStringValue());
            }
            break;

          case "coverage.admin-unit":

            if (metadataObject.getCoverageAdminUnit() == null) {
              metadataObject.setCoverageAdminUnit(element.getStringValue());
            } else {
              metadataObject
                .setCoverageAdminUnit(metadataObject.getCoverageAdminUnit() + ", " + element.getStringValue());
            }
            break;

          case "coverage.geolocation":

            if (metadataObject.getCoverageGeolocation() == null) {
              metadataObject.setCoverageGeolocation(element.getStringValue());
            } else {
              metadataObject.setCoverageGeolocation(metadataObject.getCoverage() + ", " + element.getStringValue());
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

          case "format":
            if (metadataObject.getFormat() == null) {
              metadataObject.setFormat(element.getStringValue());
            }
            break;


          case "rights":
            if (metadataObject.getRigths() == null) {
              metadataObject.setRigths(element.getStringValue());
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
