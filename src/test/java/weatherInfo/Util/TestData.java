package weatherInfo.Util;

import WeatherInfo.model.WeatherInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestData {

    public static Page<WeatherInfo> populateWeatherHistoryPage() throws ParseException {

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<WeatherInfo> weatherInfoList = new ArrayList<>();
        Date date =  new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Populate mocked station
        for(int i = 1; i <= 100; i++) {
            WeatherInfo weatherInfo = new WeatherInfo();
            weatherInfo.setStationId(i);
            weatherInfo.setStationName("station"+i);
            weatherInfo.setWeatherDate(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            weatherInfoList.add(weatherInfo);
        }

        return new PageImpl<WeatherInfo>(weatherInfoList.subList(0,10), pageRequest, weatherInfoList.size());
    }

    public static Map<Integer,WeatherInfo> populateWeatherHistoryMap() throws ParseException {


        Map<Integer,WeatherInfo> weatherInfoMap = new Hashtable<>();

        Date date =  new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Populate mocked station
        for(int i = 1; i <= 100; i++) {
            WeatherInfo weatherInfo = new WeatherInfo();
            weatherInfo.setStationId(i);
            weatherInfo.setStationName("station"+i);
            weatherInfo.setWeatherDate(calendar.getTime());
            calendar.add(Calendar.DATE, 1);

            weatherInfoMap.put(i,weatherInfo);
        }

        return weatherInfoMap;
    }
}
