package com.trendingtech.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trendingtech.service.TicketsService;
import com.trendingtech.util.ExcelFileUtil;

@RestController
@RequestMapping("/excel")
public class ExcelDBRestController {

	private static final Logger log = LoggerFactory.getLogger(ExcelFileUtil.class);

	@Autowired
	private TicketsService ticketService;
	
	@GetMapping("/db")
	public void writeDataToDB() {
		String fileName = "C:\\Users\\kvo4kor\\data.xlsx";
		try {
			ticketService.writeExcelToDB(new FileInputStream(new File(fileName)));
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}
	
	@PostMapping("/uploadExcel")
	public void uploadExcelToDB(@RequestPart("excelfile") MultipartFile excelfile) {
		log.error("Upload excel");
		ticketService.uploadExcelToDB(excelfile);
	}

	
	@GetMapping(value = "/downloadExcel" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadTicketsReports() {
		
		return ticketService.downloadTicketsReports();
	}
	
	@GetMapping(value="/exportDB",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public Resource exportDBToExcel() {
		
		return ticketService.exportDBToExcel();
	}
}