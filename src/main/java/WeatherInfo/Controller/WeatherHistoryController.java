package WeatherInfo.Controller;

import WeatherInfo.Service.WeatherInfoService;
import WeatherInfo.model.WeatherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/weatherInfo")
public class WeatherHistoryController {

    Logger logger = LoggerFactory.getLogger(WeatherHistoryController.class);

    @Autowired
    WeatherInfoService weatherInfoService;

    public WeatherHistoryController(WeatherInfoService weatherInfoService) {
        this.weatherInfoService = weatherInfoService;
    }

    public WeatherInfoService getWeatherInfoService() {

        return weatherInfoService;
    }

    public void setWeatherInfoService(WeatherInfoService weatherInfoService) {
        this.weatherInfoService = weatherInfoService;
    }

    @GetMapping
    public Page<WeatherInfo> getWeatherDataByParam(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                   Pageable pageable) {
        logger.info("Inside WeatherHistoryController with params startdate : " + startDate + "EndDate : " + endDate);
        return getWeatherInfoService().getHistoricWeatherData(startDate,endDate,pageable);
    }

    @GetMapping("/stations/{id}")
    public WeatherInfo getStationDetailById(@PathVariable Integer id) {

        logger.info("Inside WeatherHistoryController getStationDetailById with params id : " + id);

        WeatherInfo station = weatherInfoService.getStationById(id);

        return station;
    }

}
