package com.iisi.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;

import com.iisi.tool.domain.TaskReportDTO;
import com.iisi.util.TemplateCopyCompnet;

public class TaskReportGernerate extends AbstractGenerate {

	private TaskReportDTO dto;

	public TaskReportGernerate(TaskReportDTO dto) {
		super();
		this.dto = dto;
	}

	@Override
	public void generate(HSSFWorkbook book) {

		HSSFSheet sheet = book.createSheet(dto.getFuncode());

		this.creatCell(book, sheet, dto);

		int max= this.getMaxSize(dto);
		sheet.addMergedRegion(new Region(1, (short) 0,max, (short) 0));

		for (int i = 0; i < max; i++) {
			sheet.autoSizeColumn((short) i);
		}
		

	}

	private int getMaxSize(TaskReportDTO dto) {
		int max = -1;
		for (Entry<String, List<String>> entry : dto.getMap().entrySet()) {
			max = entry.getValue().size() > max ? entry.getValue().size() : max;
		}
		return max;

	}

	public static void main(String[] args) {
		TaskReportDTO dto = new TaskReportDTO();

		for (int i = 0; i < 9; i++) {
			List<String> contract = new ArrayList<String>();
			for (int value = 0; value < 30; value++) {
				contract.add(value + "");
			}
			dto.getMap().put(i + "", contract);
		}
		dto.getMap().put("0", Arrays.asList(new String[] { "出生申請書職權更正" }));

		dto.setFuncode("RL0X301");

		AbstractGenerate generate = new TaskReportGernerate(dto);
		generate.create("D:\\sris-tool-resource\\ITI", "124.xls");

	}

	private void creatCell(HSSFWorkbook book, HSSFSheet sheet, TaskReportDTO dto) {

		HSSFPalette palette = book.getCustomPalette();
		short colorIndex = 45;
		palette.setColorAtIndex(colorIndex, (byte) 253, (byte) 233, (byte) 217);

		HSSFFont font = book.createFont();
		font.setFontName("標楷體");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		CellStyle tCs = book.createCellStyle();
		tCs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		tCs.setFillForegroundColor(colorIndex);
		tCs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		tCs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		tCs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		tCs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		tCs.setFont(font);
		tCs.setAlignment(CellStyle.ALIGN_CENTER);
		tCs.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		HSSFFont cotnetFont = book.createFont();
		cotnetFont.setFontName("標楷體");

		CellStyle cotnet = book.createCellStyle();

		cotnet.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		cotnet.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cotnet.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cotnet.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cotnet.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cotnet.setFont(cotnetFont);

		final String[] titles = new String[] { //
				"作業代碼", "作業名稱", "畫面", "程式代號", "代碼檔(properties)", "資料庫Table(含RSCD Table)", "報表代號", "檔案代號", "測試個案代號" };

		for (int i = 0; i < 5000; i++) {
			HSSFRow titleRow = sheet.createRow(i);

			if (i == 0) {
				for (int j = 0; j < titles.length; j++) {
					HSSFCell title = titleRow.createCell(j);
					title.setCellType(HSSFCell.CELL_TYPE_STRING);
					title.setCellValue(titles[j]);
					title.setCellStyle(tCs);

				}

			} else {
				for (int subTypeIndex = 0; subTypeIndex < titles.length; subTypeIndex++) {
					HSSFCell title = titleRow.createCell(subTypeIndex);
					title.setCellType(HSSFCell.CELL_TYPE_STRING);
					title.setCellStyle(cotnet);

					List<String> values = dto.getMap().get(subTypeIndex + "");

					if (i > values.size()) {
						title.setCellValue("");
					} else {
						title.setCellValue(values.get(i - 1));
					}

				}
			}

		}

	}

	private void creatCell(HSSFSheet sheet, int location, String name, List<String> list) {

		HSSFRow titleRow = sheet.createRow(0);

		HSSFCell title = titleRow.createCell(location);
		title.setCellType(HSSFCell.CELL_TYPE_STRING);
		title.setCellValue(name);

		for (int i = 0; i < list.size(); i++) {
			HSSFRow cotnentRowF = sheet.createRow(i + 1);
			HSSFCell contentCell = cotnentRowF.createCell(location);
			contentCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			contentCell.setCellValue(list.get(i));
		}
	}

}
