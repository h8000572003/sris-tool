package com.iisi.tool.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskReportDTO {
	private String funcode = "";
	private Map<String, List<String>> map = new HashMap<String, List<String>>();
	public String getFuncode() {
		return funcode;
	}
	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}
	public Map<String, List<String>> getMap() {
		return map;
	}
	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}



}
