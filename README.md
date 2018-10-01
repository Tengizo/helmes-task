# helmes-task

instructions for running

1. project runs with one command. 
 
    mvn spring-boot:run
    
it will install node, npm, node_modules, will build angular app with prod properties and takes result 
files to resources/static, then starts spring-boot server
project url is http://localhost:8080

2. another way to run application is to start api and client separately 
client will start by running 
    2.1 npm install and npm start
    2.2 spring-boot by running main class
    
application uses h2 embedded database, and stores all data to files
you can find DB Tables on url: http://localhost:8080/h2

all api methods are available on swagger http://localhost:8080/swagger-ui.html