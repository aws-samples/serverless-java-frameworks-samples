// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product;

import com.amazonaws.xray.interceptors.TracingInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazonaws.example.product.dao.DynamoProductDao;
import software.amazonaws.example.product.dao.ProductDao;

import javax.inject.Singleton;

@Module
public class ProductModule {

  @Provides
  @Singleton
  public static DynamoDbClient dynamoDbClient() {
    return DynamoDbClient.builder()
      .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
      .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
      .overrideConfiguration(ClientOverrideConfiguration.builder()
        .addExecutionInterceptor(new TracingInterceptor())
        .build())
      .httpClient(UrlConnectionHttpClient.builder().build())
      .build();
  }

  @Provides
  @Singleton
  public static ObjectMapper objectMapper() {
      return new ObjectMapper();
  }

  @Provides
  @Singleton
  public static ProductDao orderDao(DynamoDbClient dynamoDbClient) {
    return new DynamoProductDao(dynamoDbClient);
  }
}
