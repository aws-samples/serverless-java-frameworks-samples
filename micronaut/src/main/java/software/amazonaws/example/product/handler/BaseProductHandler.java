// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.annotation.Nonnull;

/**
 * Refer <a href="https://docs.micronaut.io/latest/guide/#eagerInit">Eager Init of Singletons</a> for details
 */
@Introspected
public abstract class BaseProductHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  @Nonnull
  @Override
  protected ApplicationContextBuilder newApplicationContextBuilder() {
    ApplicationContextBuilder builder = super.newApplicationContextBuilder();
    builder.eagerInitSingletons(true);
    return builder;
  }
}
