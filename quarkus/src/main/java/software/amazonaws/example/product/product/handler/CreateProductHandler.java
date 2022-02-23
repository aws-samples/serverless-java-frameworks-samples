package software.amazonaws.example.product.product.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazonaws.example.product.product.dao.ProductDao;
import software.amazonaws.example.product.product.entity.Product;

import javax.inject.Inject;
import javax.inject.Named;

@Named("createProduct")
public class CreateProductHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    ProductDao productDao;

    @Inject
    ObjectMapper objectMapper;

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        if(!requestEvent.getHttpMethod().equals(SdkHttpMethod.PUT.name())) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.METHOD_NOT_ALLOWED)
                    .withBody("Only PUT method is supported");
        }
        try {
            String id = requestEvent.getPathParameters().get("id");
            String jsonPayload = requestEvent.getBody();
            Product product = objectMapper.readValue(jsonPayload,Product.class);
            if(!product.getId().equals(id)) {
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
