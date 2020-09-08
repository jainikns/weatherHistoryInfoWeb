package WeatherInfo.Controller;

import WeatherInfo.Service.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stations")
public class StationDetailsController {

	@Autowired
	private WeatherInfoService service;

	@GetMapping
	@RequestMapping("/{id}/{stationName}")
	public String getStationDetails(Model model, @PathVariable Integer id,@PathVariable String stationName) {
		if(id == null){
			return "error";
		}
		model.addAttribute("stationId", id);
		model.addAttribute("stationName", stationName);
		return "stationDetails";
	}

}
