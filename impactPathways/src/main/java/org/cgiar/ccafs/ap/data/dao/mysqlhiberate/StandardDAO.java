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

import org.cgiar.ccafs.ap.config.HibernateListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Christian David García O. - CIAT/CCAFS
 * @author Héctor F. Tobón R. - CIAT/CCAFS
 */
public class StandardDAO {


  private Session session;
  private Transaction tx;

  private void closeSession() {
    // Close caches and connection pools
    session.close();
  }

  protected void commitTransaction() {
    tx.commit();
  }

  protected <T> List<T> customFindAll(String hibernateQuery) {

    List<T> list = new ArrayList<T>();
    try {
      this.getSession();
      this.initTransaction();
      Query query = session.createQuery(hibernateQuery);
      // query.l
      list.addAll(query.list());
      session.flush();
      this.commitTransaction();
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
    } finally {
      this.closeSession();
    }
    return list;
  }

  protected void delete(Object obj) {
    try {
      this.getSession();
      this.initTransaction();
      session.delete(obj);
      this.commitTransaction();
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
    } finally {
      this.closeSession();
    }
  }

  protected <T> T find(Class clazz, Object id) {
    T obj = null;
    try {
      this.getSession();
      this.initTransaction();
      obj = (T) session.get(clazz, (Serializable) id);
      session.flush();
      this.commitTransaction();
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
    } finally {
      this.closeSession();
    }
    return obj;
  }

  private Session getSession() {
    SessionFactory sessionFactory =
      (SessionFactory) ServletActionContext.getServletContext().getAttribute(HibernateListener.KEY_NAME);
    if (session == null || !session.isOpen()) {
      session = sessionFactory.openSession();
    }
    return session;
  }


  private void initTransaction() {
    tx = session.beginTransaction();
  }

  private void rollBackTransaction() {
    tx.rollback();
  }

  protected void saveOrUpdate(Object obj) {
    try {
      this.getSession();
      this.initTransaction();
      session.saveOrUpdate(obj);
      session.flush();
      this.commitTransaction();
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
    } finally {
      this.closeSession();
    }
  }

}