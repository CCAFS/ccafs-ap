package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This object represents a specific user with a specific role inside an institution.
 * 
 * @author Javier Andrés Gallego 
 * @author Héctor Fabio Tobón R.
 *
 */
public class Employee {

  private int id;
  private User user;
  //private Institution institution;
  //private Role role;

  public Employee() {
	  
  }
  
  public Employee(int id, User user) {
    this.id = id;
    this.user = user;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
	return user;
  }

  public void setUser(User user) {
	this.user = user;
  }

@Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}