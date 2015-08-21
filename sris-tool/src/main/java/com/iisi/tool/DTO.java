/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package com.iisi.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 *
 */
public class DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private String outPath;

	private String oldFun;
	private String newFun;

	private String oldTable;
	private String newTable;

	private String oldRec;
	private String newRec;
	private String newOutPutPath = "";

	private List<DownLoadDTO> files = new ArrayList<DownLoadDTO>();

	public List<DownLoadDTO> getFiles() {
		return files;
	}

	public void setFiles(List<DownLoadDTO> files) {
		this.files = files;

	}

	public String getOldTable() {
		return this.oldTable;
	}

	public void setOldTable(String oldTable) {
		this.oldTable = oldTable;
//		this.setChanged(this);
	}

	public String getNewTable() {
		return this.newTable;

	}

	public void setNewTable(String newTable) {
		this.newTable = newTable;
//		this.setChanged(this);
	}

	public String getOldFun() {
		return this.oldFun;
	}

	public void setOldFun(String oldFun) {
		this.oldFun = oldFun;
//		this.setChanged(this);
	}

	public String getNewFun() {
		return this.newFun;
	}

	public void setNewFun(String newFun) {
		this.newFun = newFun;
//		this.setChanged(this);
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
//		this.setChanged(this);
	}

	public String getOutPath() {
		return this.outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
//		this.setChanged(this);
	}

	public String getOldRec() {
		return this.oldRec;
	}

	public void setOldRec(String oldRec) {
		this.oldRec = oldRec;
//		this.setChanged(this);
	}

	public String getNewRec() {
		return this.newRec;
	}

	public void setNewRec(String newRec) {
		this.newRec = newRec;
//		this.setChanged(this);
	}

	public String getNewOutPutPath() {
		return newOutPutPath;
	}

	public void setNewOutPutPath(String newOutPutPath) {
		this.newOutPutPath = newOutPutPath;
//		this.setChanged(this);
	}

}
