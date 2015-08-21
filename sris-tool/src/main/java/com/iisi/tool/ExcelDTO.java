package com.iisi.tool;

import java.util.ArrayList;
import java.util.List;

public class ExcelDTO {
	private List<ExcelCheckDTO> checks = new ArrayList<ExcelCheckDTO>();

	public List<ExcelCheckDTO> getChecks() {
		return checks;
	}

	public void setChecks(List<ExcelCheckDTO> checks) {
		this.checks = checks;
	}


}
