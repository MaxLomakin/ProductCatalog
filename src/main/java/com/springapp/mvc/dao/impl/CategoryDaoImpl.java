package com.springapp.mvc.dao.impl;

import com.springapp.mvc.dao.interfaces.CategoryDAO;
import com.springapp.mvc.domain.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 8/5/2015.
 */
@Repository
public class CategoryDaoImpl implements CategoryDAO
{
    @Autowired
    private SessionFactory sessionFactory; // session factory object

    public CategoryDaoImpl() {
    }

    public CategoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }



    @Override
    public List<Category> findAll() {
        List list = getCurrentSession().createQuery("from Category").list();
        return list;
    }

    @Override

    public Category findById(Integer categoryId) {
        return (Category) getCurrentSession().load(Category.class, categoryId);
    }

    @Override

    public void addCategory(Category category) {
        getCurrentSession().saveOrUpdate(category);
    }

    @Override

    public void editCategory(Category category, Integer categoryId) {
        /**
         * get current category to edit
         */
        Category categoryToUpdate = findById(categoryId);
        /**
         * set new params to categoryToUpdate if not null
         */
        if (categoryToUpdate != null) {
            categoryToUpdate.setCategoryName(category.getCategoryName());
            categoryToUpdate.setCategoryDescription(category.getCategoryDescription());
            getCurrentSession().saveOrUpdate(categoryToUpdate);
        }
    }

    @Override
    public void removeCategory(Integer categoryId) {
        /**
         * find the category which will be removed, find by id category
         */
        Category categoryToDelete = findById(categoryId);
        /**
         * check whether there is this category
         */
        if(categoryToDelete != null) {
            getCurrentSession().delete(categoryToDelete);
        }
    }
}
