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

### Prepare environment:
If you have a Vaadin Pro subscription you can use that license, otherwise copy the <b>license-file\proKey</b> and
<b>license-file\userKey</b> files in you <b>%userprofile%\.vaadin\ </b> directory.
<p>
<b>
Important:
If files with the same name exists in your local machine in %userprofile% directory, make sure to keep a backup copy, 
so that you can revert the changes after testing the app.
</b>
</p>

### Build by Maven
````
mvn package -Pproduction
````
### Run by Maven
````
mvn spring-boot:run -Dspring.profiles.active=prod
````
### Run in Docker
````
docker build .
docker-compose up
````

After application is running, there are 4 users to use: <b>david</b>, <b>john</b>, <b>emma</b> and <b>olivia</b>. Password for all users is <b>password</b>.

### Known Issue:
* Please build the project by <b>Maven</b> before running <b>docker build</b> or <b>docker-compose</b> because <b>docker build</b> will not build the project.
* Docker container is running but pages are not served. I am wondering this is because any license related issue, since in docker image is running <b>offline</b>, I could not retrieve either <b>offlineKey</b> or <b>server license key</b> for my trial license.
* To run locally please follow the steps:
  * Install postgres, if not installed
  * Create database <b>weather_app</b>
  * Update the property <b>spring.datasource.url</b>, <b>spring.datasource.username</b> and <b>spring.datasource.password</b> in <b>src/main/resources/application.properties</b> file
  * Run the application by Maven
 


