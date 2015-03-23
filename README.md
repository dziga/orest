# oRest
[![Travis](https://img.shields.io/travis/dziga/orest.svg)]() [![Coverity Scan Build Status](https://scan.coverity.com/projects/4609/badge.svg)](https://scan.coverity.com/projects/4609)

oRest is a simple utility for (un)marshaling java objects while consuming RESTful web service.

## Installation

### Maven central (recommended)

Use dependency with group id ```com.github.dziga```, artifact id ```orest``` and current version ```0.1```

### Local

Installation requires [gradle](https://gradle.org/) installed.

Run ```gradle clean install``` and use dependency with group id ```com.github.dziga```, artifact id ```orest``` and current version ```0.2-SNAPSHOT```

## Usage

### Out of the box

#### For the impatient

```java
    ObjectRestClient objectRestClient = new ObjectRestClient("localhost:8080");
    objectRestClient.addHeader("Content-Type", "application/xml, application/json");
    objectRestClient.addHeader("Accept", "application/xml, application/json");
    
    //simple java bean
    Customer customer = new Customer();

    // assuming that server will return customer object of Customer.class type
    customer = (Customer) objectRestClient.getFromService(Customer.class, "/customers/1"));
```

#### Init 

```java
    ObjectRestClient objectRestClient = new ObjectRestClient("localhost:8080");
```
#### Set headers

```java
   objectRestClient.addHeader("Content-Type", "application/xml, application/json");
   objectRestClient.addHeader("Accept", "application/xml, application/json");
```
#### Make a request 

GET (assuming service will give a customer object back)

```java
    Customer customer = new Customer();
    customer = (Customer) objectRestClient.getFromService(Customer.class, "/customers/1"));
```

Client will make the request to server at address ```http://localhost:8080/customers/1```. As we assumed, server will response with object (xml or json format) of Customer type. Client will unmarshall given message to object (Customer.class).

Available get methods:

```java
    getFromService(Class returningModel, String servicePath, HashMap<String, String> params)
    getFromService(Class returningModel, String servicePath)
```

POST (assuming service will give a customer object back)

For POST method, we will define object of Customer.class and fill it with some data. ```postToService``` method expects type of object to post, object itself and type of returning object. 

```java
    Customer customer = new Customer();
    customer.setFirstName("John");
    customer.setLastName("Doe");
    customer.setSignedContractDate("09-10-2015");
    customer.setStreet("Backer street");
    customer.setStreetNumber(3);
    customer.setCity("London");
    customer = (Customer) objectRestClient.postToService(Customer.class, customer, Customer.class, "/customers");
```

In this case, type that we will post and type given back by server are the same, thus we could simply left out return type ```customer = (Customer) objectRestClient.postToService(Customer.class, customer, "/customers")```. Similar to ```getFromService```, client will unmarshall given message to object (Customer.class).

Available post methods:

```java
    postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath, HashMap<String, String> params)
    postToService(Class modelClass, Object modelObject, String servicePath, HashMap<String, String> params)
    postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath)
    postToService(Class modelClass, Object modelObject, String servicePath)
```


PUT (assuming service will give a customer object back)

Works exactly the same as the post method(s)

```java
    Customer customer = new Customer();
    customer.setId(1);
    customer.setFirstName("John");
    customer.setLastName("Doe");
    customer.setSignedContractDate("09-10-2015");
    customer.setStreet("London street");
    customer.setStreetNumber(3);
    customer.setCity("London");
    customer = (Customer) objectRestClient.postToService(Customer.class, customer, Customer.class, "/customers/1");
```

Available put methods:

```java
    putToService(Class modelClass, Object abstractObject, Class returningClass, String servicePath, HashMap<String, String> params)
    putToService(Class modelClass, Object abstractObject, String servicePath, HashMap<String, String> params)
    putToService(Class modelClass, Object abstractObject, Class returningModel, String servicePath) 
    putToService(Class modelClass, Object abstractObject, String servicePath)
```
DELETE (status code is always returned)

```java
    objectRestClient.deleteViaService("/customers/1")
```

### Check response code and body

Sometimes body and response code are needed to evaluate service response along with unmarshaled object. These can be fetched with:

```java
    objectRestClient.getResponseCode()
    objectRestClient.getResponseBody()
```
### Additional settings

oRest out of the box works with some defaults such are

  - messaging request type ```xml``` that can be set to ```json``` with ```objectRestClient.setRequestType("json");```
  - [namespace URI](http://www.w3schools.com/dom/prop_element_namespaceuri.asp) set to empty string and can be changed with ```objectRestClient.setNamespaceUri("info.about.ql.uri");```
  - scheme default ```http```, which can be changed on instantiation of the client ```ObjectRestClient objectRestClient = new ObjectRestClient("https", "localhost:8080");```

Check the [api](src/main/java/com/github/dziga/orest/api/ORest.java) for the full list of available methods.

### Examples

Check [customer example](examples/customer)

## Planned features / improvements

 - return headers
 - more HTTP methods
 - javadoc
 - more examples
 - test coverage

#Licence

[The MIT License (MIT)](LICENSE)
