package WeatherInfo.Service;


import WeatherInfo.model.WeatherInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;


public interface WeatherInfoService {

    public Page<WeatherInfo> getHistoricWeatherData(Date startdate, Date endDate, Pageable pageable);

    public WeatherInfo getStationById(int id) ;

}
