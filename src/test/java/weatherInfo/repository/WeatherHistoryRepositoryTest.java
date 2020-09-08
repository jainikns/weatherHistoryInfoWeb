package weatherInfo.repository;

import WeatherInfo.model.WeatherInfo;
import WeatherInfo.repository.WeatherHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WeatherHistoryRepositoryTest {

    @InjectMocks
    WeatherHistoryRepository repository;

    @Before
    public void setup(){
        repository.setCsvFileName("src/test/resources/eng-climate-summary.csv");
    }

    @Test
    public void testLoadDataFromCsv(){


        Map<Integer, WeatherInfo> weatherInfoMap = repository.loadAllSationsFromCsv();

        assertEquals(9,weatherInfoMap.size());
        assertEquals(new Integer(1),weatherInfoMap.get(1).getStationId());
    }

    @Test
    public void testLoadDataFromCsvNotFOund(){
        repository.setCsvFileName("eng-climate-summary1.csv");
        Map<Integer, WeatherInfo> weatherInfoMap = repository.loadAllSationsFromCsv();

        assertEquals(0,weatherInfoMap.size());
    }
}
