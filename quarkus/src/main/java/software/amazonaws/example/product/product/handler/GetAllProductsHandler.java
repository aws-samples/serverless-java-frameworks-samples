package software.amazonaws.example.product.product.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazonaws.example.product.product.dao.ProductDao;
import javax.inject.Inject;
import javax.inject.Named;

@Named("getAllProducts")
public class GetAllProductsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    ProductDao productDao;

    @Inject
    ObjectMapper objectMapper;

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        if(!requestEvent.getHttpMethod().equals(SdkHttpMethod.GET.name())) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.METHOD_NOT_ALLOWED)
                    .withBody("Only GET method is supported");
        }
        try {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.OK)
                    .withBody(objectMapper.writeValueAsString(productDao.getAllProduct()));
        } catch (Exception e) {
            e.printStackTrace();
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .withBody("Internal Server Error");
        }
    }
}
