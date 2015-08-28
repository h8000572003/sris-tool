/**
 * 
 */
package com.iisi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author ING
 *
 */
public class ExcelCopyCompnetImpl implements TemplateCopyCompnet {

	public static void main(String[] args) {
		TemplateCopyCompnet templateCopyCompnet = new ExcelCopyCompnetImpl();

		HashMap<String, String> map = new HashMap<String, String>();
		templateCopyCompnet.copyFromTemplate("D:\\test_replace_out\\ITS_RL_0X328.xls", map,
				"D:\\test_replace_out\\ITS_RL_0X328_bak.xls");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iisi.util.TemplateCopyCompnet#copyFromTemplate(java.lang.String,
	 * java.util.Map, java.lang.String)
	 */
	public void copyFromTemplate(String templatePath, Map<String, String> map, String outPath) {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(templatePath));
			Workbook workbook = new HSSFWorkbook(inputStream);
			workbook.createSheet("000");

			final String outPut = outPath;
			FileOutputStream fOut = new FileOutputStream(outPut);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
