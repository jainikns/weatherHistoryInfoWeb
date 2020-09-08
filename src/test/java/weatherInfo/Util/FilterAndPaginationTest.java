package weatherInfo.Util;

import WeatherInfo.Util.FilterAndPagination;
import WeatherInfo.model.WeatherInfo;
import WeatherInfo.repository.WeatherHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FilterAndPaginationTest {

    @InjectMocks
    FilterAndPagination filterAndPagination;

    @Test
    public void testFilterAndPaginateStationDataWithDates() throws ParseException {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-08");

        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(TestData.populateWeatherHistoryMap().values());
        Page<WeatherInfo> page  = filterAndPagination.filterAndPaginateStationData(startDate,endDate,stationsList,pageRequest);

        assertEquals(8,page.getContent().size());
    }

    @Test
    public void testFilterAndPaginateStationDataWithoutDates() throws ParseException {

        PageRequest pageRequest = PageRequest.of(0, 50);


        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(TestData.populateWeatherHistoryMap().values());
        Page<WeatherInfo> page  = filterAndPagination.filterAndPaginateStationData(null,null,stationsList,pageRequest);

        assertEquals(50,page.getContent().size());
    }

    @Test
    public void testFilterAndPaginateStationDataWithStartDate() throws ParseException {

        PageRequest pageRequest = PageRequest.of(0, 50);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-30");

        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(TestData.populateWeatherHistoryMap().values());
        Page<WeatherInfo> page  = filterAndPagination.filterAndPaginateStationData(startDate,null,stationsList,pageRequest);

        assertEquals(40,page.getContent().size());
    }


    @Test
    public void testFilterAndPaginateStationDataWithEndDate() throws ParseException {

        PageRequest pageRequest = PageRequest.of(0, 50);

        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-20");

        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(TestData.populateWeatherHistoryMap().values());
        Page<WeatherInfo> page  = filterAndPagination.filterAndPaginateStationData(null,endDate,stationsList,pageRequest);

        assertEquals(20,page.getContent().size());
    }

    @Test
    public void testFilterAndPaginateStationDataWithPagination() throws ParseException {

        PageRequest pageRequest = PageRequest.of(3, 50);

        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-20");

        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(TestData.populateWeatherHistoryMap().values());
        Page<WeatherInfo> page  = filterAndPagination.filterAndPaginateStationData(null,endDate,stationsList,pageRequest);

        assertEquals(0,page.getContent().size());
    }

}
