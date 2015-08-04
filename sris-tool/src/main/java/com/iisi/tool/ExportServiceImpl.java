/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package com.iisi.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 */

@Service("exportService")
public class ExportServiceImpl implements ExportService {

	private transient Logger LOG = LoggerFactory
			.getLogger(ExportServiceImpl.class);

	//
	@Autowired
	private transient ReadCompent readCompent;

	@Autowired
	private transient FileExport fileExport;

	@Autowired
	private ZipComent zipComent;

	private static final String ORG_LOCATION = "D:\\code\\sris-code";

	public void export(DTO dto) {

		try {

			File folder = new File(ORG_LOCATION);
			LOG.info(folder.getAbsolutePath());

			this.search(folder, dto);

		} catch (Exception e) {
			LOG.error("e={}", e);

		}

	}

	public static void main(String[] args) {
		ExportServiceImpl service = new ExportServiceImpl();
		// service.search(new File("D:\\code\\sris-code"));
	}

	public void search(File f, DTO dto) {
		if (f.isFile()) {
			LOG.info("file name:{}", f);
			this.proFile(f, dto);
		} else {
			for (File eachFile : f.listFiles()) {
				this.search(eachFile, dto);
			}
		}
	}

	private void makeZip(DTO dto) {

		try {
			final String path = FacesContext.getCurrentInstance()
					.getExternalContext().getResource("/resources/out/")
					.getPath();

			final String zipath = FacesContext.getCurrentInstance()
					.getExternalContext().getResource("/resources/zip/")
					.getPath();

			File folder = new File(path);
			folder.deleteOnExit();

			File outFile = new File(zipath, "/out.zip");
			outFile.deleteOnExit();

			zipComent.makeZip(folder, outFile);
		} catch (MalformedURLException e) {
			LOG.error("e={}", e);
		}

	}

	/**
	 * 透過來源檔案建立，建立新的java
	 * 
	 * @param proFile
	 * @param dto
	 */
	private void proFile(File proFile, DTO dto) {

		LOG.info("in file :{}", proFile.getAbsoluteFile());

		String content = this.readCompent.getContentBy(proFile
				.getAbsolutePath());

		content = content.replaceAll(dto.getOldFun(), dto.getNewFun());
		content = content.replaceAll(dto.getOldTable(), dto.getNewTable());
		content = content.replaceAll(dto.getOldRec(), dto.getNewRec());

		try {

			String path = proFile.getAbsolutePath();
			path=path.replace(ORG_LOCATION, dto.getOutPath());
			path=path.replace(dto.getOldFun(), dto.getNewFun());

			LOG.info("out file :{}", path);

			dto.getFiles().add(new DownLoadDTO(path, new File(path).getName()));

			fileExport.export(content, path);
			dto.setNewOutPutPath(path);

		} catch (Exception e) {
			LOG.error("e={}", e);
		}

	}
}
