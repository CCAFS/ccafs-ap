package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IndicatorType {

  private int id;


  private String name;

  private String messageError = "";

  public IndicatorType() {
  }

  public IndicatorType(int id, String name) {
    this.id = id;
    this.name = name;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    IndicatorType other = (IndicatorType) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }


  public int getId() {
    return id;
  }

  public String getMessageError() {
    return messageError;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMessageError(String messageError) {
    this.messageError = messageError;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
