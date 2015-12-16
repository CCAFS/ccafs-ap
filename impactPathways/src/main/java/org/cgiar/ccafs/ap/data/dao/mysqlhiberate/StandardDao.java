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
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class StandardDao {


  private Session session;
  private Transaction tx;

  private SessionFactory sessionFactory;

  public void closeSession() {
    // Close caches and connection pools
    session.close();
  }


  public void CommitTransaction() {
    tx.commit();
  }

  protected void delete(Object obj) {
    try {
      this.getSession();
      this.InitTransaction();
      session.delete(obj);
      tx.commit();
    } catch (HibernateException e) {
      this.RollBackTransaction();
    } finally {
      this.closeSession();
    }
  }

  protected Object find(Class clazz, Object id) {
    Object obj = null;
    try {
      this.getSession();
      this.InitTransaction();
      obj = session.get(clazz, (Serializable) id);
      tx.commit();
    } catch (HibernateException e) {
      this.RollBackTransaction();
    } finally {
      this.closeSession();
    }
    return obj;
  }

  protected List findAll(Class clazz) {
    List objects = null;
    try {
      this.getSession();
      this.InitTransaction();
      Query query = session.createQuery("from " + clazz.getName());
      objects = query.list();
      tx.commit();
    } catch (HibernateException e) {
      this.RollBackTransaction();
    } finally {
      this.closeSession();
    }
    return objects;
  }

  public Session getSession() {


    SessionFactory sessionFactory =
      (SessionFactory) ServletActionContext.getServletContext().getAttribute(HibernateListener.KEY_NAME);

    session = sessionFactory.openSession();

    return session;
  }


  public Transaction InitTransaction() {
    tx = session.beginTransaction();
    return tx;
  }

  public void RollBackTransaction() {
    tx.rollback();
  }

  protected void saveOrUpdate(Object obj) {
    try {
      this.getSession();
      this.InitTransaction();
      session.saveOrUpdate(obj);
      this.CommitTransaction();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.closeSession();
    }
  }

}