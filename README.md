# Stock Analyzer

## Design

The application is built with Java, Spring Boot, H2 In-memory DB and concurrency achieved using Asynchronous and CompletableFuture

## Install

Run the following commands in the root directory of the project to build and start the server

mvn install

mvn spring-boot:run

## Run

The API endpoints to be executed from the attached POSTMAN collections

## Endpoints

### 1. Upload bulk data set and save to DB

http://localhost:8080/dowjones/api/v1/stocks/

### 2. Read stock info by stock ticker and quarter

http://localhost:8080/dowjones/api/v1/stocks?stockTicker=AA&quarter=1

### 3. Create new stock record

http://localhost:8080/dowjones/api/v1/stock

## Assumptions and Enhancements

1. The application can also be implemented using Reactive Spring and Reactive MongoDB and performance can be compared.
2. Logging info has to be implemented.
3. More test cases and error scenarios to be covered.
4. Spring security can be implemented.
5. Bulk upload works for single CSV file. To be implemented for multiple CSV files.

