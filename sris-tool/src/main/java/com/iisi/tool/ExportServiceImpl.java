/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package com.iisi.tool;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void export(DTO dto) {

		try {
			String path = FacesContext.getCurrentInstance()
					.getExternalContext().getResource("/resources/codes/")
					.getPath();
			File folder = null;

			final List<File> files = new ArrayList<File>();

			for (File f : folder.listFiles()) {
				if (f.isFile()) {

					files.add(f);
				} else {
					for (File sf : f.listFiles()) {

						files.add(sf);

						// this.proFile(f, dto, null);
					}
				}

			}

			for (File proFile : files) {
				this.proFile(proFile, dto);
			}

		} catch (Exception e) {

		}

		// TODO Auto-generated method stub

	}

	private void proFile(File proFile, DTO dto) {

		// String newFileName = proFile.getName().replace(dto.getOldFun(),
		// dto.getNewFun());

		String content = this.readCompent.getContentBy(proFile
				.getAbsolutePath());

		content = content.replaceAll(dto.getOldFun(), dto.getNewFun());
		content = content.replaceAll(dto.getOldTable(), dto.getNewTable());
		content = content.replaceAll(dto.getOldRec(), dto.getNewRec());

		String newFileName = proFile.getAbsolutePath().replace(dto.getOldFun(),
				dto.getNewFun());
		newFileName = newFileName.replace(dto.getPath(), dto.getOutPath());

		fileExport.export(content, newFileName);

		LOG.debug("out file name={}", newFileName);
	}
	// ================================================
	// == [Enumeration types] Block Start
	// == [Enumeration types] Block End
	// ================================================
	// == [static variables] Block Start
	// == [static variables] Block Stop
	// ================================================
	// == [instance variables] Block Start
	// == [instance variables] Block Stop
	// ================================================
	// == [static Constructor] Block Start
	// == [static Constructor] Block Stop
	// ================================================
	// == [Constructors] Block Start (含init method)
	// == [Constructors] Block Stop
	// ================================================
	// == [Static Method] Block Start
	// == [Static Method] Block Stop
	// ================================================
	// == [Accessor] Block Start
	// == [Accessor] Block Stop
	// ================================================
	// == [Overrided Method] Block Start (Ex. toString/equals+hashCode)
	// == [Overrided Method] Block Stop
	// ================================================
	// == [Method] Block Start
	// ####################################################################
	// ## [Method] sub-block :
	// ####################################################################
	// == [Method] Block Stop
	// ================================================
	// == [Inner Class] Block Start
	// == [Inner Class] Block Stop
	// ================================================
}
