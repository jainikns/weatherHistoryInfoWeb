package WeatherInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherInfoApplication {

    public static void main(String[] args){
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        SpringApplication.run(WeatherInfoApplication.class,args);
    }
}
