package WeatherInfo.Util;

import WeatherInfo.model.WeatherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class FilterAndPagination {

    Logger logger = LoggerFactory.getLogger(FilterAndPagination.class);

    /**
     *
     * @param startDate
     * @param endDate
     * @param stationsList
     * @param pageable
     * @return station list page by page
     */
    public Page<WeatherInfo> filterAndPaginateStationData(Date startDate, Date endDate, List<WeatherInfo> stationsList,Pageable pageable){

        logger.info("Inside filterAndPaginateStationData");
        filterStationsByDates(startDate,endDate,stationsList);
        return getPage(stationsList, pageable);

    }

    /**
     * Filters list of stations within the given date range.
     * Both start end dates are inclusive
     *
     * @param startDate
     * @param endDate
     * @param stationsList
     */
    private void filterStationsByDates(Date startDate, Date endDate, List<WeatherInfo> stationsList) {

        if(startDate != null || endDate != null) {

            logger.info("Filtering station based on dates : " + startDate + " and " + endDate);
            Iterator<WeatherInfo> iterator = stationsList.iterator();
            while (iterator.hasNext()) {
                WeatherInfo weatherInfo = iterator.next();
                if(weatherInfo.getWeatherDate() == null) {
                    iterator.remove();
                    continue;
                }

                if(startDate != null && weatherInfo.getWeatherDate().getTime() < startDate.getTime()) {
                    iterator.remove();
                    continue;
                }

                if(endDate != null && weatherInfo.getWeatherDate().getTime() > endDate.getTime()) {
                    iterator.remove();
                    continue;
                }
            }
        }
    }

    /**
     *
     * @param stationList
     * @param pageable
     * @return
     */
    private Page<WeatherInfo> getPage(List<WeatherInfo> stationList, Pageable pageable) {
        // Filter the list based on the page being viewed
        int pageNo = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int startIndex = pageNo*size;

        logger.info("Showing page : " + pageNo + " with records :  " + size);

        List<WeatherInfo> pageOfStations = new ArrayList<WeatherInfo>();
        for(int i = startIndex; i < (startIndex+size); i++) {
            if(i > (stationList.size() - 1)) {
                break;
            }

            pageOfStations.add(stationList.get(i));
        }

        // Construct a response that has page information
        PageImpl<WeatherInfo> page = new PageImpl<WeatherInfo>(pageOfStations, pageable, stationList.size());

        return page;
    }
}
