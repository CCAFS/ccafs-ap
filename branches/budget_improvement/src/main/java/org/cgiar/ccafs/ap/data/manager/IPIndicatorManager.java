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

import org.cgiar.ccafs.ap.data.manager.impl.IPIndicatorManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPIndicatorManagerImpl.class)
public interface IPIndicatorManager {

  /**
   * This method gets all the indicators related with the element passed
   * as parameter.
   * 
   * @param element - IP Element
   * @return a list of ip indicators object with the information.
   */
  public List<IPIndicator> getElementIndicators(IPElement element);

  /**
   * This method get the indicator identified by the value passed as parameter.
   * 
   * @param indicatorID - indicator identifier
   * @return the indicator object searched. Null if the indicator wasn't found.
   */
  public IPIndicator getIndicator(int indicatorID);

  /**
   * This method returns a list of indicators which have a parent
   * identified with the value passed as parameter.
   * 
   * @param indicator
   * @return a list of indicators object
   */
  public List<IPIndicator> getIndicatorsByParent(IPIndicator indicator);

  /**
   * This method get all the indicators present in the database.
   * 
   * @return a list of IPIndicator objects.
   */
  public List<IPIndicator> getIndicatorsList();

  /**
   * This method return a list of indicators which corresponds with the
   * list of identifiers received as parameter.
   * 
   * @param indicatorsIDs
   * @return a list of IPIndicator objects.
   */
  public List<IPIndicator> getIndicatorsList(String[] indicatorsIDs);

  /**
   * This method removes from the database the indicators which are related with the
   * ipProgram and ipElement passed as parameters
   * 
   * @param element
   * @param program
   * @return true if the indicators were removed successfully. False otherwise.
   */
  public boolean removeElementIndicators(IPElement element, IPProgram program);
}
