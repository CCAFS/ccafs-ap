package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPProgramManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPProgramManagerImpl.class)
public interface IPProgramManager {


  /**
   * Get all the ipElements existent in the database
   * 
   * @return an array of IPElement objects
   */
  public List<IPProgram> getProgramsTypeFlagship();

  /**
   * Get a list of IPElement objects corresponding to the given array of ids
   * 
   * @param ids - list of IPElement identifiers
   * @return a list of IPElement objects
   */
  public List<IPProgram> getProgramsTypeRegion();


}
