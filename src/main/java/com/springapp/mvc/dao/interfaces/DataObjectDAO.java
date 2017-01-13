package com.springapp.mvc.dao.interfaces;

import com.springapp.mvc.domain.DataObject;

import java.util.List;

/**
 * Created by Max on 13.06.2016.
 */
public interface DataObjectDAO {
    DataObject findById(Integer objectId);
    DataObject findByName(String objectName);
    List<DataObject> findAll();
    void addObject(DataObject object);
    void update(DataObject object, Integer objectId);
    void remove(Integer objectId);
}
