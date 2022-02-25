# Serverless Spring Boot Application Demo

## Deployment

Deploy the demo to your AWS account using [AWS SAM](https://aws.amazon.com/serverless/sam/).

### Option 1: Managed Runtime

```bash
mvn clean package
sam deploy -g
```
SAM will create an output of the API Gateway endpoint URL for future use in our load tests. 
Make sure the app name used here matches with the `STACK_NAME` present under `load-test/run-load-test.sh`

### Option 2: GraalVM Native Image

On MacOS:
```bash
docker run --mount type=bind,source=$(pwd),destination=/project -it --entrypoint /bin/bash marksailes/al2-graalvm:11-22.0.0.2
```

On Windows:
```bash
docker run -v <SPRING_BOOT_DIR_ABSOLUTE_PATH>:/project -it --entrypoint /bin/bash marksailes/al2-graalvm:11-22.0.0.2
```
Make sure to replace `SPRING_BOOT_DIR_ABSOLUTE_PATH` with absolute path to springboot directory.

Once docker downloads the image and runs, you would see a bash command that will run inside docker container.
Run below command:

```bash
mvn clean package -Pnative
exit
```

Once above command completes, run:
```bash
sam deploy -t template.native.yaml -g
```

SAM will create an output of the API Gateway endpoint URL for future use in our load tests. 
Make sure the app name used here matches with the `STACK_NAME` present under `load-test/run-load-test-native.sh`

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

### Native Image
> Before running load tests, make sure you update the stack name in [load test bash script](./load-test/run-load-test-native.sh)

```bash
./run-load-test-native.sh
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
  <img src="../imgs/springboot/springboot-sample-log-insights.JPG" alt="JVM Version Log Insights"/>
</p>

Latency for GraalVM version:

<p align="center">
  <img src="../imgs/springboot/springboot-native-log-insights.JPG" alt="GraalVM Version Log Insights"/>
</p>

## AWS X-Ray Tracing
You can add additional detail to your X-Ray tracing by adding a TracingInterceptor to your AWS SDK clients.

Example cold start trace for JVM version:

<p align="center">
  <img src="../imgs/springboot/springboot-sample-cold-trace.JPG" alt="JVM Version Cold Trace Example"/>
</p>

Example cold start trace for GraalVM version:

<p align="center">
  <img src="../imgs/springboot/springboot-native-cold-trace.JPG" alt="GraalVM Version Cold Trace Example"/>
</p>

Example warm start trace for JVM version:

<p align="center">
  <img src="../imgs/springboot/springboot-sample-warm-trace.JPG" alt="JVM Version Warm Trace Example"/>
</p>

Example warm start trace for GraalVM version:

<p align="center">
  <img src="../imgs/springboot/springboot-native-warm-trace.JPG" alt="GraalVM Version Warm Trace Example"/>
</p>
