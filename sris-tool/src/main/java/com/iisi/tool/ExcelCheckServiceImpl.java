package com.iisi.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExcelCheckServiceImpl implements ExcelCheckService {

	private static final String BASE_PATH = "F:\\sris-tool-resource";

	private static transient Logger LOG = LoggerFactory
			.getLogger(ExportController.class);

	public void exportExcel(ExcelDTO dto) {
		// TODO Auto-generated method stub
		List<ExcekTask> tasks = this.loadAllFun(BASE_PATH + "\\task.txt");
		dto.getChecks().clear();

		for (ExcekTask task : tasks) {
			ExcelCheckDTO checkDTO = new ExcelCheckDTO();
			checkDTO.setTaskCode(task.getFuncode());
			dto.getChecks().add(checkDTO);

		}

		for (ExcelCheckDTO check : dto.getChecks()) {

			LOG.info("task:{}", check.getTaskCode());
			this.check(check, dto);
		}
		this.write2Excel(dto.getChecks());

	}

	public static void main(String[] args) {
		ExcelCheckServiceImpl service = new ExcelCheckServiceImpl();
		service.exportExcel(new ExcelDTO());

//		Pattern rldfPattern = Pattern.compile(".*0X301.*");
//		if (rldfPattern.matcher("RL0X301.doc").matches()) {
//			LOG.info(" match!");
//		} else {
//			LOG.info("NO match!");
//		}
	}

	private void check(ExcelCheckDTO task, ExcelDTO dto) {
		this.checkPDF(task, dto);
		this.checkSRS(task, dto);
		this.checkTech(task, dto);
	}

	private String genCheck(String path, String keyWord) {
		File folder = new File(BASE_PATH + path);
		Pattern rldfPattern = Pattern.compile(".*" + keyWord + ".*");
		for (File f : folder.listFiles()) {
			if (rldfPattern.matcher(f.getName()).matches()) {
				LOG.info(f.getName() + " match!");
				return "Y";
			}
		}
		return "";

	}

	private void checkPDF(ExcelCheckDTO task, ExcelDTO dto) {
		task.setCheckPDF(this.genCheck("\\PDS\\", task.getTaskCode()));

	}

	private void checkSRS(ExcelCheckDTO task, ExcelDTO dto) {
		task.setCheckSRS(this.genCheck("\\SRS\\", task.getTaskCode()));
	}

	private void checkTech(ExcelCheckDTO task, ExcelDTO dto) {
		task.setCheckTec(this.genCheck("\\TEC\\", task.getTaskCode()));
	}

	private void write2Excel(List<ExcelCheckDTO> checks) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("檢查清單");

		HSSFRow row0 = sheet.createRow(0);

		HSSFCell title_1 = row0.createCell(0);
		title_1.setCellType(HSSFCell.CELL_TYPE_STRING);
		title_1.setCellValue("功能名稱");

		HSSFCell title_2 = row0.createCell(1);
		title_2.setCellType(HSSFCell.CELL_TYPE_STRING);
		title_2.setCellValue("PDS");

		HSSFCell title_3 = row0.createCell(2);
		title_3.setCellType(HSSFCell.CELL_TYPE_STRING);
		title_3.setCellValue("SRS");

		HSSFCell title_4 = row0.createCell(3);
		title_4.setCellType(HSSFCell.CELL_TYPE_STRING);
		title_4.setCellValue("教育");

		for (int i = 1; i < checks.size(); i++) {

			HSSFRow row = sheet.createRow(i);

			HSSFCell name = row.createCell(0);
			name.setCellType(HSSFCell.CELL_TYPE_STRING);
			name.setCellValue(checks.get(i).getTaskCode());

			HSSFCell cell1 = row.createCell(1);
			cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell1.setCellValue(checks.get(i).getCheckPDF());

			HSSFCell cell2 = row.createCell(2);
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell2.setCellValue(checks.get(i).getCheckSRS());

			HSSFCell cell3 = row.createCell(3);
			cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell3.setCellValue(checks.get(i).getCheckTec());

		}

		try {
			FileOutputStream fOut = new FileOutputStream(BASE_PATH
					+ "\\work.xls");
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<ExcekTask> loadAllFun(String path) {

		List<ExcekTask> excekTasks = new ArrayList<ExcekTask>();
		File f = new File(path);
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(f);
			br = new BufferedReader(fr);

			String strNum = "";
			while ((strNum = br.readLine()) != null) {

				ExcekTask task = new ExcekTask();
				task.setFuncode(strNum);
				excekTasks.add(task);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return excekTasks;
	}

}
