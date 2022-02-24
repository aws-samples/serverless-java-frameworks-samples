// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazonaws.example.product.dao.ProductDao;
import software.amazonaws.example.product.entity.Products;

@Controller
public class GetAllProductsController {

    private static final Logger logger = LoggerFactory.getLogger(GetAllProductsController.class);
    private ProductDao productDao;

    public GetAllProductsController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Get("/products")
    public Products getAllProducts() {
        logger.info("Entering GetProducts Method !!");
        return productDao.getAllProduct();
    }

}
