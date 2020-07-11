package com.trendingtech.data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TicketsData {
	private long slno;
	private String name;
	private String ticketNo;
	private String toolName;
	private String category;
	private String title;
	@JsonFormat(pattern = "dd-MMM-YYYY")
	private LocalDate recDate;
	@JsonFormat(pattern = "dd-MMM-YYYY")
	private LocalDate cloDate;
	private String status;
	
	public long getSlno() {
		return slno;
	}
	public void setSlno(long slno) {
		this.slno = slno;
	} 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getRecDate() {
		return recDate;
	}
	public void setRecDate(LocalDate recDate) {
		this.recDate = recDate;
	}
	public LocalDate getCloDate() {
		return cloDate;
	}
	public void setCloDate(LocalDate cloDate) {
		this.cloDate = cloDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}