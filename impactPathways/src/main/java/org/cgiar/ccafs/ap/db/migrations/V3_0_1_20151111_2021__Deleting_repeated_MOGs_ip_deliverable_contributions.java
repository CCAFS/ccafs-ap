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

package org.cgiar.ccafs.ap.db.migrations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class V3_0_1_20151111_2021__Deleting_repeated_MOGs_ip_deliverable_contributions implements JdbcMigration {

  private static Logger LOG =
    LoggerFactory.getLogger(V3_0_1_20151111_2021__Deleting_repeated_MOGs_ip_deliverable_contributions.class);

  @Override
  public void migrate(Connection connection) throws Exception {
    Statement statement = connection.createStatement();

    StringBuilder query = new StringBuilder();
    query.append(
      "SELECT id, deliverable_id FROM ip_deliverable_contributions group by deliverable_id having count(*) > 1");

    ResultSet rs = statement.executeQuery(query.toString());

    int deliverableID, deliverableContributionID, result;

    while (rs.next()) {
      deliverableContributionID = rs.getInt(1);
      deliverableID = rs.getInt(2);
      query.setLength(0);
      query.append("DELETE FROM ip_deliverable_contributions WHERE deliverable_id = ");
      query.append(deliverableID);
      query.append(" AND id <> ");
      query.append(deliverableContributionID);
      Statement statement2 = connection.createStatement();
      result = statement2.executeUpdate(query.toString());
      statement2.close();
      if (result < 1) {
        throw new Exception("There was a problem trying to delete an ip_deliverable_contribution");
      }
    }
    rs.close();
    statement.close();

  }
}