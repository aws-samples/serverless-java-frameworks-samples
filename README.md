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

Below is the cold start and warm start latencies observed. Please refer to "Load Test" section of each sub project for more details.
All latencies listed below are in milliseconds.

**For JVM versions of the project:**

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
            <td>11162.7833</td>
            <td>9.6783</td>
            <td>10799.0325</td>
            <td>10.6454</td>
            <td>25461.6424</td>
            <td>13.0852</td>
        </tr>
        <tr>
            <th>p90</th>
            <td>11594.9115</td>
            <td>29.4008</td>
            <td>11263.6512</td>
            <td>28.4821</td>
            <td>26184.2769</td>
            <td>46.5883</td>            
        </tr>
        <tr>
            <th>p99</th>
            <td>11935.9141</td>
            <td>89.314</td>
            <td>11629.731</td>
            <td>77.4241</td>
            <td>26712.9674</td>
            <td>207.1504</td>            
        </tr>
        <tr>
            <th>max</th>
            <td>12281.78</td>
            <td>1012.22</td>
            <td>11968.59</td>
            <td>861.7</td>
            <td>27021.46</td>
            <td>824.41</td>            
        </tr>
    </table>
    
**For GraalVM versions of the project:**

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
            <td>679.7599</td>
            <td>6.103</td>
            <td>562.7492</td>
            <td>7.1573</td>
            <td>728.2943</td>
            <td>9.2033</td>
        </tr>
        <tr>
            <th>p90</th>
            <td>739.2955</td>
            <td>7.751</td>
            <td>607.7695</td>
            <td>12.014</td>
            <td>789.7092</td>
            <td>14.164</td>            
        </tr>
        <tr>
            <th>p99</th>
            <td>797.6419</td>
            <td>14.0895</td>
            <td>637.6386</td>
            <td>18.4737</td>
            <td>815.3755</td>
            <td>24.2793</td>            
        </tr>
        <tr>
            <th>max</th>
            <td>854.17</td>
            <td>215.02</td>
            <td>860.5</td>
            <td>245.18</td>
            <td>835.99</td>
            <td>304.35</td>            
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

