package WeatherInfo.Service;


import WeatherInfo.Util.FilterAndPagination;
import WeatherInfo.model.WeatherInfo;
import WeatherInfo.repository.WeatherHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;


@Service
public class WeatherInfoServiceImpl implements WeatherInfoService{

    Logger logger = LoggerFactory.getLogger(WeatherInfoServiceImpl.class);

    Map<Integer,WeatherInfo> weatherInfoMap = new Hashtable<>();

    @Autowired
    WeatherHistoryRepository weatherHistoryRepository;

    @Autowired
    FilterAndPagination filterAndPagination;

    public Page<WeatherInfo> getHistoricWeatherData(Date startdate, Date endDate, Pageable pageable){

        logger.info("Inside WeatherInfoServiceImpl getHistoricWeatherData");

        if(weatherInfoMap.isEmpty())
            weatherInfoMap = weatherHistoryRepository.loadAllSationsFromCsv();

        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(weatherInfoMap.values());

        return  filterAndPagination.filterAndPaginateStationData(startdate,endDate,stationsList,pageable);

    }



    public WeatherInfo getStationById(int id) {

        logger.info("Inside WeatherInfoServiceImpl getStationById");

        if(weatherInfoMap.isEmpty())
            weatherInfoMap = weatherHistoryRepository.loadAllSationsFromCsv();

        WeatherInfo weatherInfo = weatherInfoMap.get(id);

        return weatherInfo;
    }

}
