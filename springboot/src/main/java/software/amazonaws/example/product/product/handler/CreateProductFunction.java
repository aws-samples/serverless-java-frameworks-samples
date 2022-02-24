// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.product.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazonaws.example.product.product.dao.ProductDao;
import software.amazonaws.example.product.product.entity.Product;

import java.util.function.Function;

@Component
public class CreateProductFunction implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  ProductDao productDao;
  ObjectMapper objectMapper;

  @Autowired
  public void setProductDao(ProductDao productDao) {
    this.productDao = productDao;
  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent requestEvent) {
    if (!requestEvent.getHttpMethod().equals(HttpMethod.PUT.name())) {
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(HttpStatusCode.METHOD_NOT_ALLOWED)
        .withBody("Only PUT method is supported");
    }
    try {
      String id = requestEvent.getPathParameters().get("id");
      String jsonPayload = requestEvent.getBody();
      Product product = objectMapper.readValue(jsonPayload, Product.class);
      if (!product.getId().equals(id)) {
        return new APIGatewayProxyResponseEvent()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withBody("Product ID in the body does not match path parameter");
      }
      productDao.putProduct(product);
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(HttpStatusCode.CREATED)
        .withBody("Product with id = " + id + " created");
    } catch (Exception e) {
      e.printStackTrace();
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withBody("Internal Server Error :: " + e.getMessage());
    }
  }
}
