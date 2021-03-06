package com.iisi.web;

import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iisi.tool.DTO;
import com.iisi.tool.DownLoadDTO;
import com.iisi.tool.ExportService;

//@ManagedBean
//@ViewScoped
public class ExportController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger LOG = LoggerFactory.getLogger(ExportController.class);

	private DTO dto = new DTO();

	@ManagedProperty(value = "#{exportService}")
	private transient ExportService exportService;

	private StreamedContent file;

	public ExportController() {

		LOG.info("ExportController begin..");

		dto.setOldTable("m02m");
		dto.setNewTable("m01m");

		dto.setOldRec("227");
		dto.setNewRec("228");

		dto.setOldFun("327");
		dto.setNewFun("328");

		LOG.info("ExportController end..");

	}

	public DTO getDto() {
		return dto;
	}

	public void setDto(DTO dto) {
		this.dto = dto;
	}

	public String send() {
		LOG.info("send");

		this.exportService.export(dto);

		return null;

	}

	public void downloadAll() {
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/resources/zip/out.zip");

		file = new DefaultStreamedContent(stream, "java/*", "out.zip");
	}

	public void download(DownLoadDTO download) {

		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/resources/out/" + download.getName());

		file = new DefaultStreamedContent(stream, "java/*",
				download.getName());

	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

}
