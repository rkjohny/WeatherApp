# WeatherApp

Sample weather app using OpenMetro weather service. I have used Vaadin Flow with Spring Boot. For <b>chart</b> and <b>unit testing</b> 
I have used <b>Vaadin Pro</b> with 14 days trial license.

### Language Framework and Tools:
* Java 21
* Spring Boot 3.2.7
* Vaadin 24.4.4
* Vaadin Chart 24.4.4
* Postgres 16rc1
* Docker 25.0.3

### Build Tool:
* Apache Maven 3.9.8

### Feature
* Searching and filtering locations by city name
* Daily weather forecast for a specific city
* Hourly weather forecast for a specific city and day
* Mark/Unmark favourite cities
* Hourly forecast of favourite cities
* Global error handling
* Centralized exception handling for all APIs
* API documentation with OpenApi specification
* Initialized with sample data to test API
* Local log file
* Unit testing

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
docker build -t weather_app .
docker-compose up
````
or
````
docker-compose up --build
````

After application is running, there are 4 users to use: <b>david</b>, <b>john</b>, <b>emma</b> and <b>olivia</b>. Password for all users is <b>password</b>.

### Login
````
http://localhost:8080/login
````

### Swagger Url
````
http://localhost:8080/swagger-ui/index.html
````

### Known Issue:
* Calling API through swagger-ui will return Forbidden (status: 403). This is because authorization and routing issue. Need to work on it.
* Building project with <b>Docker</b> works but pages were not server, this is because the licensing issue. Work around: follow the following step
* Build the project by <b>Maven</b> before running <b>docker build</b> or <b>docker-compose --build</b> because <b>docker build</b> will not build the project.

### Run in Local Machine
* If there is any issue in running docker image, run the application in local machine.
* I am wondering license related issue may arise. I could not retrieve either <b>offlineKey</b> or <b>server license key</b> for my trial license.
* To run locally please follow the steps:
  * Install postgres, if not installed
  * Create database <b>weather_app</b>
  * Update the property <b>spring.datasource.url</b>, <b>spring.datasource.username</b> and <b>spring.datasource.password</b> in <b>src/main/resources/application.properties</b> file
  * Run the application by Maven
 
