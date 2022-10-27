STACK_NAME=dagger-example

API_URL=$(aws cloudformation describe-stacks --stack-name $STACK_NAME \
  --query 'Stacks[0].Outputs[?OutputKey==`DaggerApiEndpoint`].OutputValue' \
  --output text)

artillery run load-test.yml --target "$API_URL"
