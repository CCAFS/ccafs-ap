package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPProgramManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPProgramManagerImpl.class)
public interface IPProgramManager {

  /**
   * This method gets from the database a list of ipElements
   * which have the same type as passed as parameter
   * 
   * @param ipProgramTypeID - IP Program type identifier
   * @return a list of IPProgram objects with the information.
   */
  public List<IPProgram> getProgramsByType(int ipProgramTypeID);

}
