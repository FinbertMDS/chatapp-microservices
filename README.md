# Microservice demo
A microservices demo implemented with stacks:

1. Spring and Spring Cloud
2. React
3. React Native

## Technologies
- Spring Boot
- Spring Cloud Config
- Eureka
- Spring Cloud gateway
- Spring Cassandra
- Spring Kafka
- Kafka
- Zookeeper
- RabbitMQ Stomp
- Mysql
- Redis
- Cassandra

## Services implemented
1. Security: service to authentication with Spring Secrutiy and JWT
2. Message: service for Chat application API: create room, send message.

    Client (web/mobile) will receive by Websocket.
3. Storage: service to upload and download file (Client web and mobile is not implement UI)
4. Contact: service to add and get contact of use. (Client web and mobile is not implement UI)

    After signup user with service security, service security sends 
    messages to Kafka, servie contact receive the message and create 
    data about contact.

Api Documentation of all services in [`ChatApp-Microservices.postman_collection.json`](ChatApp-Microservices.postman_collection.json).

## How To Run
The demo can be run with Docker Compose.

[How to run](HOW-TO-RUN.md) includes more details

## Use ELK Stack
Demo use ELK Stack for logging in microservices at branch [elk](https://github.com/FinbertMDS/chatapp-microservices/tree/elk)

## Thanks
This demo get inspired from
1. [ewolff/microservice](https://github.com/ewolff/microservice)
2. [bezkoder/spring-boot-spring-security-jwt-authentication](https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication)
3. [jorgeacetozi/ebook-chat-app-spring-websocket-cassandra-redis-rabbitmq](https://github.com/jorgeacetozi/ebook-chat-app-spring-websocket-cassandra-redis-rabbitmq)