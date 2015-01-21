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

/**
 * @author Hernán David Carvajal
 */

package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;
import java.util.List;

public class Product extends Deliverable {

  private int id;
  private String description;
  private String descriptionUpdate;

  public Product() {
  }

  public Product(int id) {
    this.id = id;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getDescriptionUpdate() {
    return descriptionUpdate;
  }

  @Override
  public List<FileFormat> getFileFormats() {
    if (fileFormats != null) {
      return fileFormats;
    } else {
      return new ArrayList<>();
    }
  }

  public ArrayList<String> getFileFormatsIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getFileFormats().size(); c++) {
      ids.add(getFileFormats().get(c).getId() + "");
    }
    return ids;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void setDescriptionUpdate(String desriptionUpdate) {
    this.descriptionUpdate = desriptionUpdate;
  }

  @Override
  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }
}
