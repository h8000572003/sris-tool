package com.iisi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.iisi.tool.ExcelCheckService;
import com.iisi.tool.ExcelDTO;

@Controller
public class ExcelController  {

	@Autowired
	private transient ExcelCheckService excelCheckService;
	
	private ExcelDTO dto=new ExcelDTO();

	

	public ExcelCheckService getExcelCheckService() {
		return excelCheckService;
	}

	public void setExcelCheckService(ExcelCheckService excelCheckService) {
		this.excelCheckService = excelCheckService;
	}

	public ExcelDTO getDto() {
		return dto;
	}

	public void setDto(ExcelDTO dto) {
		this.dto = dto;
	}

	

}
