package com.aleal.hotels.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aleal.hotels.model.Room;


@FeignClient("rooms")
public interface RoomsFeignClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "rooms/{id}", consumes = "application/json")
	public List<Room> searchByHotelId(@PathVariable long id);
	
}
