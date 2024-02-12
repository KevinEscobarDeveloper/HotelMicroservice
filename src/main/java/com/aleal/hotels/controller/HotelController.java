package com.aleal.hotels.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.aleal.hotels.config.HotelsServiceConfiguration;
import com.aleal.hotels.model.Hotel;
import com.aleal.hotels.model.HotelRooms;
import com.aleal.hotels.model.PropertiesHotels;
import com.aleal.hotels.services.IHotelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class HotelController {
	
	
	@Autowired
	private IHotelService service;
	
	@Autowired
	private HotelsServiceConfiguration configHotels;
	
	
	@GetMapping("hotels")
	public List<Hotel> search(){
		log.info("Inicio de metodo search");
		return (List<Hotel>) this.service.search();	
	}
	
	
	@GetMapping("hotels/{hotelId}")
	//@CircuitBreaker(name="searchHotelByIdSupportCB", fallbackMethod = "searchHotelByIdAlternative")
	@Retry(name = "searchHotelByIdSupportRetry", fallbackMethod = "searchHotelByIdAlternative")
	public HotelRooms searchHotelById(@PathVariable long hotelId){
		log.info("inicio de metodo searchById");
		return this.service.searchHotelById(hotelId);	
	}
	
	public HotelRooms searchHotelByIdAlternative(@PathVariable long hotelId, Throwable thr) {
		return this.service.searchHotelByIdWithoutRooms(hotelId);	
	}
	
	@GetMapping("/hotels/read/properties")
	public String getPropertiesHotels() throws JsonProcessingException {
		ObjectWriter owj = new ObjectMapper().writer().withDefaultPrettyPrinter();
		PropertiesHotels propHotels = new PropertiesHotels(configHotels.getMsg(), configHotels.getBuildVersion(), configHotels.getMailDetails());
		String jsonString = owj.writeValueAsString(propHotels);
		return jsonString;
	}

}
