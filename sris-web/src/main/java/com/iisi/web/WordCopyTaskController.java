package com.iisi.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.iisi.tool.WordCopyTaskDTO;
import com.iisi.tool.WordCopyTaskService;

@Controller
public class WordCopyTaskController implements Serializable {
	private WordCopyTaskDTO dto = new WordCopyTaskDTO();

	@Autowired
	private transient WordCopyTaskService service;

	@PostConstruct
	public void init() {

	}

	public void copy() {
		this.service.copy2CreateWord(dto);
	}

	public WordCopyTaskDTO getDto() {
		return dto;
	}

	public void setDto(WordCopyTaskDTO dto) {
		this.dto = dto;
	}

	public enum MapContent {

		//
		;
		private String key;
		private String value;
		
		
	}
}
