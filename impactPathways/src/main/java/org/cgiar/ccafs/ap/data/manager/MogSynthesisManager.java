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

import org.cgiar.ccafs.ap.data.manager.impl.MogSynthesisManagerImpl;
import org.cgiar.ccafs.ap.data.model.MogSynthesis;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(MogSynthesisManagerImpl.class)
public interface MogSynthesisManager {


  /**
   * This method gets all the OutcomeSynthesis information by a given program id
   * 
   * @param projectID - is the Id of the project
   * @return a List of OutcomeSynthesis with the Information related with the programId
   */
  public List<MogSynthesis> getMogSynthesis(int programId);

  /**
   * This method gets all the MogSynthesis information by a given program id
   * 
   * @param projectID - is the Id of the project
   * @return a List of MogSynthesis with the Information related with the programId
   */
  public List<MogSynthesis> getMogSynthesisRegions(int midoutcome);

  /**
   * This method saves the information of the given MogSynthesis
   */
  public int saveMogSynthesis(MogSynthesis synthesis);


}
