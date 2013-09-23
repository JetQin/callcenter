package com.egoonet.callcenter.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractModelService
{

	protected SessionFactory sessionFactory;

	private static final int FIRST = 0;

	private String selectSQL = "";
	private String deleteSQL = "";
	private String deleteAllSQL = "";
	private String targetName = "";
	private String byName = "";
	private Logger log = Logger.getLogger(AbstractModelService.class);

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
		log.debug("[messagebody]=" + model.toString());
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
			if ( null != getTargetName() && !"".equals(getTargetName()) )
			{
				query.setString("targetName", getTargetName());
			}
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
			Query query = session.createQuery(getRequestSQL());
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

	public Object getModelByName(String type, String sname, String stype)
	{
		Session session = null;
		Transaction tx = null;
		Object model = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(getByName());

			query.setString("stattype", type);
			query.setString("subscribeName", sname);
			query.setString("subscribeType", stype);

			if ( query.list().size() > 0 )
			{
				model = query.list().get(FIRST);
			}
			else
			{
				model = null;
			}

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

	public String getTargetName()
	{
		return targetName;
	}

	public void setTargetName(String targetName)
	{
		this.targetName = targetName;
	}

	public String getByName()
	{
		return byName;
	}

	public void setByName(String byName)
	{
		this.byName = byName;
	}

	public List<String> getSubscribeName()
	{
		Session session = null;
		Transaction tx = null;
		List<String> models = null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(getSubscribeSQL());
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

	public abstract String getRequestSQL();

	public abstract String getSubscribeSQL();

}
