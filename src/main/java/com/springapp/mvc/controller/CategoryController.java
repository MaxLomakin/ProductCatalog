package com.springapp.mvc.controller;

import org.apache.log4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by user on 8/5/2015.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        logger.debug(this.getClass().getName() + " index()");

        model.addAttribute("message", "Category page!");
        return "category";
    }
}
