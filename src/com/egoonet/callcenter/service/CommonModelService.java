package com.egoonet.callcenter.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommonModelService
{

	protected SessionFactory sessionFactory;

	private static final int FIRST = 0;

	private String selectSQL = "";
	private String deleteSQL = "";
	private String deleteAllSQL = "";
	private String requestSQL = "";

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public void saveModel(Object model)
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(model);
			tx.commit();
		}
		catch ( HibernateException e )
		{
			if ( tx != null )
			{
				tx = null;
			}
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
	}

	public List<Object> getModels()
	{
		Session session = null;
		Transaction tx = null;
		List<Object> models = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(getSelectSQL());
			models = query.list();
			tx.commit();
		}
		catch ( HibernateException e )
		{
			if ( tx != null )
			{
				tx = null;
			}
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return models;
	}

	public Object getModel(int id)
	{
		Session session = null;
		Transaction tx = null;
		Object model = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(getRequestSql());
			query.setInteger("id", id);
			model = query.list().get(FIRST);
			tx.commit();
		}
		catch ( HibernateException e )
		{
			if ( tx != null )
			{
				tx = null;
			}
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return model;
	}

	public void deleteModel(int id)
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(getDeleteSQL());
			query.setInteger("id", id);
			query.executeUpdate();
			tx.commit();
		}
		catch ( HibernateException e )
		{
			if ( tx != null )
			{
				tx = null;
			}
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
	}

	public void deleteAllModel()
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(getDeleteAllSQL());
			query.executeUpdate();
			tx.commit();
		}
		catch ( HibernateException e )
		{
			if ( tx != null )
			{
				tx = null;
			}
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
	}

	public void updateModel(Object model)
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(model);
			tx.commit();
		}
		catch ( HibernateException e )
		{
			if ( tx != null )
			{
				tx = null;
			}
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
	}

	public String getSelectSQL()
	{
		return selectSQL;
	}

	public String getDeleteSQL()
	{
		return deleteSQL;
	}

	public String getDeleteAllSQL()
	{
		return deleteAllSQL;
	}

	public void setDeleteSQL(String deleteSQL)
	{
		this.deleteSQL = deleteSQL;
	};

	public void setDeleteAllSQL(String deleteAllSQL)
	{
		this.deleteAllSQL = deleteAllSQL;
	};

	public void setSelectSQL(String selectSQL)
	{
		this.selectSQL = selectSQL;
	};

	public String getRequestSql()
	{
		return requestSQL;
	}

	public void setRequestSql(String requestSQL)
	{
		this.requestSQL = requestSQL;
	}
}
