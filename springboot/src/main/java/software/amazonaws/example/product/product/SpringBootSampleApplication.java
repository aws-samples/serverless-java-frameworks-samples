// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.product;

import org.joda.time.DateTime;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import software.amazonaws.example.product.product.entity.Product;
import software.amazonaws.example.product.product.entity.Products;


@SpringBootApplication
@RegisterReflectionForBinding({DateTime.class, APIGatewayProxyRequestEvent.class, Product.class, Products.class})

public class SpringBootSampleApplication {

    public static void main(String[] args) {
      SpringApplication.run(SpringBootSampleApplication.class, args);
    }
    
  }
  
