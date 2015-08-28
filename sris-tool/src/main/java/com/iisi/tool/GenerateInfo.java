package com.iisi.tool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GenerateInfo {
	private String outPath = "";
	private String fileName = "";
	private HSSFWorkbook book;
	public GenerateInfo(String outPath, String fileName, HSSFWorkbook book) {
		super();
		this.outPath = outPath;
		this.fileName = fileName;
		this.book = book;
	}
	public String getOutPath() {
		return outPath;
	}
	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public HSSFWorkbook getBook() {
		return book;
	}
	public void setBook(HSSFWorkbook book) {
		this.book = book;
	}
	
	
}
