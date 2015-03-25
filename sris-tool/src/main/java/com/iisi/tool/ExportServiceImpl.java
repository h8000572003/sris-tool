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

	public void export(DTO dto) {

		try {
			String path = FacesContext.getCurrentInstance()
					.getExternalContext().getResource("/resources/codes/")
					.getPath();
			File folder = new File(path);
			folder.deleteOnExit();

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
			this.makeZip(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

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

			File outFile = new File(zipath, "/out.zip");
			outFile.deleteOnExit();

			zipComent.makeZip(folder, outFile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void proFile(File proFile, DTO dto) {

		// String newFileName = proFile.getName().replace(dto.getOldFun(),
		// dto.getNewFun());

		String content = this.readCompent.getContentBy(proFile
				.getAbsolutePath());

		content = content.replaceAll(dto.getOldFun(), dto.getNewFun());
		content = content.replaceAll(dto.getOldTable(), dto.getNewTable());
		content = content.replaceAll(dto.getOldRec(), dto.getNewRec());

		try {

			String path = FacesContext.getCurrentInstance()
					.getExternalContext().getResource("/resources/out/")
					.getPath();

			String outPut = path
					+ proFile.getName().replace(dto.getOldFun(),
							dto.getNewFun());

			;

			dto.getFiles().add(
					new DownLoadDTO(outPut, new File(outPut).getName()));

			fileExport.export(content, outPut);
			dto.setNewOutPutPath(outPut);
			LOG.debug("out file name={}", outPut);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
