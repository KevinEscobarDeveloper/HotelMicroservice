package com.aleal.hotels.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "hotels")
@Data
public class HotelsServiceConfiguration {
	private String msg;
	private String buildVersion;
	private Map<String,String> mailDetails;
	
}
