package com.springapp.mvc.service.impl;

import com.springapp.mvc.dao.interfaces.DataObjectDAO;
import com.springapp.mvc.domain.DataObject;
import com.springapp.mvc.service.interfaces.DataObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Max on 13.06.2016.
 */
@Service
@Transactional
public class DataObjectServiceImpl implements DataObjectService {

    @Autowired
    private DataObjectDAO dataObjectDAO;

    @Override
    @Transactional
    public List<DataObject> findAll() {
        return dataObjectDAO.findAll();
    }

    @Override
    @Transactional
    public DataObject findById(Integer dataObjectId) {
        return dataObjectDAO.findById(dataObjectId);
    }

    @Override
    @Transactional
    public DataObject findByName(String userName) {
        return dataObjectDAO.findByName(userName);
    }

    @Override
    public void addObject(DataObject object) {
        dataObjectDAO.addObject(object);
    }

    @Override
    public void update(DataObject object, Integer objectId) {
        dataObjectDAO.update(object, objectId);
    }

    @Override
    @Transactional
    public void saveOrUpdate(DataObject dataObject) {
        if (dataObject.getObjectId() == null || findById(dataObject.getObjectId()) == null) {
            dataObjectDAO.addObject(dataObject);
        } else {
            dataObjectDAO.update(dataObject, dataObject.getObjectId());
        }
    }

    @Override
    public void remove(Integer objectId) {
        dataObjectDAO.remove(objectId);
    }
}
