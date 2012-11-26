package org.cgiar.ccafs.ap.data.model;


public class Logframe {

  private int id;
  private int year;
  private String name;

  public Logframe() {
  }

  public Logframe(int id, int year, String name) {
    super();
    this.id = id;
    this.year = year;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getYear() {
    return year;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setYear(int year) {
    this.year = year;
  }


}
