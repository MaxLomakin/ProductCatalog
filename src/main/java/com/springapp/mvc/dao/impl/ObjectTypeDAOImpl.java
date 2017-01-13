package com.springapp.mvc.dao.impl;

import com.springapp.mvc.dao.interfaces.ObjectTypeDAO;
import com.springapp.mvc.domain.ObjectType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Max on 13.06.2016.
 */
@Repository
public class ObjectTypeDAOImpl implements ObjectTypeDAO {

    @Autowired
    private SessionFactory sessionFactory; // session factory object

    public ObjectTypeDAOImpl() {
    }

    public ObjectTypeDAOImpl(SessionFactory sessionFactory)
    {
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
    public List<ObjectType> findAll() {
        List list = getCurrentSession().createQuery("from ObjectType").list();
        return list;
    }

    @Override
    public ObjectType findById(Integer objectTypeId) {
        return (ObjectType) getCurrentSession().get(ObjectType.class, objectTypeId);
    }

}
