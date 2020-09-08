package weatherInfo.service;

import WeatherInfo.Service.WeatherInfoServiceImpl;
import WeatherInfo.Util.FilterAndPagination;
import WeatherInfo.model.WeatherInfo;
import WeatherInfo.repository.WeatherHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import weatherInfo.Util.TestData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherInfoServiceImplTest {

    @Mock
    WeatherHistoryRepository repository;

    @Mock
    FilterAndPagination filterAndPagination;

    @InjectMocks
    WeatherInfoServiceImpl service;

    @Before
    public void setup(){
    }

    @Test
    public void testHistoricWeatherData() throws ParseException {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-08");

        Map<Integer,WeatherInfo> weatherInfoMap = TestData.populateWeatherHistoryMap();
        when(repository.loadAllSationsFromCsv()).thenReturn(weatherInfoMap);

        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(weatherInfoMap.values());

        when(filterAndPagination.filterAndPaginateStationData(startDate,endDate,stationsList,pageRequest)).thenReturn(TestData.populateWeatherHistoryPage());

        Page<WeatherInfo>  page =  service.getHistoricWeatherData(startDate,endDate,pageRequest);

        assertEquals(10,page.getContent().size());
        assertEquals("station1",page.getContent().get(0).getStationName());

    }

    @Test
    public void testHistoricWeatherDataWithEmptyMap() throws ParseException {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-08");

        Map<Integer,WeatherInfo> weatherInfoMap = new Hashtable<>();
        when(repository.loadAllSationsFromCsv()).thenReturn(weatherInfoMap);

        ArrayList<WeatherInfo> stationsList = new ArrayList<WeatherInfo>(weatherInfoMap.values());

        Page<WeatherInfo> pageResponse = new PageImpl<WeatherInfo>(new ArrayList<>(), pageRequest, 0);
        when(filterAndPagination.filterAndPaginateStationData(startDate,endDate,stationsList,pageRequest)).thenReturn(pageResponse);

        Page<WeatherInfo>  page =  service.getHistoricWeatherData(startDate,endDate,pageRequest);

        assertEquals(0,page.getContent().size());

    }

    @Test
    public void testGetStationbyId() throws ParseException {

        Map<Integer,WeatherInfo> weatherInfoMap = TestData.populateWeatherHistoryMap();
        when(repository.loadAllSationsFromCsv()).thenReturn(weatherInfoMap);

        WeatherInfo result =  service.getStationById(5);

        assertEquals(new Integer(5),result.getStationId());
        assertEquals("station5",result.getStationName());

    }



}
