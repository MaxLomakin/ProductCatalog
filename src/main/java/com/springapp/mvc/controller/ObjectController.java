package com.springapp.mvc.controller;

import com.springapp.mvc.dao.interfaces.DataObjectDAO;
import com.springapp.mvc.domain.DataObject;
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
@RequestMapping("/objects")
public class ObjectController {

    private static final Logger logger = Logger.getLogger(ObjectController.class);

    @Autowired
    DataObjectDAO dataObjectDAO;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        logger.debug(this.getClass().getName() + " index()");

        StringBuilder result = new StringBuilder();

        List<DataObject> all = dataObjectDAO.findAll();
        for (DataObject dataObject : all) {
            result.append(dataObject.getName() + " " + dataObject.getObjectType().getName());

            Picture picture = dataObject.getPicture();
            if(picture != null) {
                result.append(" <img src=" + picture.getUrl() + ">");
            }
        }

        model.addAttribute("_user", getPrincipal());
        model.addAttribute("message", result);
        return "objects";
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
