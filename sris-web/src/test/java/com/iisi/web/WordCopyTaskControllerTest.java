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
		dto.setTemplatePath("F:\\template\\template.doc");
		dto.setOutPutPath("F:\\template_out.doc");
		dto.getMap().put("$title", "new title");

		this.wordCopyTaskController.setDto(dto);

		this.wordCopyTaskController.copy();

	}
}
