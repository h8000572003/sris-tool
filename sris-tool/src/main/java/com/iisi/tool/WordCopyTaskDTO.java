package com.iisi.tool;

import java.util.HashMap;
import java.util.Map;

public class WordCopyTaskDTO {
	private String templatePath = "";
	private String outPutPath = "";
	private Map<String, String> map = new HashMap<String, String>();
	

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getOutPutPath() {
		return outPutPath;
	}

	public void setOutPutPath(String outPutPath) {
		this.outPutPath = outPutPath;
	}

}
