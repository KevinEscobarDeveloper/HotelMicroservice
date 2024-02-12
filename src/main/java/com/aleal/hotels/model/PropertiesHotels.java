package com.aleal.hotels.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertiesHotels {
	private String msg;
	private String buildVersion;
	private Map<String, String> mailDetails;

}
