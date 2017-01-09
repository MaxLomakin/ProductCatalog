package com.springapp.mvc.service.impl;

import com.springapp.mvc.dao.interfaces.UserDAO;
import com.springapp.mvc.domain.User;
import com.springapp.mvc.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Max on 09.11.2016.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public User findById(Integer userId) {
        return userDAO.findById(userId);
    }

    @Override
    @Transactional
    public User findByName(String userName) {
        return userDAO.findByName(userName);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    @Transactional
    public void saveOrUpdate(User user) {
        if (user.getId() == null || findById(user.getId()) == null) {
            userDAO.addUser(user);
        } else {
            userDAO.update(user, user.getId());
        }
    }

    @Override
    public void remove(Integer userId) {
        userDAO.remove(userId);
    }
}
