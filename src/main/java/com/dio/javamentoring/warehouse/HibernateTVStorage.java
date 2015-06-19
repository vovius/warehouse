package com.dio.javamentoring.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.TypeLocatorImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.hibernate.usertype.UserType;

public class HibernateTVStorage extends CommonTVStorage implements Serializable {
	
	private SessionFactory sessionFactory;
	
	public HibernateTVStorage(String config) throws Exception {
		super(config,StorageType.HIBERNATE);
	}

	public TVStorage fillBySourceString(String sourceString) throws Exception {
		Class.forName("org.sqlite.JDBC");
	    Configuration configuration = new Configuration().configure(sourceString);
	    //configuration.registerTypeOverride( new DateFromStringType(), new String[] {DateFromStringType.class.getName()} );
		//configuration.addAnnotatedClass(TV.class);
	    sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();

		List<TV> tvList = session.createCriteria(TV.class).list();
		
		session.flush();
		session.close();
		
		goodsList = new ArrayList<TV>(tvList);
		return this;
	}

	public boolean saveBySourceString(String sourceString) throws Exception {
		Transaction trans = null;
		Session session = null;
		for (TV item : goodsList) {
			try {
				session = sessionFactory.openSession();
				trans = session.beginTransaction();
				TV itemSave = (TV)session.merge(item);
				session.saveOrUpdate(itemSave);
				session.flush();
				trans.commit();
				session.clear();
				session.disconnect();
				//session.close();
			} catch (Exception e) {
				trans.rollback();
				if (session != null)
					session.close();
				e.printStackTrace();
			}
		}
		
		
		return false;
	}

}
