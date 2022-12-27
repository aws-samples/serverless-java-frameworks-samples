## Lambda demo with common Java application frameworks

<p align="center">
  <img src="imgs/diagram.jpg" alt="Architecture diagram"/>
</p>

This is a simple serverless application built in Java using popular frameworks - [Micronaut](https://micronaut.io/), [Quarkus](https://quarkus.io/), and [Spring Boot](https://spring.io/projects/spring-boot)

It consists of an [Amazon API Gateway](https://aws.amazon.com/api-gateway/) backed by four [AWS Lambda](https://aws.amazon.com/lambda/)
functions and an [Amazon DynamoDB](https://aws.amazon.com/dynamodb/) table for storage.

## Requirements

- [AWS CLI](https://aws.amazon.com/cli/)
- [AWS SAM](https://aws.amazon.com/serverless/sam/)
- Java 11
- Maven
- [Artillery](https://www.artillery.io/) for load-testing the application
- Docker (at least 8GB memory and 4 CPUs)

## Software

Each of the sub folders contains a Products maven project. Each maven project contains all the code for all four
Lambda functions. It uses the hexagonal architecture pattern to decouple the entry points, from the main domain logic
and the storage logic.

## Infrastructure

The sample application can be deployed in two different ways:
1. JVM - This mode uses zip packaging style and runs on the JVM inside the Lambda environment.
2. Custom Runtime - This mode uses GraalVM native image and uses Custom Runtime to run on Lambda. 
   GraalVM native image is a stand-alone execution binary and does not require a JVM to run.

## Deployment and Testing

[Micronaut](micronaut)

[Quarkus](quarkus)

[Spring Boot](springboot)

[Dagger](dagger)

## Summary
Below is the cold start and warm start latencies observed. Please refer to "Load Test" section of each sub-project for more details.
All latencies listed below are in milliseconds.

[Artillery](https://www.artillery.io/) is used to make **100 requests / second for 10 minutes to our API endpoints**.

### Results from Managed Java Runtime

<table class="table-bordered">
        <tr>
            <th colspan="1" style="horizontal-align : middle;text-align:center;"></th>
            <th colspan="4" style="horizontal-align : middle;text-align:center;">Cold Start (ms)</th>
            <th colspan="4" style="horizontal-align : middle;text-align:center;">Warm Start (ms)</th>           
        </tr>
        <tr>
            <th></th>
            <th scope="col">p50</th>
            <th scope="col">p90</th>
            <th scope="col">p99</th>
            <th scope="col">max</th>
            <th scope="col">p50</th>
            <th scope="col">p90</th>
            <th scope="col">p99</th>
            <th scope="col">max</th>
        </tr>        
        <tr>
            <th>Micronaut</th>
            <td>4898.8332</td>
            <td>5479.1013</td>
            <td>7551.7604</td>
            <td>10147.71</td>
            <td>9.3759</td>
            <td>17.1385</td>
            <td>42.356</td>
            <td>531.75</td>
        </tr>
        <tr>
            <th>Quarkus</th>
            <td>3808.0718</td>
            <td>4229.4438</td>
            <td>5501.0506</td>
            <td>7195.38</td>
            <td><b style="color: green">8.6605</b></td>
            <td><b style="color: green">15.3362</b></td>
            <td><b style="color: green">38.5082</b></td>
            <td>375.65</td>
        </tr>
        <tr>
            <th>Spring Boot</th>
            <td>5517.570</td>
            <td>6442.1359</td>
            <td>8616.8082</td>
            <td>11551.01</td>
            <td>9.3759</td>
            <td>16.8686</td>
            <td>40.3863</td>
            <td>319.69</td>
        </tr>
        <tr>
            <th>Dagger *</th>
            <td><b style="color: green">3213.0091</b></td>
            <td><b style="color: green">3629.6889</b></td>
            <td><b style="color: green">4850.1132</b></td>
            <td><b style="color: green">6896.54</b></td>
            <td>8.9399</td>
            <td>16.3415</td>
            <td>40.3863</td>
            <td><b style="color: green">292.2</b></td>
        </tr>
</table>
*: Dagger is not fully comparable to other frameworks as it only provides dependency injection, and thus is much lighter than the others, which explains these results.
When choosing a framework, be conscious of the features available. Dagger was initially done with Android in mind, to provide a lightweight dependency injection framework, without introspection (like Spring). 
It fits particularly well with Lambda to reduce the initialization time, but does not provide the breadth of the others.

### Results from GraalVM Native images running in custom runtime

<table class="table-bordered">
        <tr>
            <th colspan="1" style="horizontal-align : middle;text-align:center;"></th>
            <th colspan="4" style="horizontal-align : middle;text-align:center;">Cold Start (ms)</th>
            <th colspan="4" style="horizontal-align : middle;text-align:center;">Warm Start (ms)</th>           
        </tr>
        <tr>
            <th></th>
            <th scope="col">p50</th>
            <th scope="col">p90</th>
            <th scope="col">p99</th>
            <th scope="col">max</th>
            <th scope="col">p50</th>
            <th scope="col">p90</th>
            <th scope="col">p99</th>
            <th scope="col">max</th>
        </tr>        
        <tr>
            <th>Micronaut</th>
            <td>604.16</td>
            <td>659.02</td>
            <td>700.45</td>
            <td>893.70</td>
            <td><b style="color: green">6.30</b></td>
            <td><b style="color: green">8.00</b></td>
            <td><b style="color: green">15.88</b></td>
            <td><b style="color: green">69.9</b></td>
        </tr>
        <tr>
            <th>Quarkus</th>
            <td><b style="color: green">437.45</b></td>
            <td><b style="color: green">475.76</b></td>
            <td><b style="color: green">519.50</b></td>
            <td><b style="color: green">528.03</b></td>
            <td>7.45</td>
            <td>12.60</td>
            <td>21.32</td>
            <td>93.45</td>
        </tr>
        <tr>
            <th>Spring Boot</th>
            <td>620.66</td>
            <td>684.53</td>
            <td>721.77</td>
            <td>751.98</td>
            <td>9.10</td>
            <td>14.22</td>
            <td>23.61</td>
            <td>259.16</td>
        </tr>
</table>

## 👀 With other languages

You can find implementations of this project in other languages here:

* [☕ Java (GraalVM)](https://github.com/aws-samples/serverless-graalvm-demo)
* [🦀 Rust](https://github.com/aws-samples/serverless-rust-demo)
* [🏗️ TypeScript](https://github.com/aws-samples/serverless-typescript-demo)
* [🐿️ Go](https://github.com/aws-samples/serverless-go-demo)
* [⭐ Groovy](https://github.com/aws-samples/serverless-groovy-demo)
* [🤖 Kotlin](https://github.com/aws-samples/serverless-kotlin-demo)
* [🥅 .NET](https://github.com/aws-samples/serverless-dotnet-demo)

## Security

See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License

This library is licensed under the MIT-0 License. See the LICENSE file.
