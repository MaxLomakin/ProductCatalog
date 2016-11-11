package com.springapp.mvc.controller;

import com.springapp.mvc.domain.Picture;
import com.springapp.mvc.service.interfaces.PictureService;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Created by Max on 05.11.2016.
 */

@Controller
@RequestMapping("/uploads")
public class UploadController {

    private static final Logger logger = Logger.getLogger(UploadController.class);

    @Autowired
    PictureService pictureService;

    private String uploadsStrorage = "/uploads";

    public void setUploadsStrorage(String uploadsStrorage) {
        this.uploadsStrorage = uploadsStrorage;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        logger.debug(this.getClass().getName() + " index()");

        return "uploads";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
        logger.debug(this.getClass().getName() + " getImageById()");

        Path filePath = getFilePath(imageName + ".png");

        byte[] bytes = Files.readAllBytes(filePath);
        if(bytes.length == 0) {
            //todo resource
            return Files.readAllBytes(getFilePath("404.png"));
        }
        return bytes;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void handleUpload(@RequestParam("file") MultipartFile file) throws IOException {
        logger.debug(this.getClass().getName() + " upload()");

        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();

            Path outputFilePath = getFilePath(name);

            try {
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, outputFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                String marker = "a";
                String a = marker;
            }


            String imageUrl = getFileUrl();
            Picture picture = new Picture(imageUrl + "/" + name, name);
            pictureService.addPicture(picture);
        }
    }

    protected Path getFilePath(String fileName) throws IOException {
        logger.debug(this.getClass().getName() + " getFilePath()");

        File directory = new File(new File("").getAbsolutePath().replace('\\', '/') + uploadsStrorage + "/");
        if(!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory + "/" + fileName);
        if(!file.exists()) {
            file.createNewFile();
        }

        logger.debug(this.getClass().getName() + " filepath=" + file.getAbsolutePath());
        return file.toPath();
    }

    protected String getFileUrl () {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURL().toString();
    }

}


