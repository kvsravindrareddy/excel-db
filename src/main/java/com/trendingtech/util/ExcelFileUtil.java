package com.trendingtech.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trendingtech.data.TicketsData;
import com.trendingtech.entity.Tickets;

public class ExcelFileUtil {

	private static final Logger log = LoggerFactory.getLogger(ExcelFileUtil.class);

	public static List<TicketsData> readExcelFile(InputStream fis) {
		log.info("Before mapping the excel file data to TicketsData object");
		List<TicketsData> list = new ArrayList<TicketsData>();
		try {
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet("data");
			int rowNum = sh.getPhysicalNumberOfRows();
			log.debug("Number of rows identified in excel sheet " + rowNum);

			DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd-MMM-YYYY");

			for (int i = 1; i < rowNum; i++) {
				TicketsData ticketsData = new TicketsData();
				Row row = sh.getRow(i);
				int cellNum = row.getPhysicalNumberOfCells();
				for (int j = 0; j < cellNum; j++) {
					Cell cell = row.getCell(j);

					String columnName = sh.getRow(0).getCell(j).getStringCellValue();

					if (columnName.equalsIgnoreCase("Sl No")) {
						String slNo = NumberToTextConverter.toText(cell.getNumericCellValue());
						ticketsData.setSlno(Long.valueOf(slNo));
						log.info("***************************");
						log.info("----"+ticketsData.getSlno());
					}
					if (columnName.equalsIgnoreCase("Name")) {
						ticketsData.setName(cell.getStringCellValue());
					}
					if (columnName.equalsIgnoreCase("Ticket No")) {
						ticketsData.setTicketNo(cell.getStringCellValue());
					}
					if (columnName.contains("Tool Name")) {
						ticketsData.setToolName(cell.getStringCellValue());
					}
					if (columnName.contains("Category")) {
						ticketsData.setCategory(cell.getStringCellValue());
					}
					if (columnName.equalsIgnoreCase("Summary/Title")) {
						ticketsData.setTitle(cell.getStringCellValue());
					}
					if (columnName.equalsIgnoreCase("Received Date")) {
						ticketsData.setRecDate(
								cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					}
					if (columnName.equalsIgnoreCase("Closed Date")) {
						ticketsData.setCloDate(
								cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					}
					if (columnName.equalsIgnoreCase("Status")) {
						ticketsData.setStatus(cell.getStringCellValue());
					}
				}
				list.add(ticketsData);
			}
		} catch (IOException ioe) {
			log.error("Exception occured while reading excel file " + ioe.getMessage());
		}
		log.info("After mapping the excel file data to TicketsData object");
		return list;
	}
	
	public static ByteArrayInputStream exportToExcel(List<Tickets> list) {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("dbdata");
		FileOutputStream fos = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String filePath = "C:\\Users\\kvo4kor\\Downloads\\excelData/test-out.xlsx";
		try {
			fos = new FileOutputStream(new File(filePath));
			String[] headers = { "slno", "Name", "TicketNo", "ToolName", "Category", "Title", "ReceivedDate",
					"ClosedDate", "Status" };
			XSSFRow headerRow = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				XSSFCell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				sheet.autoSizeColumn(i);
			} 
			
			for (int i = 0; i < list.size(); i++) {
				Tickets tkt = list.get(i);
				XSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(tkt.getSlno());
				row.createCell(1).setCellValue(tkt.getName());
				row.createCell(2).setCellValue(tkt.getTicketNo());
				row.createCell(3).setCellValue(tkt.getToolName());
				row.createCell(4).setCellValue(tkt.getCategory());
				row.createCell(5).setCellValue(tkt.getTitle());
				row.createCell(6).setCellValue(tkt.getRecDate());
				row.createCell(7).setCellValue(tkt.getCloDate());
				row.createCell(8).setCellValue(tkt.getStatus());
				sheet.autoSizeColumn(i);
						
			}
			wb.write(out);
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
}