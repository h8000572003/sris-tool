package com.iisi.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iisi.tool.WordCopyTaskDTO;

public class WordCopyTaskControllerTest {
	private transient WordCopyTaskController wordCopyTaskController;

	@Before
	public void testSetup() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		this.wordCopyTaskController = context
				.getBean(WordCopyTaskController.class);
	}

	@Test
	public void copy() {

		final WordCopyTaskDTO dto = new WordCopyTaskDTO();
		this.wordCopyTaskController.setDto(dto);
		
		dto.setTemplatePath("F:\\template\\template.doc");

		dto.setOutPutPath("F:\\template_out\\RL0X327.doc");
		dto.getMap().put("$title", "婚姻紀錄檔");
		this.wordCopyTaskController.copy();
		

		dto.setOutPutPath("F:\\template_out\\RL0X328.doc");
		dto.getMap().put("$title", "出生資料檔");
		this.wordCopyTaskController.copy();
		
		dto.setOutPutPath("F:\\template_out\\RL0X329.doc");
		dto.getMap().put("$title", "認領、收養記錄檔");
		this.wordCopyTaskController.copy();
		
		dto.setOutPutPath("F:\\template_out\\RL0X330.doc");
		dto.getMap().put("$title", "監護、輔助、未成年子女資料檔");
		this.wordCopyTaskController.copy();
		
		dto.setOutPutPath("F:\\template_out\\RL0X331.doc");
		dto.getMap().put("$title", "監護、輔助、未成年子女資料檔");
		this.wordCopyTaskController.copy();
		
		dto.setOutPutPath("F:\\template_out\\RL0X332.doc");
		dto.getMap().put("$title", "除戶婚姻紀錄檔");
		this.wordCopyTaskController.copy();


	}

}
