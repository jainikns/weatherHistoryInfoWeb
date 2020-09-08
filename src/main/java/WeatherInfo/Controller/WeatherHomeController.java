package WeatherInfo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherHomeController {

    @GetMapping(value = "/")
    public String loadWeatherData() {
        return "index";
    }
}
