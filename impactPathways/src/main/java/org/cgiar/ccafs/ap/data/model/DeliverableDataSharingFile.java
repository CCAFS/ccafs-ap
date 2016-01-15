package org.cgiar.ccafs.ap.data.model;
// Generated Jan 15, 2016 10:52:29 AM by Hibernate Tools 4.3.1.Final


/**
 * DeliverableDataSharingFile generated by hbm2java
 */
public class DeliverableDataSharingFile implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1383844353852418485L;
  private Integer id;
  private int deliverableId;
  private String file;

  public DeliverableDataSharingFile() {
  }


  public DeliverableDataSharingFile(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public DeliverableDataSharingFile(int deliverableId, String file) {
    this.deliverableId = deliverableId;
    this.file = file;
  }

  public int getDeliverableId() {
    return this.deliverableId;
  }

  public String getFile() {
    return this.file;
  }

  public Integer getId() {
    return this.id;
  }

  public void setDeliverableId(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public void setId(Integer id) {
    this.id = id;
  }


}

