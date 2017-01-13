package com.springapp.mvc.service.impl;

import com.springapp.mvc.dao.interfaces.FileDAO;
import com.springapp.mvc.domain.File;
import com.springapp.mvc.service.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Max on 11.01.2017.
 */
public class FileServiceImpl implements FileService {

    @Autowired
    FileDAO fileDAO;

    @Override
    public File findById(Integer fileId) {
        return fileDAO.findById(fileId);
    }

    @Override
    public List<File> findAll() {
        return fileDAO.findAll();
    }

    @Override
    public List<File> findFilesByObjectId(Integer objectId) {
        return fileDAO.findFilesByObjectId(objectId);
    }

    @Override
    public void addFile(File file) {
        fileDAO.addFile(file);
    }

    @Override
    public void update(File file, Integer fileId) {
        fileDAO.update(file, fileId);
    }

    @Override
    public void remove(Integer fileId) {
        fileDAO.remove(fileId);
    }
}
