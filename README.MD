# WeatherApp

Sample weather app using OpenMetro weather service. I have used Vaadin Flow with Spring Boot. For <b>chart</b> and <b>unit testing</b> 
I have used <b>Vaadin Pro</b> with 14 days trial license.

### Language Framework and Tools:
* Java 21
* Spring Boot 3.2.7
* Vaadin 24.4.4
* Postgres 16rc1
* Docker 25.0.3

### Build Tool:
* Apache Maven 3.9.8

### Prepare environment
If you have a Vaadin Pro subscription you can use that license, otherwise copy the <b>license-file\proKey</b> and
<b>license-file\userKey</b> files in you <b>USER_HOME\.vaadin\ </b> directory.
<p>
<b>
Important:
If files with the same name exists in your local machine in USER_HOME directory, make sure to keep a backup copy, 
so that you can revert the changes after testing the app.
</b>
</p>

### Build
````
mvn package -Pproduction
````
### Run
````
mvn spring-boot:run -Dspring.profiles.active=prod
````
### Docker
````
docker build .
docker-compose up
````

### Known Issue:
Please build the project by <b>Maven</b> before running <b>docker build</b> or <b>docker-compose</b> because <b>docker build</b> will not build the project.
