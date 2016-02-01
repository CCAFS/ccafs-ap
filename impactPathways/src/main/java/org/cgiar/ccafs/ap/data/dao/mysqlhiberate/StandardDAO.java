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

import com.google.inject.Singleton;
import org.apache.struts2.ServletActionContext;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Christian David García O. - CIAT/CCAFS
 * @author Héctor F. Tobón R. - CIAT/CCAFS
 */
@Singleton
public class StandardDAO {

  private Session session;
  private Transaction tx;
  private SessionFactory sessionFactory;

  public StandardDAO() {
    this.sessionFactory =
      (SessionFactory) ServletActionContext.getServletContext().getAttribute(HibernateListener.KEY_NAME);
  }

  /**
   * This method closes the session to the database.
   */
  private void closeSession() {
    // Close caches and connection pools
    session.close();
  }

  /**
   * This method commit the changes to hibernate table (in memory) but does not synchronize the changes to the database
   * engine.
   */
  private void commitTransaction() {
    tx.commit();
  }

  /**
   * This method deletes a record from the database.
   * 
   * @param obj is a persistence instance from the database model.
   * @return true if the record was successfully deleted, false otherwhise.
   */
  protected boolean delete(Object obj) {
    try {
      this.openSession();
      this.initTransaction();
      session.delete(obj);
      this.commitTransaction();
      return true;
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
      return false;
    } finally {
      session.flush(); // Flushing the changes always.
      this.closeSession();
    }
  }

  /**
   * This method finds a specific record from the database and transform it to a database model object.
   * 
   * @param clazz represents the class of the database model object.
   * @param id is the record identifier.
   * @return the object populated.
   */
  protected <T> T find(Class<T> clazz, Object id) {
    T obj = null;
    try {
      this.openSession();
      this.initTransaction();
      obj = session.get(clazz, (Serializable) id);
      this.commitTransaction();
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
    } finally {
      session.flush(); // Flushing the changes always.
      this.closeSession();
    }
    return obj;
  }

  /**
   * This method make a query that returns a list of objects from the model.
   * This method was implemented in a generic way, so, the list of objects to be returned will depend on how the method
   * is being called.
   * e.g:
   * List<SomeObject> list = this.findAll("some hibernate query");
   * or
   * this.<SomeObject>findAll("some hibernate query");
   * 
   * @param hibernateQuery is a string representing an HQL query.
   * @return a list of <T> objects.
   */
  protected <T> List<T> findAll(String hibernateQuery) {
    try {
      this.openSession();
      this.initTransaction();
      this.commitTransaction();
      Query query = session.createQuery(hibernateQuery);
      @SuppressWarnings("unchecked")
      List<T> list = query.list();

      return list;
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
      return null;
    } finally {
      session.flush(); // Flushing the changes always.
      this.closeSession();
    }
  }

  /**
   * This method initializes a transaction.
   */
  private void initTransaction() {
    tx = session.beginTransaction();
  }


  /**
   * This method opens a session to the database.
   * 
   * @return a Session object.
   */
  private Session openSession() {
    if (session == null || !session.isOpen()) {
      session = sessionFactory.openSession();
      // Calling flush when committing change.
      session.setFlushMode(FlushMode.COMMIT);
    }
    return session;
  }

  /**
   * This method tries to roll back the changes in case they were not flushed.
   */
  private void rollBackTransaction() {
    tx.rollback();
  }

  /**
   * This method saves or update a record into the database.
   * 
   * @param obj is the Object to be saved/updated.
   * @return true if the the save/updated was successfully made, false otherwhise.
   */
  protected boolean saveOrUpdate(Object obj) {
    try {
      this.openSession();
      this.initTransaction();
      session.saveOrUpdate(obj);
      this.commitTransaction();
      return true;
    } catch (HibernateException e) {
      this.rollBackTransaction();
      e.printStackTrace();
      return false;
    } finally {
      session.flush();
      this.closeSession();
    }
  }

}