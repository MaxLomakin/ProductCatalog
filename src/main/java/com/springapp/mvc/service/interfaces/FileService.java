package com.springapp.mvc.service.interfaces;

import com.springapp.mvc.domain.File;

import java.util.List;

/**
 * Created by Max on 11.01.2017.
 */
public interface FileService {
    File findById(Integer fileId);
    List<File> findAll();
    List<File> findFilesByObjectId(Integer objectId);
    void addFile(File file);
    void update(File file, Integer fileId);
    void remove(Integer fileId);
}
