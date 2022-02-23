package software.amazonaws.example.product.product.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazonaws.example.product.product.dao.ProductDao;
import java.util.function.Function;

@Component
public class GetAllProductsFunction implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

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
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.OK)
                    .withBody(objectMapper.writeValueAsString(productDao.getAllProduct()));
        } catch (JsonProcessingException je) {
            je.printStackTrace();
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .withBody("Internal Server Error");
        }
    }

//    protected void propagateTraceId(Map<String, String> headers) {
//        String traceId = headers.get("X-Amzn-Trace-Id");
//        if (!StringUtils.isNullOrEmpty(traceId)) {
//            System.setProperty("com.amazonaws.xray.traceHeader", traceId);
//        }
//
//    }
}
