package com.springapp.mvc.controller;

import com.springapp.mvc.dao.interfaces.DataObjectDAO;
import com.springapp.mvc.domain.DataObject;
import com.springapp.mvc.domain.ObjectType;
import com.springapp.mvc.domain.Picture;
import com.springapp.mvc.service.interfaces.DataObjectService;
import com.springapp.mvc.service.interfaces.ObjectTypeService;
import com.springapp.mvc.service.interfaces.UserService;
import com.springapp.mvc.validator.ObjectFormValidator;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Max on 13.06.2016.
 */
@Controller
@RequestMapping("/objects")
public class ObjectController {

    private static final Logger logger = Logger.getLogger(ObjectController.class);

    @Autowired
    ObjectFormValidator objectFormValidator;

    @Autowired
    DataObjectService dataObjectService;

    @Autowired
    ObjectTypeService objectTypeService;

    @Autowired
    UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(objectFormValidator);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index(Model model) {
        logger.debug(this.getClass().getName() + " index()");

        model.addAttribute("_user", getPrincipal());
        return "redirect:/objects";
    }

    //list page
    @RequestMapping(method = RequestMethod.GET)
    public String showAllObjects(Model model) {
        logger.debug(this.getClass().getName() + " showAllUsers()");

        model.addAttribute("objects", dataObjectService.findAll());
        model.addAttribute("_user", getPrincipal());
        return "objects/list";
    }

    // save or update object
    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdateObject(@ModelAttribute("objectForm") @Validated DataObject dataObject,
                                   BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        logger.debug(this.getClass().getName() + " saveOrUpdateUser() : " + dataObject.getName() + " " + dataObject.getObjectId());

        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "objects/objectform";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            if(dataObject.isNew()) {
                redirectAttributes.addFlashAttribute("msg", "Object added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Object updated successfully!");
            }

            dataObjectService.saveOrUpdate(dataObject);
            model.addAttribute("_user", getPrincipal());

            // POST/REDIRECT/GET
            return "redirect:/objects/" + dataObject.getObjectId();
        }

    }

    //show add object form /objects/add
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddObjectForm(Model model) {
        logger.debug(this.getClass().getName() + " showAddUserForm()");

        DataObject dataObject = new DataObject();

        // set default value
        dataObject.setName("name");

        model.addAttribute("objectForm", dataObject);
        model.addAttribute("_user", getPrincipal());

        populateDefaultModel(model);

        return "objects/objectform";
    }

    //show update form /objects/{id}/update
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateObjectForm(@PathVariable("id") int id, Model model) {
        logger.debug(this.getClass().getName() + " showUpdateObjectForm() : " + id);

        DataObject object = dataObjectService.findById(id);
        model.addAttribute("objectForm", object);
        model.addAttribute("_user", getPrincipal());

        populateDefaultModel(model);

        return "objects/objectform";
    }

    //delete user /users/{id}/delete
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteObject(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        logger.debug(this.getClass().getName() + " deleteObject() : " + id);

        dataObjectService.remove(id);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");

        return "redirect:/objects";
    }

    //show objects /objects/{id}
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showObject(@PathVariable("id") int id, Model model) {
        logger.debug(this.getClass().getName() + " showObject() id: " + id);

        DataObject object = dataObjectService.findById(id);
        if (object == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "User not found");
        }
        model.addAttribute("object", object);
        model.addAttribute("_user", getPrincipal());

        return "objects/show";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {
        logger.debug(this.getClass().getName() + " handleEmptyData()");
        logger.error(this.getClass().getName() + " Request: " +  req.getRequestURL() + " error: " + ex);

        ModelAndView model = new ModelAndView();
        model.setViewName("objects/show");
        model.addObject("msg", "object not found");

        return model;

    }

//    @RequestMapping(method = RequestMethod.GET)
//    public String printWelcome(ModelMap model) {
//        logger.debug(this.getClass().getName() + " index()");
//
//        StringBuilder result = new StringBuilder();
//
//        List<DataObject> all = dataObjectDAO.findAll();
//        for (DataObject dataObject : all) {
//            result.append(dataObject.getName() + " " + dataObject.getObjectType().getName());
//
//            Picture picture = dataObject.getPicture();
//            if(picture != null) {
//                result.append(" <img src=" + picture.getUrl() + ">");
//            }
//        }
//
//        model.addAttribute("_user", getPrincipal());
//        model.addAttribute("message", result);
//        return "objects";
//    }

    //todo make configurable
    private void populateDefaultModel(Model model) {
        Map<String, ObjectType> objectTypesAttr = new LinkedHashMap<String, ObjectType>();
        List<ObjectType> objectTypes = objectTypeService.findAll();

        for (ObjectType objectType : objectTypes) {
            objectTypesAttr.put(objectType.getName(), objectType);
        }

        model.addAttribute("objectTypes", objectTypesAttr);
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
