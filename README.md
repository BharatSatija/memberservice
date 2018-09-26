# Member Service using spring boot

Build Restful CRUD API for a simple Note-Taking application using Spring Boot, Mysql, JPA and Hibernate.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

## How to run


This application is packaged as a war which has Tomcat 8 embedded. Run it using the java -jar command.

**1. Build and run the app using maven**

```bash
mvn  package
java -jar target/member-service-1.0.0.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8088>.

## How to test


```bash
mvn  test
```


## Docker

```bash
* Build webapp as jar file using maven refer point 1
* Build image : docker build -t member-service .
* Show images : docker images
* Run image : docker run --rm -p 8088:8088 member-service

The app will start running at <http://localhost:8088>.
```


## Explore Rest APIs

The app defines following CRUD APIs.

    POST /api/members

    GET /api/members

    GET /api/members/{memberId}
    
    PUT /api/members/{memberId}
    
    DELETE /api/members/{memberId}

You can test them using postman, any other rest client or curl.

