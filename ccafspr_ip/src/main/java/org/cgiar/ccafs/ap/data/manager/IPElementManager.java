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
   * This method delete the relation between the ipElement given and the ipProgram
   * passed as parameter.
   * If the parameter program is the same as element.getProgram then the IPElement
   * is also deleted.
   * 
   * @param element
   * @param program
   * @return true if the relation between the program and the element was successfully
   *         deleted, false otherwise.
   */
  public boolean deleteIPElement(IPElement element, IPProgram program);

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
   * Get all the ipElements existent in the database
   * 
   * @return an array of IPElement objects
   */
  public List<IPElement> getIPElementList();

  /**
   * Get a list of IPElement objects corresponding to the given array of ids
   * 
   * @param ids - list of IPElement identifiers
   * @return a list of IPElement objects
   */
  public List<IPElement> getIPElementList(String[] ids);

  /**
   * This method return all the impact pathways elements
   * setted with the basic information id, description,
   * translatedOf and contributesTo
   * 
   * @return
   */
  public List<IPElement> getIPElementListForGraph();

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
   * This method gets all the elements that are children of the element
   * passed as parameter.
   * 
   * @param parent
   * @return a list of IPElements objects with the information
   */
  public List<IPElement> getIPElementsByParent(IPElement parent, int relationTypeID);

  /**
   * This method save into the database the information of the IPElements
   * 
   * @param elements - List of objects to save
   * @return true if all the information was successfully saved, false otherwise.
   */
  public boolean saveIPElements(List<IPElement> elements);
}
