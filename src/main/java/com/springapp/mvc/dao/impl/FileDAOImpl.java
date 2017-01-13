package com.springapp.mvc.dao.impl;

import com.springapp.mvc.dao.interfaces.FileDAO;
import com.springapp.mvc.domain.File;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * Created by Max on 11.01.2017.
 */
@Repository
@EnableTransactionManagement
public class FileDAOImpl implements FileDAO {

    @Autowired
    private SessionFactory sessionFactory; // session factory object

    public FileDAOImpl() {
    }

    public FileDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }

        return session;
    }

    @Override
    public File findById(Integer fileId) {
        return (File) getCurrentSession().get(File.class, fileId);
    }

    @Override
    public List<File> findAll() {
        List list = getCurrentSession().createQuery("from File").list();
        return list;
    }

    @Override
    public List<File> findFilesByObjectId(Integer objectId) {
        Query query = getCurrentSession().createQuery("from File where object_id=:objectId");
        List users = query.setParameter("objectId", objectId).list();

        return users;
    }

    @Override
    public void addFile(File file) {
        getCurrentSession().save(file);
    }

    @Override
    public void update(File file, Integer fileId) {
        File fileToUpdate = findById(fileId);

        if(fileToUpdate != null) {
            fileToUpdate.setName(file.getName());
            fileToUpdate.setUrl(file.getUrl());
            fileToUpdate.setType(file.getType());
            fileToUpdate.setDescription(file.getDescription());

            getCurrentSession().update(fileToUpdate);
        }

    }

    @Override
    public void remove(Integer fileId) {
        File file = findById(fileId);

        if(file != null) {
            getCurrentSession().delete(file);
        }

    }
}
