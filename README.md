## Lambda demo with common Java application frameworks

<p align="center">
  <img src="imgs/diagram.jpg" alt="Architecture diagram"/>
</p>

This is a simple serverless application built in Java using popular frameworks. 

[Micronaut](https://micronaut.io/)

[Quarkus](https://quarkus.io/)

[Spring Boot](https://spring.io/projects/spring-boot)


It consists of an [Amazon API Gateway](https://aws.amazon.com/api-gateway/) backed by four [AWS Lambda](https://aws.amazon.com/lambda/)
functions and an [Amazon DynamoDB](https://aws.amazon.com/dynamodb/) table for storage.

## Requirements

- [AWS CLI](https://aws.amazon.com/cli/)
- [AWS SAM](https://aws.amazon.com/serverless/sam/)
- Java 11
- Maven
- [Artillery](https://www.artillery.io/) for load-testing the application
- Docker

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

## Summary

Below is the latencies seen for JVM version of the samples

<table class="table-bordered">
        <tr>
            <th colspan="1" style="horizontal-align : middle;text-align:center;"></th>
            <th colspan="2" style="horizontal-align : middle;text-align:center;">Micronaut</th>
            <th colspan="2" style="horizontal-align : middle;text-align:center;">Quarkus</th>
            <th colspan="2" style="horizontal-align : middle;text-align:center;">Spring Boot</th>           
        </tr>
        <tr>
            <th></th>
            <th scope="col">Cold Start</th>
            <th scope="col">Warm Start</th>
            <th scope="col">Cold Start</th>
            <th scope="col">Warm Start</th>
            <th scope="col">Cold Start</th>
            <th scope="col">Warm Start</th>
        </tr>        
        <tr>
            <th>p50</th>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>
        </tr>
        <tr>
            <th>p90</th>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>            
        </tr>
        <tr>
            <th>p99</th>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>
            <td>x</td>            
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

## Security

See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License

This library is licensed under the MIT-0 License. See the LICENSE file.

