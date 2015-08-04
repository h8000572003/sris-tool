package com.iisi.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 */

@Controller("exControlller")
public class ExportController {

	private transient Logger LOG = LoggerFactory
			.getLogger(ExportController.class);

	@Autowired
	private ExportService exportService;

	private DTO dto = new DTO();

	public ExportService getExportService() {
		return this.exportService;
	}

	public void setExportService(ExportService exportService) {

		this.exportService = exportService;
	}

	public void export() {
		LOG.info("export..");
		this.exportService.export(dto);
	}

	public DTO getDto() {
		return this.dto;
	}

	public void setDto(DTO dto) {
		this.dto = dto;
	}

}
