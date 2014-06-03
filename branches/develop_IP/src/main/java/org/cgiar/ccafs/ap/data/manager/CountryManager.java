package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CountryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(CountryManagerImpl.class)
public interface CountryManager {

  /**
   * Get all the countries that belongs to the region given
   * 
   * @param regionID - Region identifier
   * @return a list of Country objects with the information.
   */
  public Country[] getCountriesByRegion(String regionID);

  /**
   * Get the country object corresponding to the given id
   * 
   * @param id - The country identifier
   * @return An object with the country information or null if no data found
   */
  public Country getCountry(String id);


  /**
   * Get the countries list.
   * 
   * @return an array of Country objects or null if no data found
   */
  public Country[] getCountryList();

  /**
   * Get a list of countries object corresponding to the given array of ids
   * 
   * @param ids - Array of country identifiers
   * @return a list of Country objects
   */
  public List<Country> getCountryList(String[] ids);
}
