package org.cgiar.ccafs.ap.data.model;
// Generated Jan 5, 2016 2:07:57 PM by Hibernate Tools 3.5.0.Final


/**
 * CaseStudieIndicators generated by hbm2java
 */
public class CaseStudieIndicators implements java.io.Serializable {


  private Integer id;
  private CasesStudies casesStudies;
  private int idIndicator;

  public CaseStudieIndicators() {
  }

  public CaseStudieIndicators(CasesStudies casesStudies, int idIndicator) {
    this.casesStudies = casesStudies;
    this.idIndicator = idIndicator;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public CasesStudies getCasesStudies() {
    return this.casesStudies;
  }

  public void setCasesStudies(CasesStudies casesStudies) {
    this.casesStudies = casesStudies;
  }

  public int getIdIndicator() {
    return this.idIndicator;
  }

  public void setIdIndicator(int idIndicator) {
    this.idIndicator = idIndicator;
  }


}
