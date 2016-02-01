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


package org.cgiar.ccafs.ap.data.model;

/**
 * @author Christian David García.
 */
public enum ProjectStatusEnum {


  Implementation("2", "Implementation  (e.g. ongoing; incomplete)"), Completion("3", "Completion"),
  Cancelled("5", "Cancelled");

  private String status;
  private String statusId;

  private ProjectStatusEnum(String statusId, String status) {
    this.statusId = statusId;
    this.status = status;
  }


  public String getStatus() {
    return status;
  }


  public String getStatusId() {
    return statusId;
  }


  public void setStatusId(String statusId) {
    this.statusId = statusId;
  }


}
