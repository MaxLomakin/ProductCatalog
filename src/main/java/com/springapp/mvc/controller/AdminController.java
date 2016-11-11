package com.springapp.mvc.controller;

import org.apache.log4j.*;
import com.springapp.mvc.domain.Category;
import com.springapp.mvc.domain.Product;
import com.springapp.mvc.service.interfaces.CategoryService;
import com.springapp.mvc.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by user on 8/5/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @RequestMapping( method = RequestMethod.GET)
    public String getAdminPage(ModelMap model) {
        logger.debug(this.getClass().getName() + " index()");

        model.addAttribute("message1", "Add Category");
        model.addAttribute("message2", "Add Product");
        return "admin";
    }

    @RequestMapping(value = "/add-category",method = RequestMethod.POST)
    public String printAddCategory(ModelMap model,
                                  @RequestParam("category_name") String categoryName,
                                  @RequestParam("category_description") String categoryDescription) {
        logger.debug(this.getClass().getName() + " printAddCategory()");

        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryDescription(categoryDescription);
        categoryService.addCategory(category);

        model.addAttribute("message1", "Category Added");

        return "admin";
    }

    @RequestMapping(value = "add-product", method = RequestMethod.POST)
    public  String printAddProduct(ModelMap model,
                                   @RequestParam("product_name") String productName,
                                   @RequestParam("product_stock") String productStock,
                                   @RequestParam("product_price") String productPrice,
                                   @RequestParam("product_description" ) String productDescription) {
        logger.debug(this.getClass().getName() + " printAddProduct()");


        Product product = new Product(productName, Integer.valueOf(productStock),Double.valueOf(productPrice),productDescription);

        productService.addProduct(product);
        model.addAttribute("message2", "Product Added");
        return "admin";
    }

}

