# Movies apis using AWS Lambda and Spring Boot

Example of a [Spring Boot](https://projects.spring.io/spring-boot/) application deployed using [AWS Lambda](https://aws.amazon.com/lambda/) for a serverless environment using the [Serverless Framework](https://serverless.com).

Note: This example uses a MySQL database to support an RDBMS approach to storing and querying data. The database is created and preloaded with data when the first Lambda function is invoked. You will notice a delay in **"cold starts"** and a message of 'endpoint timeout' until the Spring environment is fully initialized.

## Prerequisites

- Create an [Amazon Web Services](https://aws.amazon.com) account - for the purpose of this demo, the Free Tier will be more than enough
- Create an RDBMS database (MySQL, Postgres, etc)
- Install and set-up [Serverless Framework CLI](https://serverless.com) - more on this below
- Install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Serverless Framework

For this example I used the [Serverless Framework](https://serverless.com), an agnostic framework to help deploy serverless applications across different cloud solutions in an easy and painless way.

This application focused on using AWS Lambda to run its services. AWS Lambda does not provide external endpoints to consume. Instead we should use  API Gateway to create the routes, paths and connect with the Lambda functions.

There a few important notes here:

  a) all configuration for the Serverless framework is located at the 'serverless.yml' file
  b) this file consolidates all routes to be configured on AWS API Gateway, the lambda functions and how to connect one with the other
  c) there are two important architecture decisions here:
  c.1) this is a single Spring Boot application (fat jar) with both services for Movies and Actors. Ideally, different domains would be independent applications following a micro-services architecture, but for the sake of simplicity both domains are in the same app and sharing the same datastore
  c.2) for each type of endpoint method, I created a different Lambda function and API Gateway resource. The reasoning here is that, in a traditional serverless production environment you would want these services to scale independently as each one has a different footprint, latency, and while managing you want greater visibility of the costs associated with each type of service to determine potential enhancements or rearchitecture
  d) a flaw of this approach is the increased number of database connections as there's no Connection Pool to be shared - in an AWS environment, the higher the number of the connections, the higher the size of the instance - then again, for a serverless approach, the most ideal candidate in the AWS universe is DynamoDB


### Limitations

#### Searching by actor or movie name 

In a traditional a production setting, you should have an index server, and for simplicity it's not included in this application, and this causes a few limitations: this application requires exact perfect name and case match to search by either actor or movie name, while an index server can match with synonyms, common wrongly types names, etc

To search by movie or actor name, you have to supply the exact words and cases and spaces in all searches. This is intentional.

##### alternative solution 1

To increase the matching capability by only using an RDBMS, you could:

a) tokenize the input string and use partial search (e.g. '%') functions on the database, for example: "first_name like '%token1%' or "first_name like '%token2%'", etc
b) lower/uppercase everything while searching to avoid different ways to case-sensitivity

This is really a bad approach as:
- it requires a lot of manual manipulation of queries, opening possibilities of SQL injection if not properly thought out checking all edge cases and escaping all input
- it drastically reduces the performance
- the results won't likely be the best matches


##### alternative solution 2

Adding an index server, you can create your own data pipeline as changes happen to the database store, you application puts in a stream (e.g. Kineses, Kafka, etc) and it gets sent to an index server


##### alternative solution 3

If using only AWS and RDBMS is not a strong requirement, for this application, DynamoDB would be perfect as we can:

- create a stream of data changes automatically
- this stream can be automatically indexed by Elasticsearch 
- you don't have to manage the data pipeline :)


### endpoints


** List all Movies **


Returns all movies currently in the database.

* **URL**

  /dev/movies

* **Method:**

  `GET`
  
* **URL Params**

  None.
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"title":"Spider-Man: Homecoming","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"},{"id":2,"firstName":"Michael","lastName":"Keaton","shownName":"Michael Keaton","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"}]},{"id":2,"title":"Despicable Me 3","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[{"id":3,"firstName":"Steve","lastName":"Carell","shownName":"Steve Carell","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"},{"id":4,"firstName":"Kristen","lastName":"Wiig","shownName":"Kristen Wiig","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"}]},{"id":3,"title":"Baby Driver","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[]},{"id":4,"title":"Wonder Woman","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[]},{"id":5,"title":"Transformers: The Last Knight","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[]},{"id":6,"title":"Cars 3","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[]},{"id":7,"title":"The House","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[]},{"id":8,"title":"The Big Sick","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[]}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
** List Movie by ID **
----

Returns a movie by its ID.

* **URL**

  /dev/movies/:id

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"title":"Spider-Man: Homecoming","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"},{"id":2,"firstName":"Michael","lastName":"Keaton","shownName":"Michael Keaton","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"}]}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
  OR

  **Code:** 400 BAD REQUEST <br />
  **Content:** `Bad request`
    
    
** List Movies by Actor **
----

Returns all movies with an actor in the cast and/or movie title name. This is case-sensitive and requires full name correctly spelled. You can use either or both together.

* **URL**

  /dev/movies?actor=:actor&title=:title

* **Method:**

  `GET`
  
