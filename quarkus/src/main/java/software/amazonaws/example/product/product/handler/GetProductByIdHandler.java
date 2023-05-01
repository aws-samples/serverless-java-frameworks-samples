// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.product.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazonaws.example.product.product.dao.ProductDao;
import software.amazonaws.example.product.product.entity.Product;

import java.util.Optional;

@Named("getProductById")
public class GetProductByIdHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  @Inject
  ProductDao productDao;

  @Inject
  ObjectMapper objectMapper;

  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
    if (!requestEvent.getHttpMethod().equals(SdkHttpMethod.GET.name())) {
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(HttpStatusCode.METHOD_NOT_ALLOWED)
        .withBody("Only GET method is supported");
    }
    try {
      String id = requestEvent.getPathParameters().get("id");
      Optional<Product> product = productDao.getProduct(id);
      if (product.isEmpty()) {
        return new APIGatewayProxyResponseEvent()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withBody("Product with id = " + id + " not found");
      }
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(HttpStatusCode.OK)
        .withBody(objectMapper.writeValueAsString(product.get()));
    } catch (Exception e) {
      e.printStackTrace();
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withBody("Internal Server Error");
    }
  }
}
