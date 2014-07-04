package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPElementManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPElementManagerImpl.class)
public interface IPElementManager {

  /**
   * Delete all IP Elements which belongs to the program given and which are of
   * the same type given.
   * 
   * @param program
   * @param type
   * @return true if the deletion process was successful.
   */
  public boolean deleteIPElements(IPProgram program, IPElementType type);

  /**
   * This method gets all the IPElements related to the IP program
   * given
   * 
   * @param program - Object with the program information
   * @return a list with IPElements
   */
  public List<IPElement> getIPElements(IPProgram program);

  /**
   * This method gets all the IPElements of the type given and which are
   * related to the IP program given.
   * 
   * @param program - IPProgram object
   * @param type - IPElementType object
   * @return a list of IPElements which fill the conditions.
   */
  public List<IPElement> getIPElements(IPProgram program, IPElementType type);

  /**
   * This method save into the database the information of the IPElements
   * 
   * @param elements - List of objects to save
   * @return true if all the information was successfully saved, false otherwise.
   */
  public boolean saveIPElements(List<IPElement> elements);
}
