package software.amazonaws.example.product;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;

import java.net.MalformedURLException;

public class FunctionLambdaRuntime extends AbstractMicronautLambdaRuntime<
  APIGatewayProxyRequestEvent,
  APIGatewayProxyResponseEvent,
  APIGatewayProxyRequestEvent,
  APIGatewayProxyResponseEvent> {

  public static void main(String[] args) {
    try {
      new FunctionLambdaRuntime().run(args);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
}