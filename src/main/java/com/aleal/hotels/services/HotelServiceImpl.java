package com.aleal.hotels.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aleal.hotels.clients.RoomsFeignClient;
import com.aleal.hotels.dao.IHotelDao;
import com.aleal.hotels.model.Hotel;
import com.aleal.hotels.model.HotelRooms;
import com.aleal.hotels.model.Room;

@Service
public class HotelServiceImpl implements IHotelService {
	
	@Autowired
	private IHotelDao hotelDao;
	
	@Autowired
	private RestTemplate clientRest;

	@Autowired
	RoomsFeignClient roomFeignClient;
	
	@Override
	public List<Hotel> search() {
		return (List<Hotel>) hotelDao.findAll();
	}

	@Override
	public HotelRooms searchHotelById(long hotelId) {
		HotelRooms response = new HotelRooms();
		Optional<Hotel> hotel = hotelDao.findById(hotelId);
		
		//Rest Template
		/*Map<String, Long> pathVariable = new HashMap<String, Long>();
		pathVariable.put("id", hotelId);
		
		List<Room> rooms = Arrays.asList(clientRest.getForObject("http://localhost:8081/rooms/{id}", Room[].class, pathVariable));
		*/
		
		List<Room> rooms = roomFeignClient.searchByHotelId(hotelId);
		
		response.setHotelId(hotel.get().getHotelId());
		response.setHotelName(hotel.get().getHotelName());
		response.setHotelAddress(hotel.get().getHotelAddress());
		response.setRooms(rooms);
		
		
		return response;
	}
	
	@Override
	public HotelRooms searchHotelByIdWithoutRooms(long hotelId) {
		HotelRooms response = new HotelRooms();
		Optional<Hotel> hotel = hotelDao.findById(hotelId);

		
		response.setHotelId(hotel.get().getHotelId());
		response.setHotelName(hotel.get().getHotelName());
		response.setHotelAddress(hotel.get().getHotelAddress());
		
		
		return response;
	}
}
