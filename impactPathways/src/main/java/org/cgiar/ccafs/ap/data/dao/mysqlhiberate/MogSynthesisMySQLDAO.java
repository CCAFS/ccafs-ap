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


package org.cgiar.ccafs.ap.data.dao.mysqlhiberate;


import org.cgiar.ccafs.ap.data.dao.MogSynthesisDAO;
import org.cgiar.ccafs.ap.data.model.MogSynthesis;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MogSynthesisMySQLDAO implements MogSynthesisDAO {

  private static Logger LOG = LoggerFactory.getLogger(MogSynthesisMySQLDAO.class); // TODO To review

  private StandardDAO dao;

  @Inject
  public MogSynthesisMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public List<MogSynthesis> findMogSynthesis(int programId) {
    String sql = "from " + MogSynthesis.class.getName() + " where program_id=" + programId;
    List<MogSynthesis> list = dao.findAll(sql);
    return list;
  }

  @Override
  public List<MogSynthesis> findMogSynthesisRegion(int midoutcome) {
    String sql =
      "from " + MogSynthesis.class.getName() + " where mog_id=" + midoutcome + " and program_id not in (1,2,3,4)";
    List<MogSynthesis> list = dao.findAll(sql);
    return list;
  }

  @Override
  public int save(MogSynthesis mogSynthesis) {

    dao.saveOrUpdate(mogSynthesis);
    return mogSynthesis.getId();
  }
}