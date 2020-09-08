package WeatherInfo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;


public class WeatherInfo {

    private Integer stationId;

    @CsvBindByPosition(position = 0)
    private String stationName;

    @CsvBindByPosition(position = 1)
    private String province;

    @CsvDate("dd/MM/yyyy")
    @CsvBindByPosition(position = 2)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date weatherDate;

    @CsvBindByPosition(position = 3)
    private String meanTemp;

    @CsvBindByPosition(position = 4)
    private String maxTemp;

    @CsvBindByPosition(position = 5)
    private String minTemp;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(Date weatherDate) {
        this.weatherDate = weatherDate;
    }

    public String getMeanTemp() {
        return meanTemp;
    }

    public void setMeanTemp(String meanTemp) {
        this.meanTemp = meanTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }
}
