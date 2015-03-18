# oRest

oRest is a simple utility for (un)marshaling java objects while consuming RESTful web service.

## Installation

Installation requires [gradle][https://gradle.org/] installed.

Run ```gradle clean install``` and use dependency with group id ```com.dziga```, artifact id ```orest``` and current version ```0.1```

## Usage

### Out of the box

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

POST (assuming service will give a customer object back)

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

PUT (assuming service will give a customer object back)

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

DELETE (status code is always returned)

```java
    objectRestClient.deleteViaService("/customers/1")
```

### Additional settings

oRest out of the box works with some defaults such are

  - messaging request type ```xml``` that can be set to ```json``` with ```objectRestClient.setRequestType("json");```
  - [namespace URI](http://www.w3schools.com/dom/prop_element_namespaceuri.asp) set to empty string and can be changed with ```objectRestClient.setNamespaceUri("info.about.ql.uri");```

### Examples

Check [customer example](examples/customer)

## Planned features