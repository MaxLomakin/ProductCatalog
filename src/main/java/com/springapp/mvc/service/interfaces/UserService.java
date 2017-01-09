package com.springapp.mvc.service.interfaces;

import com.springapp.mvc.domain.User;

import java.util.List;

/**
 * Created by Max on 09.11.2016.
 */
public interface UserService {
    User findById(Integer userId);
    User findByName(String userName);
    List<User> findAll();
    void addUser(User user);
    void saveOrUpdate(User user);
    void remove(Integer userId);
}
