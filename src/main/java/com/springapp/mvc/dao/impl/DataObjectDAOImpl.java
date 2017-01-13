package com.springapp.mvc.dao.impl;

import com.springapp.mvc.dao.interfaces.DataObjectDAO;
import com.springapp.mvc.domain.DataObject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Max on 13.06.2016.
 */
@Repository
public class DataObjectDAOImpl implements DataObjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public DataObjectDAOImpl() {
    }

    public DataObjectDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    private Session getCurrentSession()
    {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }

        return session;
    }

    @Override
    public List<DataObject> findAll() {
        List list = getCurrentSession().createQuery("from DataObject").list();
        return list;
    }

    @Override
    public DataObject findById(Integer objectId) {
        return (DataObject) getCurrentSession().get(DataObject.class, objectId);
    }

    @Override
    public DataObject findByName(String objectName) {
        Query query = getCurrentSession().createQuery("from DataObject where name=:objectName");
        List objects = query.setParameter("objectName", objectName).list();

        return (DataObject) objects.iterator().next();
    }

    @Override
    public void addObject(DataObject object) {
        getCurrentSession().save(object);
    }

    @Override
    public void update(DataObject object, Integer objectId) {
        DataObject dataObject = findById(objectId);

        if(dataObject != null) {
            dataObject.setName(object.getName());
            dataObject.setDescription(object.getDescription());
            dataObject.setObjectType(object.getObjectType());
            dataObject.setPicture(object.getPicture());

            getCurrentSession().update(dataObject);
        }
    }

    @Override
    public void remove(Integer objectId) {
        DataObject object = findById(objectId);

        if (object != null) {
            getCurrentSession().delete(object);
        }
    }
}
