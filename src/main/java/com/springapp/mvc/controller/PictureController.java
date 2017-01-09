package com.springapp.mvc.controller;

import com.springapp.mvc.dao.interfaces.PictureDAO;
import com.springapp.mvc.domain.Picture;
import com.springapp.mvc.service.interfaces.UserService;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Max on 13.06.2016.
 */
@Controller
@RequestMapping("/pictures")
public class PictureController {

    private static final Logger logger = Logger.getLogger(PictureController.class);

    @Autowired
    PictureDAO pictureDAO;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        logger.debug(this.getClass().getName() + " index()");

        StringBuilder result = new StringBuilder();

        List<Picture> all = pictureDAO.findAll();
        for (Picture picture : all) {
            result.append("<img src=" + picture.getUrl() + " />");
        }

        model.addAttribute("_user", getPrincipal());
        model.addAttribute("message", result);
        return "pictures";
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
