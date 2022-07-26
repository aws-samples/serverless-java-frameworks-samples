// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.product.entity;

import java.util.List;

public class Products {
  private final List<Product> products;

  public Products(List<Product> products) {
    this.products = products;
  }

  public List<Product> getProducts() {
    return products;
  }
}

