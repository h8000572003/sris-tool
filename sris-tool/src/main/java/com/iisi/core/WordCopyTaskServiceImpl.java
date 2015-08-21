package com.iisi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iisi.tool.WordCopyTaskDTO;
import com.iisi.tool.WordCopyTaskService;
import com.iisi.util.WordCompnet;

@Service
public class WordCopyTaskServiceImpl implements WordCopyTaskService {

	@Autowired
	private transient WordCompnet wordCompnet;

	public void copy2CreateWord(WordCopyTaskDTO dto) {
		this.wordCompnet.copyFromTemplate(//
				dto.getTemplatePath(), dto.getMap(), dto.getOutPutPath());

	}

}
