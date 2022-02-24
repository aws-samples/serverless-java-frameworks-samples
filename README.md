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
            <td>11162.78</td>
            <td>11594.91</td>
            <td>11935.91</td>
            <td>12281.78</td>
            <td>9.67</td>
            <td>29.40</td>
            <td>89.31</td>
            <td>1012.22</td>
        </tr>
        <tr>
            <th>Quarkus</th>
            <td style="color: lightgreen;">10799.03</td>
            <td style="color: lightgreen;">11263.65</td>
            <td style="color: lightgreen;">11629.73</td>
            <td style="color: lightgreen;">11968.59</td>
            <td>10.64</td>
            <td>28.48</td>
            <td>77.42</td>
            <td>861.7</td>
        </tr>
        <tr>
            <th>Spring Boot</th>
            <td>25461.64</td>
            <td>26184.27</td>
            <td>26712.96</td>
            <td>27021.46</td>
            <td>13.08</td>
            <td>46.58</td>
            <td>207.15</td>
            <td>824.41</td>
        </tr>
</table>
    
**For GraalVM versions of the project:**

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
            <td>679.75</td>
            <td>739.29</td>
            <td>797.64</td>
            <td>854.17</td>
            <td>6.10</td>
            <td>7.75</td>
            <td>14.08</td>
            <td>215.02</td>
        </tr>
        <tr>
            <th>Quarkus</th>
            <td>562.74</td>
            <td>607.76</td>
            <td>637.63</td>
            <td>860.5</td>
            <td>7.15</td>
            <td>12.01</td>
            <td>18.47</td>
            <td>245.18</td>
        </tr>
        <tr>
            <th>Spring Boot</th>
            <td>728.29</td>
            <td>789.70</td>
            <td>815.37</td>
            <td>835.99</td>
            <td>9.20</td>
            <td>14.16</td>
            <td>24.27</td>
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

