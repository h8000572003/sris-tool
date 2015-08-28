package com.iisi.tool;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractGenerate {

	private static transient Logger LOG = LoggerFactory.getLogger(ExportController.class);
	
	

	public GenerateInfo create(String outFile, String fileName) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		this.generate(workbook);

		try {
			final String outPut = outFile + "\\" + fileName;
			FileOutputStream fOut = new FileOutputStream(outPut);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			LOG.info("create new xls:{}", outPut);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("e:{}", e.getMessage(), e);
		}
		return new GenerateInfo(outFile, fileName, workbook);

	}

	public abstract void generate(HSSFWorkbook book);
}
