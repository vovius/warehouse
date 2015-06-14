package com.dio.javamentoring.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.TypeLocatorImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.hibernate.type.EnumType;

public class HibernateTVStorage extends CommonTVStorage implements Serializable {
	
	private Session session;
	
	public HibernateTVStorage(String config) throws Exception {
		super(config,StorageType.HIBERNATE);
	}

	public TVStorage fillBySourceString(String sourceString) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Configuration configuration = new Configuration().configure(sourceString);
		session = configuration.buildSessionFactory().openSession();
		session.beginTransaction();

		List<TV> tvList = session.createCriteria(TV.class).list();
		
		goodsList = new ArrayList<TV>(tvList);
		return this;
	}

	public boolean saveBySourceString(String sourceString) throws Exception {
		for (TV item : goodsList) {
			session.merge(item);
		}
		//session.flush();
		session.getTransaction().commit();
		session.beginTransaction();
		return false;
	}

}
