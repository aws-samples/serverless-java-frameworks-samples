# Serverless Dagger Application Demo

## Deployment

Deploy the demo to your AWS account using [AWS SAM](https://aws.amazon.com/serverless/sam/).

### Managed Runtime

```bash
mvn clean package
sam deploy -g
```
SAM will create an output of the API Gateway endpoint URL for future use in our load tests. 
Make sure the app name used here matches with the `STACK_NAME` present under `load-test/run-load-test.sh`

## Load Test

[Artillery](https://www.artillery.io/) is used to make 100 requests / second for 10 minutes to our API endpoints. You
can run this with the following command under `load-test` directory:

```bash
cd load-test
```

### Managed Runtime
> Before running load tests, make sure you update the stack name in [load test bash script](./load-test/run-load-test.sh)

```bash
./run-load-test.sh
```


This is a demanding load test, to change the rate alter the `arrivalRate` value in `load-test.yml`.

## CloudWatch Logs Insights

Using this CloudWatch Logs Insights query you can analyse the latency of the requests made to the Lambda functions.

The query separates cold starts from other requests and then gives you p50, p90 and p99 percentiles.

```
filter @type="REPORT"
| fields greatest(@initDuration, 0) + @duration as duration, ispresent(@initDuration) as coldStart
| stats count(*) as count, pct(duration, 50) as p50, pct(duration, 90) as p90, pct(duration, 99) as p99, max(duration) as max by coldStart
```

Latency for JVM version:
<p align="center">
  <img src="../imgs/dagger/dagger-sample-log-insights.png" alt="JVM Version Log Insights"/>
</p>

## AWS X-Ray Tracing
You can add additional detail to your X-Ray tracing by adding a TracingInterceptor to your AWS SDK clients.

Example cold start trace for JVM version:

<p align="center">
  <img src="../imgs/dagger/dagger-sample-cold-trace.png" alt="JVM Version Cold Trace Example"/>
</p>

Example warm start trace for JVM version:

<p align="center">
  <img src="../imgs/dagger/dagger-sample-warm-trace.png" alt="JVM Version Warm Trace Example"/>
</p>
