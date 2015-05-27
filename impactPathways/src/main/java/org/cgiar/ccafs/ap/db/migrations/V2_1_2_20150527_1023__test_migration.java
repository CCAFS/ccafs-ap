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

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;


/**
 * @author Hernán David Carvajal
 */

public class V2_1_2_20150527_1023__test_migration implements JdbcMigration {

  public void migrate(Connection connection) throws Exception {
    PreparedStatement statement =
      connection.prepareStatement("INSERT INTO persons (first_name, last_name) VALUES ('Obelix', 'DCB')");

    try {
      statement.execute();
    } finally {
      statement.close();
    }
  }
}
