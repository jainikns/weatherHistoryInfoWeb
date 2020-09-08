Summary : Web page to show weather history for stations

Description : 
1) Web page can display weather basic information and user can find specific weather date data by filtering on start date and end date
2) Upon clicking row user can find detail information about station

###Install dependency and generate package
mvn clean package

###Run Spring Boot application from command line
cd WeatherInfo \
mvn spring-boot:run

### Running Junit test
mvn clean test

### Running the app
Access http://127.0.01:8080/ in browser