package com.springapp.mvc.service.interfaces;

import com.springapp.mvc.domain.DataObject;

import java.util.List;

/**
 * Created by Max on 13.06.2016.
 */
public interface DataObjectService {
    DataObject findById(Integer objectId);
    DataObject findByName(String objectName);
    List<DataObject> findAll();
    void addObject(DataObject object);
    void update(DataObject object, Integer objectId);
    void saveOrUpdate(DataObject object);
    void remove(Integer objectId);
}
