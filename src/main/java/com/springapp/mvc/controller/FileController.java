package com.springapp.mvc.controller;

import com.springapp.mvc.domain.DataObject;
import com.springapp.mvc.domain.File;
import com.springapp.mvc.domain.FileBucket;
import com.springapp.mvc.domain.User;
import com.springapp.mvc.service.interfaces.DataObjectService;
import com.springapp.mvc.service.interfaces.FileService;
import com.springapp.mvc.service.interfaces.UserService;
import com.springapp.mvc.validator.FileValidator;
import java.net.URLEncoder;
import com.sun.jndi.toolkit.url.Uri;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Max on 11.01.2017.
 */
@Controller
@RequestMapping("/files")
public class FileController {

    private static final Logger logger = Logger.getLogger(UploadController.class);


    @Autowired
    DataObjectService objectService;

    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    FileValidator fileValidator;

    @InitBinder("fileBucket")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

    @RequestMapping(value = { "/{objectId}/manage" }, method = RequestMethod.GET)
    public String showDocuments(@PathVariable int objectId, ModelMap model) {
        DataObject object = objectService.findById(objectId);
        model.addAttribute("object", object);

        List<File> documents = fileService.findFilesByObjectId(objectId);
        model.addAttribute("files", documents);

        model.addAttribute("_user", getPrincipal());
        return "files/" + objectId + "manage";
    }

    @RequestMapping(value = { "/add/{objectId}" }, method = RequestMethod.GET)
    public String addDocuments(@PathVariable int objectId, ModelMap model) {
        DataObject object = objectService.findById(objectId);
        model.addAttribute("object", object);

        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);

        List<File> documents = fileService.findFilesByObjectId(objectId);
        model.addAttribute("files", documents);

        model.addAttribute("_user", getPrincipal());
        return "files/manage";
    }


    @RequestMapping(value = { "/download/{objectId}/{fileId}" }, method = RequestMethod.GET)
    public String downloadDocument(@PathVariable int objectId, @PathVariable int fileId, HttpServletResponse response) throws IOException {
        File file = fileService.findById(fileId);
        DataObject object = objectService.findById(objectId);
        response.setContentType(file.getType());

        byte[] content = getContent(file, object);

        response.setContentLength(content.length);
        response.setCharacterEncoding("UTF-8");

        String headerValue = URLEncoder.encode(file.getName(), "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + headerValue + "\"");

        FileCopyUtils.copy(content, response.getOutputStream());

        return "redirect:/files/add/" + objectId;
    }

    @RequestMapping(value = { "/delete/{objectId}/{fileId}" }, method = RequestMethod.GET)
    public String deleteDocument(@PathVariable int objectId, @PathVariable int fileId) {
        fileService.remove(fileId);
        return "redirect:/files/add/" + objectId;
    }

    @RequestMapping(value = { "/add/{objectId}" }, method = RequestMethod.POST)
    public String uploadDocument(@Valid FileBucket fileBucket, BindingResult result, ModelMap model, @PathVariable int objectId) throws IOException{
        if (result.hasErrors()) {
            System.out.println("validation errors");
            DataObject object = objectService.findById(objectId);
            model.addAttribute("object", object);

            List<File> documents = fileService.findFilesByObjectId(objectId);
            model.addAttribute("files", documents);

            return "files/manage";
        } else {
            DataObject object = objectService.findById(objectId);
            model.addAttribute("object", object);

            saveDocument(fileBucket, object);

            model.addAttribute("_user", getPrincipal());
            return "redirect:" + objectId;
        }
    }

    private void saveDocument(FileBucket fileBucket, DataObject object) throws IOException {
        File file = new File();

        MultipartFile multipartFile = fileBucket.getFile();

        file.setName(multipartFile.getOriginalFilename());
        file.setDescription(fileBucket.getDescription());
        file.setType(multipartFile.getContentType());

        String url = resolveUrl(multipartFile, object);
        file.setUrl(url);
        file.setObjectId(object.getObjectId());

        fileService.addFile(file);
    }

    private String resolveUrl(MultipartFile file, DataObject object) throws IOException {
        logger.debug(this.getClass().getName() + " resolveUrl()");

        String rootPath = System.getProperty("catalina.home");
        String dirPath = rootPath + "\\" + "uploads" + "\\" + object.getObjectId();

        java.io.File dir = new java.io.File(dirPath);

        if(!dir.exists()) {
            dir.mkdirs();
        }

        java.io.File serverFile = new java.io.File(dir + "\\" + file.getOriginalFilename());

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(file.getBytes());
        stream.close();

        logger.debug("Server file location=" + serverFile.getAbsolutePath());

        return serverFile.getAbsolutePath();
    }

    private byte[] getContent (final File file, DataObject object) throws IOException {
        String url = file.getUrl();
        if(url == null || url.isEmpty()) {
            return null;
        }

        String rootPath = System.getProperty("catalina.home");
        String dirPath = rootPath + "\\" + "uploads" + "\\" + object.getObjectId();

        java.io.File serverFile = new java.io.File(dirPath);

        java.io.File[] matchingFiles = serverFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(java.io.File dir, String name) {
                return name.startsWith(file.getName());
            }
        });

        if(matchingFiles.length > 0) {
            return Files.readAllBytes(matchingFiles[0].toPath());
        }

        return null;
    }

    private Object getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String name = ((UserDetails) principal).getUsername();
            return userService.findById(Integer.parseInt(name));
        }

        return "";
    }

}
