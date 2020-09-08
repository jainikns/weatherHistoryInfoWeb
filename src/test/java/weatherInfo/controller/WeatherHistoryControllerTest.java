package weatherInfo.controller;

import WeatherInfo.Controller.StationDetailsController;
import WeatherInfo.Controller.WeatherHistoryController;
import WeatherInfo.Controller.WeatherHomeController;
import WeatherInfo.Service.WeatherInfoService;
import WeatherInfo.model.WeatherInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import weatherInfo.Util.TestData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class WeatherHistoryControllerTest {

    @Autowired
    WeatherHistoryController controller;

    @Autowired
    StationDetailsController stationController;

    @Autowired
    WeatherHomeController weatherHomeController;
    @Mock
    WeatherInfoService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MockMvc stationDetailMvc;

    @Autowired
    private MockMvc homeMvc;

    @Before
    public void setup(){
        service = Mockito.spy(WeatherInfoService.class);
        controller = new WeatherHistoryController(service);
        stationController = new StationDetailsController();
        weatherHomeController = new WeatherHomeController();

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
        stationDetailMvc = MockMvcBuilders.standaloneSetup(stationController).build();
        homeMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

    }
    @Test
    public void getWeatherHistoryWithDate() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-08");

        Mockito.when(controller.getWeatherInfoService().getHistoricWeatherData(startDate, endDate, pageRequest)).thenReturn(TestData.populateWeatherHistoryPage());

        MockHttpServletResponse response = mvc.perform(
                get("/weatherInfo?page=0&size=10&startDate=2018-10-01&endDate=2018-10-08")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertNotNull(response);
        assertEquals(200,response.getStatus());
    }

    @Test
    public void getWeatherHistoryWithoutDate() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-08");

        Mockito.when(controller.getWeatherInfoService().getHistoricWeatherData(null, null, pageRequest)).thenReturn(TestData.populateWeatherHistoryPage());

        MockHttpServletResponse response = mvc.perform(
                get("/weatherInfo?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertNotNull(response);
        assertEquals(200,response.getStatus());
    }

    @Test
    public void getWeatherHistoryWithoutPagination() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-08");

        Mockito.when(controller.getWeatherInfoService().getHistoricWeatherData(null, null, pageRequest)).thenReturn(TestData.populateWeatherHistoryPage());

        MockHttpServletResponse response = mvc.perform(
                get("/weatherInfo")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertNotNull(response);
        assertEquals(200,response.getStatus());
    }

    @Test
    public void getWeatherHistoryWithWrongUrl() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-08");

        Mockito.when(controller.getWeatherInfoService().getHistoricWeatherData(null, null, pageRequest)).thenReturn(TestData.populateWeatherHistoryPage());

        MockHttpServletResponse response = mvc.perform(
                get("/weatherInfo1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(404,response.getStatus());
    }

    @Test
    public void getStationDetailWithStationID() throws Exception {

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setStationId(1);
        weatherInfo.setStationName("stationName");
        weatherInfo.setWeatherDate(new Date());
        weatherInfo.setMeanTemp("12");
        weatherInfo.setMaxTemp("23");
        weatherInfo.setMinTemp("7");

        Mockito.when(controller.getWeatherInfoService().getStationById(1)).thenReturn(weatherInfo);

        MockHttpServletResponse response = mvc.perform(
                get("/weatherInfo/stations/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200,response.getStatus());
        assertNotNull(response);
    }

    @Test
    public void getStationDetailWithoutStationID() throws Exception {

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setStationId(1);
        weatherInfo.setStationName("stationName");
        weatherInfo.setWeatherDate(new Date());
        weatherInfo.setMeanTemp("12");
        weatherInfo.setMaxTemp("23");
        weatherInfo.setMinTemp("7");

        Mockito.when(controller.getWeatherInfoService().getStationById(1)).thenReturn(weatherInfo);

        MockHttpServletResponse response = mvc.perform(
                get("/weatherInfo/stations/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(404,response.getStatus());
        assertNotNull(response);
    }

    @Test
    public void getStationDetailWithStationIdName() throws Exception {

        stationDetailMvc.perform(
                get("/stations/1/Name")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("stationDetails"));

    }

    @Test
    public void getWeatherHistoryHomePage() throws Exception {

        homeMvc.perform(
                get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("index"));


    }

}
