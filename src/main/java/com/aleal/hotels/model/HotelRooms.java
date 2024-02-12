package com.aleal.hotels.model;

import java.util.List;

import lombok.Data;

@Data
public class HotelRooms {
	private long hotelId;
	private String hotelName;
	private String hotelAddress;
	private List<Room> rooms;

}
