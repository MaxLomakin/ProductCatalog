package com.springapp.mvc.controller;

import com.springapp.mvc.dao.interfaces.ProductDAO;
import com.springapp.mvc.domain.Product;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by user on 8/5/2015.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    ProductDAO productDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        logger.debug(this.getClass().getName() + " index()");

        StringBuilder result = new StringBuilder();

        List<Product> all = productDAO.findAll();
        for (Product product : all) {
            result.append(product.getProductName() + " " );
        }

        model.addAttribute("message", result);
        return "category";
    }
}
