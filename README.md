# Ecommerce-app Microservices architecture

## Application structure
    The application is microservices application made up of serveral
    microservices interacting together to function as a single
    ecommerce application. The application makes use of KEYCLOAK for
    securing the apis. However, for demo reasons security was set up
    on only the gateway which serves as the entry point to the application.

## Various Services making up the ecommerce app
* API GATEWAY
* CONFIG SERVER
* CUSTOMER SERVICE
* PRODUCT SERVICE
* ORDER SERVICE
* PAYMENT SERVICE
* NOTIFICATION SERVICE
* DISCOVERY SERVER

## Utils
    Implemented distributed tracing using zipkin to trace the paths of
    requests entering the application. Added Kafka as a messaging broker
    to enable asyncronous processing of requests which helped in sending
    payments and order confirmation emails to users.

## ER diagram of database entities
![image](https://github.com/user-attachments/assets/23fed942-38a0-4dd7-ac42-5e9ab97f4a4e)
