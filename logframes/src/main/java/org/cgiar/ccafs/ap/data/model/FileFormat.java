package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class FileFormat {

  private int id;
  private String name;

  public FileFormat() {
  }

  public FileFormat(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FileFormat) {
      FileFormat ff = (FileFormat) obj;
      return ff.getId() == this.getId() && ff.getName().equals(this.getName());
    }
    return super.equals(obj);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return this.getId() + this.getName().hashCode();
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
