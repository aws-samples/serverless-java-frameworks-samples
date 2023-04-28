// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.product.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;

@RegisterForReflection
public record Product(String id, String name, BigDecimal price) {

}