*  **URL Params**

   **Optional:**
 
   `actor=[string]`<br/>
   `title=[string]`
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"title":"Spider-Man: Homecoming","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"},{"id":2,"firstName":"Michael","lastName":"Keaton","shownName":"Michael Keaton","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"}]}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
  OR

  **Code:** 400 BAD REQUEST <br />
  **Content:** `Bad request`

    
** List all Actors **
----

Returns all actors currently in the database.

* **URL**

  /dev/actors

* **Method:**

  `GET`
  
* **URL Params**

  None.
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:07:19 AM","modifiedAt":"Jul 17, 2017 12:07:19 AM"},{"id":2,"firstName":"Michael","lastName":"Keaton","shownName":"Michael Keaton","birthYear":1989,"createdAt":"Jul 17, 2017 12:07:19 AM","modifiedAt":"Jul 17, 2017 12:07:19 AM"},{"id":3,"firstName":"Steve","lastName":"Carell","shownName":"Steve Carell","birthYear":1989,"createdAt":"Jul 17, 2017 12:07:19 AM","modifiedAt":"Jul 17, 2017 12:07:19 AM"},{"id":4,"firstName":"Kristen","lastName":"Wiig","shownName":"Kristen Wiig","birthYear":1989,"createdAt":"Jul 17, 2017 12:07:19 AM","modifiedAt":"Jul 17, 2017 12:07:19 AM"}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
** List Actor by ID **
----

Returns an actor by its ID.

* **URL**

  /dev/actors/:id

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:07:19 AM","modifiedAt":"Jul 17, 2017 12:07:19 AM"}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
  OR

  **Code:** 400 BAD REQUEST <br />
  **Content:** `Bad request`


** Find Actor by Name **
----

Returns an actor profile using the complete name. This is case-sensitive and requires full name to be correctly spelled.

* **URL**

  /dev/actors?name=:name

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `name=[string]`
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:07:19 AM","modifiedAt":"Jul 17, 2017 12:07:19 AM"}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `Bad request`


** Add Movie to Database **
----

Add a new movie to the database

* **URL**

  /dev/movies

* **Method:**

  `POST`
  
*  **URL Params**

   None
   
* **Data Params**

  **Required:**
 
   `title=[string]`
   `release_year=[integer]`
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"title":"Spider-Man: Homecoming","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"},{"id":2,"firstName":"Michael","lastName":"Keaton","shownName":"Michael Keaton","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"}]}]`
    
* **Error Response:**

  **Code:** 400 BAD REQUEST <br />
  **Content:** `Bad request`


** Add an Actor to Database **
----

Add a new actor to the database

* **URL**

  /dev/actors

* **Method:**

  `POST`
  
*  **URL Params**

   None
   
* **Data Params**

  **Required:**
 
   `firstName=[string]`<br/>
   `lastName=[string]`<br/>
   `shownName=[string]`<br/>
   `birthYear=[integer]`<br/>
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:07:19 AM","modifiedAt":"Jul 17, 2017 12:07:19 AM"}]`
    
* **Error Response:**

  **Code:** 400 BAD REQUEST <br />
  **Content:** `Bad request`


** Add an Actor to a Movie **
----

Add an actor to a movie previously saved. Both IDs are checked and will result in a 404 if either do not exist.

* **URL**

  /dev/movies/:id/actor

* **Method:**

  `PUT`
  
*  **URL Params**

   **Required:**
 
   `id=[long]`
   
* **Data Params**

  **Required:**
 
  `actorId=[long]`
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"title":"Spider-Man: Homecoming","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"},{"id":2,"firstName":"Michael","lastName":"Keaton","shownName":"Michael Keaton","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"}]}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
  OR

  **Code:** 400 BAD REQUEST <br />
  **Content:** `Bad request`


** Update information about a Movie **
----

This allows you to change the title and/or release year of a movie. If the ID does not exist it will result in a 404.

* **URL**

  /dev/movies/:id/

* **Method:**

  `PUT`
  
*  **URL Params**

   **Required:**
 
   `id=[long]`
   
* **Data Params**

  **Required:**
 
  `title=[string]`<br/>
  `releaseYear=[integer]`
  
* **Success Response:**

  **Code:** 200 <br />
  **Content:** `[{"id":1,"title":"Spider-Man: Homecoming","releaseYear":2017,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM","actors":[{"id":1,"firstName":"Tom","lastName":"Holland","shownName":"Tom Holland","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"},{"id":2,"firstName":"Michael","lastName":"Keaton","shownName":"Michael Keaton","birthYear":1989,"createdAt":"Jul 17, 2017 12:34:41 AM","modifiedAt":"Jul 17, 2017 12:34:41 AM"}]}]`
    
* **Error Response:**

  **Code:** 404 NOT FOUND <br />
  **Content:** `Not found.`
    
  OR

  **Code:** 400 BAD REQUEST <br />
  **Content:** `Bad request`



### Build and Deploy
- Before building, make sure you have configured the database URL parameters in the application.properties file inside the project folders (see note below)
- To build, run `./mvn clean build`
- To deploy, run `serverless deploy`

#### But what about the real world?

In a production setup, the correct way of configuring the properties and database connection would be using the Spring Config server to centralize and protect application properties and credentials in a single place.

You are able to create bootstrap-[ENVIRONMENT].properties files and load the properties for each environment from a central configuration server creating a transparent and secure build and deploy environment.
