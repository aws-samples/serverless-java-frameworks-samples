// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.product;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.xray.entities.TraceHeader;
import com.amazonaws.xray.entities.TraceID;
import com.amazonaws.xray.interceptors.TracingInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.nativex.hint.TypeHint;

import java.util.HashSet;

@SpringBootApplication
@TypeHint(types = {
  DateTime.class,
  APIGatewayProxyRequestEvent.class,
  TracingInterceptor.class,
  HashSet.class,
  TraceHeader.class,
  TraceID.class
},
  typeNames = {
    "com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent$ProxyRequestContext",
    "com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent$RequestIdentity",
    "com.amazonaws.xray.entities.TraceHeader$SampleDecision"
  })
public class SpringBootSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootSampleApplication.class, args);
  }

  @Bean
  @ConditionalOnMissingBean
  public ObjectMapper defaultObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }
}
