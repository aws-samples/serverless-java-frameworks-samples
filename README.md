## Lambda demo with common Java application frameworks

![Architecture Diagram](imgs/diagram.jpg)

This is a simple serverless application built in Java using popular frameworks — [Micronaut](https://micronaut.io/),
[Quarkus](https://quarkus.io/), and [Spring Boot](https://spring.io/projects/spring-boot)

It consists of an [Amazon API Gateway](https://aws.amazon.com/api-gateway/) backed by four [AWS Lambda](https://aws.amazon.com/lambda/)
functions and an [Amazon DynamoDB](https://aws.amazon.com/dynamodb/) table for storage.

## Requirements

- [AWS CLI](https://aws.amazon.com/cli/)
- [AWS SAM](https://aws.amazon.com/serverless/sam/)
- Java:
  - v17 — Micronaut, Quarkus, and Spring Boot
  - v11 — Dagger
- [GraalVM](https://www.graalvm.org/) for Native builds
- Maven
- [Artillery](https://www.artillery.io/) for load-testing application
- Docker (at least 8GB memory and 4 CPUs)

## Software
Each of the sub folders contains a Product Maven project.
Each Maven project contains all the code for all four Lambda functions.
It uses the hexagonal architecture pattern to decouple the entry points from the main domain logic
and the storage logic.

## Infrastructure

The sample application can be deployed in three different ways:
1. Managed Java Runtime (without SnapStart) — This mode uses zip packaging style and runs on the JVM inside the Lambda environment.
2. Managed Java Runtime (with SnapStart) — This mode enables SnapStart on the Lambda functions.
   Current examples only include Spring Boot and Quarkus versions of the application.
Stay tuned for other framework examples with SnapStart.
   Learn more about SnapStart [here](https://docs.aws.amazon.com/lambda/latest/dg/snapstart.html).
3. Custom Runtime — This mode uses GraalVM native image and uses Custom Runtime to run on Lambda. 
   GraalVM native image is a stand-alone execution binary and does not require a JVM to run.

## Deployment and Testing

[Micronaut](micronaut)

[Quarkus](quarkus)

[Spring Boot](springboot)

[Dagger](dagger)

## Summary
Below is the cold start and warm start latencies observed.
Please refer to the "Load Test" section of each subproject for more details.
All latencies listed below are in milliseconds.

[Artillery](https://www.artillery.io/) is used to make **100 requests / second for 10 minutes to our API endpoints**.

### Results from Managed Java Runtime without SnapStart

<table class="table-bordered">
        <tr>
            <th colspan="1" style="text-align:center;"></th>
            <th colspan="4" style="text-align:center;">Cold Start (ms)</th>
            <th colspan="4" style="text-align:center;">Warm Start (ms)</th>           
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
            <th>Micronaut (Java 17)</th>
            <td>4609.07</td>
            <td>4943.09</td>
            <td>5501.05</td>
            <td>6657.94</td>
            <td>8.52</td>
            <td>14.31</td>
            <td>32.52</td>
            <td>314.99</td>
        </tr>
        <tr>
            <th>Quarkus (Java 21)</th>
            <td><b style="color: green">2807.45</b></td>
            <td><b style="color: green">3330.47</b></td>
            <td><b style="color: green">4051.52</b></td>
            <td><b style="color: green">4483.37</b></td>
            <td><b style="color: green">8.07</b></td>
            <td><b style="color: green">12.91</b></td>
            <td><b style="color: green">24.61</b></td>
            <td><b style="color: green">156.30</b></td>
        </tr>
        <tr>
            <th>Spring Boot (Java 21)</th>
            <td>5731.16</td>
            <td>6018.84</td>
            <td>6258.09</td>
            <td>7011.35</td>
            <td>8.32</td>
            <td>13.54</td>
            <td>26.65</td>
            <td>174.24</td>
        </tr>
        <tr>
            <th>Dagger *</th>
            <td>3213.00</td>
            <td>3629.68</td>
            <td>4850.11</td>
            <td>6896.54</td>
            <td>8.94</td>
            <td>16.34</td>
            <td>40.38</td>
            <td>292.20</td>
        </tr>
</table>
*: Dagger is not fully comparable to other frameworks as it only provides dependency injection, and thus is much lighter than the others, which explains these results.
When choosing a framework, be conscious of the features available. Dagger was initially done with Android in mind, to provide a lightweight dependency injection framework, without introspection (like Spring). 
It fits particularly well with Lambda to reduce the initialization time, but does not provide the breadth of the others.

### Results from Managed Java Runtime with SnapStart enabled Lambda functions

<table class="table-bordered">
        <tr>
            <th colspan="1" style="text-align:center;"></th>
            <th colspan="4" style="text-align:center;">Cold Start (ms)</th>
            <th colspan="4" style="text-align:center;">Warm Start (ms)</th>           
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
            <th>Micronaut (Java 17)</th>
            <td>726.11</td>
            <td>815.37</td>
            <td>931.30</td>
            <td>1047.49</td>
            <td>7.68</td>
            <td>12.30</td>
            <td>28.18</td>
            <td>244.82</td>
        </tr>
        <tr>
            <th>Quarkus (Java 21)</th>
            <td><b style="color: green">680.44</b></td>
            <td><b style="color: green">812.12</b></td>
            <td><b style="color: green">922.04</b></td>
            <td><b style="color: green">1038.75</b></td>
            <td><b style="color: green">7.75</b></td>
            <td><b style="color: green">13.11</b></td>
            <td><b style="color: green">23.84</b></td>
            <td><b style="color: green">174.29</b></td>
        </tr>
        <tr>
            <th>Spring Boot (Java 21)</th>
            <td>1085.19</td>
            <td>1278.47</td>
            <td>1583.38</td>
            <td>1967.14</td>
            <td>7.10</td>
            <td>12.01</td>
            <td>23.09</td>
            <td>207.14</td>
        </tr>
</table>


### Results from GraalVM Native images running in custom runtime

<table class="table-bordered">
        <tr>
            <th colspan="1" style="text-align:center;"></th>
            <th colspan="4" style="text-align:center;">Cold Start (ms)</th>
            <th colspan="4" style="text-align:center;">Warm Start (ms)</th>           
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
            <th>Micronaut (22.3.r17)</th>
            <td>638.27</td>
            <td>722.49</td>
            <td>960.60</td>
            <td>1416.85</td>
            <td>7.56</td>
            <td>12.11</td>
            <td>23.46</td>
            <td>1029.55</td>
        </tr>
        <tr>
            <th>Quarkus</th>
            <td>459.40</td>
            <td>507.19</td>
            <td>539.07</td>
            <td>583.33</td>
            <td>7.04</td>
            <td>11.82</td>
            <td>19.38</td>
            <td>228.46</td>
        </tr>
        <tr>
            <th>Spring Boot</th>
            <td>732.67</td>
            <td>835.17</td>
            <td>918.36</td>
            <td>1083.40</td>
            <td>8.53</td>
            <td>13.54</td>
            <td>22.01</td>
            <td>156.96</td>
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
