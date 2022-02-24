// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import software.amazonaws.example.product.dao.ProductDao;

@Controller
public class DeleteProductController {

    private ProductDao productDao;

    public DeleteProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Delete("/products/{id}")
    public void deleteProduct(@PathVariable String id) {
        productDao.deleteProduct(id);
    }

}
