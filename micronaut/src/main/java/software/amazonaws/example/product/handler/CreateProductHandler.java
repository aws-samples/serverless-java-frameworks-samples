// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazonaws.example.product.dao.ProductDao;
import software.amazonaws.example.product.entity.Product;


@Introspected
public class CreateProductHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  @Inject
  ProductDao productDao;

  @Inject
  ObjectMapper objectMapper;

  public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent requestEvent) {
    if (!requestEvent.getHttpMethod().equals(SdkHttpMethod.PUT.name())) {
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
        .withBody("Internal Server Error");
    }
  }
}
