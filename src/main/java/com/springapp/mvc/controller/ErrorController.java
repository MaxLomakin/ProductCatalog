package com.springapp.mvc.controller;

import org.apache.log4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Max on 07.11.2016.
 */
@Controller
@RequestMapping("/404")
public class ErrorController {

    private static final Logger logger = Logger.getLogger(ErrorController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String printError (ModelMap model) {
        logger.debug(this.getClass().getName() + " index()");

        return "404";
    }

}
