package org.cgiar.ccafs.ap.data.model;


public class ContactPerson {

  private int id;
  private String name;
  private String email;

  public ContactPerson() {
  }

  public ContactPerson(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

}
