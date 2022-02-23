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

import java.util.Optional;
import java.util.function.Function;

@Component
public class GetProductByIdFunction implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

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
        if(!requestEvent.getHttpMethod().equals(HttpMethod.GET.name())) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.METHOD_NOT_ALLOWED)
                    .withBody("Only GET method is supported");
        }
        try {
            String id = requestEvent.getPathParameters().get("id");
            Optional<Product> product = productDao.getProduct(id);
            if(product.isEmpty()) {
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
