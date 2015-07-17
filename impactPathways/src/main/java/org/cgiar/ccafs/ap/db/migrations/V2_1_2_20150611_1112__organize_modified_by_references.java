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

public class V2_1_2_20150611_1112__organize_modified_by_references implements JdbcMigration {

  private static Logger LOG = LoggerFactory.getLogger(V2_1_2_20150611_1112__organize_modified_by_references.class);

  @Override
  public void migrate(Connection connection) throws Exception {
    PreparedStatement dropKeyStatement = null, addKeyStatement = null, updateStatement = null;

    /*
     * Query to get the tables that references the employees table
     */
    StringBuilder query = new StringBuilder();
    query.append("SELECT table_name, constraint_name ");
    query.append("FROM information_schema.KEY_COLUMN_USAGE ");
    query.append("WHERE ");
    query.append("constraint_schema= DATABASE() ");
    query.append("AND column_name = 'modified_by' ");
    query.append("AND referenced_table_name = 'employees' ");

    PreparedStatement getTablesStatement = connection.prepareStatement(query.toString());

    try {
      ResultSet rs = getTablesStatement.executeQuery();
      while (rs.next()) {
        String tableName = rs.getString("table_name");
        String fkName = rs.getString("constraint_name");

        if (tableName.equals("activities") || tableName.equals("activity_partners")) {
          continue;
        }

        LOG.debug("Updating table {}.", tableName);
        /*
         * Drop the foreign key that reference to the table employees
         */
        query.setLength(0);
        query.append("ALTER TABLE ");
        query.append(tableName);
        query.append(" DROP FOREIGN KEY `");
        query.append(fkName);
        query.append("`;");

        dropKeyStatement = connection.prepareStatement(query.toString());
        dropKeyStatement.execute(query.toString());

        /*
         * Update the table values to reference the table users
         */
        query.setLength(0);
        query.append("UPDATE ");
        query.append(tableName);
        query.append(" SET modified_by = ");
        query.append(" (SELECT user_id FROM employees WHERE id = modified_by) ");
        updateStatement = connection.prepareStatement(query.toString());
        updateStatement.executeUpdate(query.toString());


        /*
         * Add the new foreign key that reference the table users
         */
        query.setLength(0);
        query.append("ALTER TABLE ");
        query.append(tableName);
        query.append(" ADD CONSTRAINT ");
        query.append(fkName.replace("employees", "users"));
        query.append(" FOREIGN KEY (`modified_by` ) ");
        query.append("REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE; ");

        addKeyStatement = connection.prepareStatement(query.toString());
        addKeyStatement.execute(query.toString());

        LOG.debug("Table {} was successfully updated", tableName);
      }
    } catch (SQLException e) {
      LOG.error("Error running the script.", e);
      throw e;
    } finally {
      getTablesStatement.close();
      if (dropKeyStatement != null) {
        dropKeyStatement.close();
      }
      if (addKeyStatement != null) {
        addKeyStatement.close();
      }
    }
  }
}
