package com.trendingtech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppData {
	private String excelFileName;
	
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
}