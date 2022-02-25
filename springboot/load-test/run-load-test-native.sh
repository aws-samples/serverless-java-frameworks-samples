STACK_NAME=springboot-sample-native

API_URL=$(aws cloudformation describe-stacks --stack-name $STACK_NAME \
  --query 'Stacks[0].Outputs[?OutputKey==`ApiEndpoint`].OutputValue' \
  --output text)

artillery run load-test.yml --target "$API_URL"
