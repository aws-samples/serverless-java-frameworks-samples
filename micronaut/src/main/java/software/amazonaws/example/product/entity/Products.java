// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.entity;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
public record Products(List<Product> products) {

}

