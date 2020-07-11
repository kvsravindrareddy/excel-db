package com.trendingtech.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trendingtech.data.TicketsData;
import com.trendingtech.entity.Tickets;
import com.trendingtech.repo.TicketsRepo;
import com.trendingtech.util.ExcelFileUtil;

@Service
public class TicketsService {

	private static final Logger log = LoggerFactory.getLogger(TicketsService.class);

	@Autowired
	private TicketsRepo ticketsRepo;

	public void writeExcelToDB(InputStream is) {

		log.info("Before writing excel data into DB");
		List<Tickets> entityList = new ArrayList<>();

		List<TicketsData> list = ExcelFileUtil.readExcelFile(is);
		for (TicketsData ticketsData : list) {
			Tickets tkt = new Tickets();
			tkt.setSlno(ticketsData.getSlno());
			tkt.setName(ticketsData.getName());
			tkt.setTicketNo(ticketsData.getTicketNo());
			tkt.setToolName(ticketsData.getToolName());
			tkt.setCategory(ticketsData.getCategory());
			tkt.setTitle(ticketsData.getTitle());
			tkt.setRecDate(ticketsData.getRecDate());
			tkt.setCloDate(ticketsData.getCloDate());
			tkt.setStatus(ticketsData.getStatus());
			entityList.add(tkt);

		}

		log.debug("entitylist = " + entityList.size());

		ticketsRepo.saveAll(entityList);
		log.info("After writing excel data into DB");
	}

	public void uploadExcelToDB(MultipartFile excelfile) {
		try {
			writeExcelToDB(excelfile.getInputStream());
		} catch (IOException ioe) {

		}
	}

	public Resource exportDBToExcel() {

		List<Tickets> list = getTicketsData();
		String filePath = null;

		ByteArrayResource resource = null;
		try {
			resource = new ByteArrayResource(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resource;
	}

	public List<Tickets> getTicketsData() {
		return StreamSupport.stream(ticketsRepo.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public ResponseEntity<InputStreamResource> downloadTicketsReports() {
		List<Tickets> list = getTicketsData();
		ByteArrayInputStream in = ExcelFileUtil.exportToExcel(list);
	    
	    HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=tickets.xlsx");
	    
	     return ResponseEntity
	                  .ok()
	                  .headers(headers)
	                  .body(new InputStreamResource(in));
		
	}

}