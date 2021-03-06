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
package org.cgiar.ccafs.ap.data.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * This class represents a CCAFS Product.
 * Each deliverable will have to belong a specific activity, and will be related to a specific MOG in the Impact
 * Pathway.
 *
 * @author Héctor Fabio Tobón R.
 */
public class Deliverable {

  private int id;
  private String title;
  private int year;
  private DeliverableType type;
  private String typeOther; // Other specific type defined by the user.
  private List<NextUser> nextUsers;
  private IPElement output; // Now it is called MOG.
  private DeliverablePartner responsiblePartner;
  private List<DeliverablePartner> otherPartners;
  private long created;
  private DeliverablesRanking ranking;
  private DeliverableDissemination dissemination;
  private DeliverableDataSharing dataSharing;
  private List<DeliverableDataSharingFile> dataSharingFile;
  private List<DeliverableFile> files;
  private DeliverablePublicationMetadata publicationMetadata;
  private List<DeliverableMetadataElements> metadataElements;
  private List<MetadataElements> metadata;

  private String statusDescription;


  private int status;


  public Deliverable() {
    super();
  }


  public Deliverable(int id) {
    this.id = id;
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Deliverable) {
      Deliverable v = (Deliverable) obj;
      return v.id == this.id;
    }
    return false;
  }


  public long getCreated() {
    return created;
  }


  public DeliverableDataSharing getDataSharing() {
    return dataSharing;
  }

  public List<DeliverableDataSharingFile> getDataSharingFile() {
    return dataSharingFile;
  }

  public DeliverableDissemination getDissemination() {
    return dissemination;
  }


  public List<DeliverableFile> getFiles() {
    return files;
  }

  public int getId() {
    return id;
  }


  public List<MetadataElements> getMetadata() {
    return metadata;
  }


  public List<DeliverableMetadataElements> getMetadataElements() {
    return metadataElements;
  }


  public int getMetadataID(String metadataName) {
    for (MetadataElements mData : metadata) {
      if (mData.getEcondedName().equals(metadataName)) {
        return mData.getId();
      }
    }
    return -1;
  }


  public int getMetadataIndex(String metadataName) {
    int c = 0;
    for (MetadataElements mData : metadata) {
      if (mData.getEcondedName().equals(metadataName)) {
        return c;
      }
      c++;
    }
    return -1;
  }

  public String getMetadataValue(int metadataID) {
    String value = "";
    for (DeliverableMetadataElements dmetadata : metadataElements) {
      if (dmetadata.getMetadataElement().getId() == metadataID) {
        value = dmetadata.getElementValue();
      }
    }

    return value;
  }

  public String getMetadataValue(String metadataName) {
    for (DeliverableMetadataElements mData : metadataElements) {
      if (mData.getMetadataElement().getElement().equals(metadataName)) {
        return mData.getElementValue();
      }
    }
    return "";
  }

  public String getMetadataValueByEncondedName(String metadataName) {
    for (DeliverableMetadataElements mData : metadataElements) {
      if (mData.getMetadataElement().getEcondedName().equals(metadataName)) {
        return mData.getElementValue();
      }
    }
    return "";
  }


  public List<NextUser> getNextUsers() {
    return nextUsers;
  }

  public List<DeliverablePartner> getOtherPartners() {
    return otherPartners;
  }


  public IPElement getOutput() {
    return output;
  }


  public DeliverablePublicationMetadata getPublicationMetadata() {
    return publicationMetadata;
  }


  public DeliverablesRanking getRanking() {
    return ranking;
  }


  public DeliverablePartner getResponsiblePartner() {
    return responsiblePartner;
  }


  public int getStatus() {
    return status;
  }


  public String getStatusDescription() {
    return statusDescription;
  }

  public String getTitle() {
    return title;
  }

  public DeliverableType getType() {
    return type;
  }

  public String getTypeOther() {
    return typeOther;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  /**
   * Check if it's a data deliverable type
   * 
   * @return true if deliverable type is Data else false
   */
  public boolean isDataType() {
    try {
      return this.getType().getCategory().getId() == 1;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Validate if the deliverable is new.
   * A deliverable is new when it was created in the planning phase for the current year
   * 
   * @param currentPlanningYear
   * @return true if the deliverable is new, false otherwise
   */
  public boolean isNew(int currentYear) {
    return year >= currentYear;
  }

  /**
   * Check if it's a other channel dissmination
   * 
   * @return true if dissmination channel is other else false
   */
  public boolean isOtherChannel() {
    try {
      return this.getDissemination().getDisseminationChannel().equals(ChannelEnum.OTHER.getId());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Check if it's a publication Peer-reviewed journal articles deliverable type and category
   * 
   * @return true if deliverable type is publication and Peer-reviewed journal articles category else false
   */
  public boolean isPublicationPeerReviewType() {
    try {
      return this.getType().getId() == 21 && this.getType().getCategory().getId() == 3;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Check if it's a publication deliverable type
   * 
   * @return true if deliverable type is publication else false
   */
  public boolean isPublicationType() {

    try {
      return this.getType().getCategory().getId() == 3;
    } catch (Exception e) {
      return false;
    }
  }


  /**
   * Check if deliverable status is "Cancelled"
   * 
   * @return true if deliverable status is "Cancelled" else false
   */
  public boolean isStatusCancelled() {
    try {
      // 5 - Cancelled - The activity has been cancelled.
      return status == 5;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isStatusComplete() {
    try {
      // 3- Complete
      return status == 3;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isStatusExtended() {
    try {
      // 4 - Extended
      return status == 4;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isStatusOnGoing() {
    try {
      // 2 - On Going
      return status == 2;
    } catch (Exception e) {
      return false;
    }
  }


  public void setCreated(long created) {
    this.created = created;
  }


  public void setDataSharing(DeliverableDataSharing dataSharing) {
    this.dataSharing = dataSharing;
  }

  public void setDataSharingFile(List<DeliverableDataSharingFile> dataSharingFile) {
    this.dataSharingFile = dataSharingFile;
  }


  public void setDissemination(DeliverableDissemination dissemination) {
    this.dissemination = dissemination;
  }

  public void setFiles(List<DeliverableFile> files) {
    this.files = files;
  }

  public void setId(int id) {
    this.id = id;
  }


  public void setMetadata(List<MetadataElements> metadata) {
    this.metadata = metadata;
  }


  public void setMetadataElements(List<DeliverableMetadataElements> metadataElements) {
    this.metadataElements = metadataElements;
  }


  public void setNextUsers(List<NextUser> nextUsers) {
    this.nextUsers = nextUsers;
  }

  public void setOtherPartners(List<DeliverablePartner> otherPartners) {
    this.otherPartners = otherPartners;
  }

  public void setOutput(IPElement output) {
    this.output = output;
  }

  public void setPublicationMetadata(DeliverablePublicationMetadata publicationMetadata) {
    this.publicationMetadata = publicationMetadata;
  }

  public void setRanking(DeliverablesRanking ranking) {
    this.ranking = ranking;
  }

  public void setResponsiblePartner(DeliverablePartner responsiblePartner) {
    this.responsiblePartner = responsiblePartner;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setType(DeliverableType type) {
    this.type = type;
  }

  public void setTypeOther(String typeOther) {
    this.typeOther = typeOther;
  }

  public void setYear(int year) {
    this.year = year;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
