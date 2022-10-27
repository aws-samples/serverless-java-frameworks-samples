// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.handler;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazonaws.example.product.dao.ProductDao;
import software.amazonaws.example.product.entity.Product;
import software.amazonaws.example.product.DaggerProductComponent;

import javax.inject.Inject;
import java.util.Optional;

public class GetProductByIdHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  @Inject
  ProductDao productDao;
  @Inject
  ObjectMapper objectMapper;

  public GetProductByIdHandler() {
    DaggerProductComponent.builder().build().inject(this);
  }

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
    if (!requestEvent.getHttpMethod().equals(HttpMethod.GET.name())) {
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
    } catch (Exception je) {
      je.printStackTrace();
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withBody("Internal Server Error :: " + je.getMessage());
    }
  }
}
