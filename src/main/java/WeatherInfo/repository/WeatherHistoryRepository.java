package WeatherInfo.repository;

import WeatherInfo.model.WeatherInfo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WeatherHistoryRepository {

    @Value("${weatherInfo.csv.filename}")
    private String csvFileName;

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    Logger logger = LoggerFactory.getLogger(WeatherHistoryRepository.class);
    /**
     * Loads station data from application resource property file and generate Map for it
     * @return Map of StationId as key and its corresponding station object
     */
    public Map<Integer,WeatherInfo> loadAllSationsFromCsv() {

        Map<Integer,WeatherInfo> weatherInfoMap = new HashMap<>();
        BufferedReader br = null;

        logger.info("Reading data from CSV file :" + csvFileName);
        try {
            InputStream is = new FileInputStream(ResourceUtils.getFile(csvFileName));
            br = new BufferedReader(new InputStreamReader(is));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        if(br!=null){
            CsvToBean<WeatherInfo> csvToBean = new CsvToBeanBuilder<WeatherInfo>(br)
                    .withType(WeatherInfo.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                    .build();

            List<WeatherInfo> stationsList = csvToBean.parse();

            int i = 1;
            for(WeatherInfo s: stationsList) {
                s.setStationId(i);
                weatherInfoMap.put(i,s);
                i++;
            }
        }

        logger.info("Map Generated with size :" + weatherInfoMap.size());
        return weatherInfoMap;
    }


}
