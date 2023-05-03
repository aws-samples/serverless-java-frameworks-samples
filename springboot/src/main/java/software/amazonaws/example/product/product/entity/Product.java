// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.product.entity;

import java.math.BigDecimal;

public record Product(String id, String name, BigDecimal price) {
}
