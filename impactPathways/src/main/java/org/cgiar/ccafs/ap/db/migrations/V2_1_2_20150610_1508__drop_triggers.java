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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class V2_1_2_20150610_1508__drop_triggers implements JdbcMigration {

  private static Logger LOG = LoggerFactory.getLogger(V2_1_2_20150610_1508__drop_triggers.class);

  @Override
  public void migrate(Connection connection) throws Exception {
    PreparedStatement dropTriggerStatement = null;

    /*
     * Query to get the triggers of the database
     */
    StringBuilder query = new StringBuilder();
    query.append("SELECT trigger_name ");
    query.append("FROM information_schema.TRIGGERS ");
    query.append("WHERE ");
    query.append("trigger_schema= DATABASE() ");

    PreparedStatement getTablesStatement = connection.prepareStatement(query.toString());

    try {
      ResultSet rs = getTablesStatement.executeQuery();
      while (rs.next()) {
        String triggerName = rs.getString("trigger_name");

        /*
         * Query to drop the triggers
         */
        query.setLength(0);
        query.append("DROP TRIGGER ");
        query.append(triggerName);

        dropTriggerStatement = connection.prepareStatement(query.toString());
        dropTriggerStatement.executeUpdate();

        LOG.info("Trigger {} was successfully removed", triggerName);
      }
    } catch (SQLException e) {
      LOG.error("Error running the script.", e);
      throw e;
    } finally {
      getTablesStatement.close();
      if (dropTriggerStatement != null) {
        dropTriggerStatement.close();
      }
    }
  }
}
