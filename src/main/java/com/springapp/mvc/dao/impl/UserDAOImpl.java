package com.springapp.mvc.dao.impl;

import com.springapp.mvc.dao.interfaces.UserDAO;
import com.springapp.mvc.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * Created by Max on 08.11.2016.
 */

@Repository
@EnableTransactionManagement
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory; // session factory object

    public UserDAOImpl() {
    }

    public UserDAOImpl(SessionFactory sessionFactory) {
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
    public User findById(Integer userId) {
        return (User) getCurrentSession().get(User.class, userId);
    }

    @Override
    public User findByName(String userName) {
        Query query = getCurrentSession().createQuery("from User where name=:userName");
        List users = query.setParameter("userName", userName).list();

        return (User) users.iterator().next();
    }

    @Override
    public List<User> findAll() {
        List list = getCurrentSession().createQuery("from User").list();
        return list;
    }

    @Override
    public void addUser(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public void update(User user, Integer userId) {
        User userToUpdate = findById(userId);

        if(user != null) {
            userToUpdate.setName(user.getName());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setConfirmPassword(user.getConfirmPassword());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setGrants(user.getGrants());
            userToUpdate.setSex(user.getSex());

            getCurrentSession().update(userToUpdate);
        }
    }

    @Override
    public void remove(Integer userId) {
        User user = findById(userId);

        if(user != null) {
            getCurrentSession().delete(user);
        }
    }
}
