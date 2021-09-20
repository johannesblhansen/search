## Requirements
Java 16
Maven 3+

## The Task
Given an API specification, openapi.json, build a REST api that can search through input data, in the form of the example
file, data.json.
Elaborate on the effects of increasing the input size.

## Running the application
To run the application, it must be build using maven.

To run the spring boot application, execute following command
java -jar target\exercise-0.0.1-SNAPSHOT.jar ExerciseApplication

Swagger UI can be found here:
http://localhost:8080/api/swagger-ui/#/search-controller/search

## Selected Tech
Maven
Spring Boot
Swagger
    - Used for the UI
    - Used for generating API model from the provided openapi.json
Various Java libs like: Lombok, JUnit, Mockito and GSON

## Notes
File handling - streaming vs objectmapping.
I have implemented both. The reason for doing so, is that objectmapping is far easier and faster to work with, but 
has the inherent problem of running out of memory, if the input stream is big enough. Therefore, I also made a streaming 
approach, as it handles data size scaling better. Plus, it was fun.

To simulate data being updated, I have created ScheduledFileLoader. On a timer it looks for the newest file in the input 
directory inboundJsonData. Adding a new file, will make the application switch to that as its datasource. This way it can 
be updated at runtime.

To create larger data files, I have created a utility test, JsonFileDataGenerator. It doesn't test anything, but is 
a test to get easy access to a runtime.

## Benchmark
Done using Postman against the service running on my local machine.
Using a 1 million elements in a json file (150mb in size):
    - Finding 1 element roughly 1000ms
With the current state of the application, run from my computer, it can handle about 600,000 elements within the set 
limit of 500ms.
Below are the benchmarks from my admittedly short investigation.

-1,000,000 (150MB) elements
Find 1 result
stream: 1100ms
mapping: 1400ms

-10,000,000(1.5GB) elements
Find 1 result
new stream: 12000msms
mapping: 13400ms

1.5GB was the largest I could generate before hitting java heap exceptions. But if larger files where used, the mapping 
approach would not work.

## Resilience
To keep high uptime, I would use Docker and Kubernetes to loadbalance and orchistrate between nodes.

## Ideas for improving performance, to handle 10m records in 500ms

# Using concurrency.
Splitting the file into small chunks, and let each chunk be processed by its own thread.
Benchmarking
Timing using async
Normal stream: 5m
9000ms

Async split: 5x 1m
6000ms

Not that much was saved here, but it was run on my old i3m processor laptop with only 2 cores.

Tracking threads through VisualVM on my ubunto machine
