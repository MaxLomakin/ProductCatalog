package com.springapp.mvc.dao.interfaces;

import com.springapp.mvc.domain.User;

import java.util.List;

/**
 * Created by Max on 08.11.2016.
 */
public interface UserDAO {
    User findById(Integer userId);
    User findByName(String userName);
    List<User> findAll();
    void addUser(User user);
    void update(User user, Integer userId);
    void remove(Integer userId);
}
