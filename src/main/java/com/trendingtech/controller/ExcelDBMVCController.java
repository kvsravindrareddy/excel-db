package com.trendingtech.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trendingtech.entity.Tickets;
import com.trendingtech.service.TicketsService;
import com.trendingtech.util.ExcelFileUtil;

@Controller
public class ExcelDBMVCController {

	private static final Logger log = LoggerFactory.getLogger(ExcelFileUtil.class);

	@Autowired
	private TicketsService ticketService;

	@GetMapping("/db")
	public void writeDataToDB() {
		System.err.println("-------------");
		String fileName = "C:\\Users\\kvo4kor\\data.xlsx";
		try {
			ticketService.writeExcelToDB(new FileInputStream(new File(fileName)));
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}

	@PostMapping("/uploadExcel")
	public String uploadExcelToDB(@RequestParam("excelfile") MultipartFile excelfile,
			RedirectAttributes redirectAttributes) {
		// log.error("Upload excel");
		ticketService.uploadExcelToDB(excelfile);
		redirectAttributes.addFlashAttribute(excelfile.getOriginalFilename());
		return "redirect:/getTickets";
	}

	@GetMapping(value = "/getTickets", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ModelAndView exportDBToExcel() {
		List<Tickets> tickets = ticketService.getTicketsData();
		Map<String, Object> params = new HashMap<>();
		params.put("tickets", tickets);
		return new ModelAndView("tickets", params);
	}

}