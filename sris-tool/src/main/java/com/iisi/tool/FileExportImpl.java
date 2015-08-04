/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package com.iisi.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class FileExportImpl implements FileExport {

	private transient Logger LOG = LoggerFactory
			.getLogger(FileExportImpl.class);



	public void export(String cotnet, String newName) {
		File file = new File(newName);
		if (!new File(newName).exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileWriter fw;
		try {

			fw = new FileWriter(newName);
			fw.write(cotnet);

			fw.flush();

			fw.close();
		} catch (IOException e) {
			LOG.error("error:{}", e);
		}

		;

	}
}
