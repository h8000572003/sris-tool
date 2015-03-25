package com.iisi.web;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iisi.tool.DTO;
import com.iisi.tool.ExportService;

@ManagedBean
@RequestScoped
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

		// ExternalContext externalContext = FacesContext.getCurrentInstance()
		// .getExternalContext();
		// String directory =
		// externalContext.getInitParameter("uploadDirectory")
		// + File.separator + "temp/";
		//
		// dto.setNewFun("aaaa");
		// InputStream stream = ((ServletContext) FacesContext
		// .getCurrentInstance().getExternalContext().getContext())
		// .getResourceAsStream("/resources/demo/images/optimus.jpg");
		// FacesContext.getCurrentInstance().getExternalContext().getr

		// file = new DefaultStreamedContent(stream, "image/jpg",
		// "downloaded_optimus.jpg");

		// String relativeWebPath = "/resources/codes/";
		// ServletContext servletContext = (ServletContext) FacesContext
		// .getCurrentInstance().getExternalContext().getContext();
		// String absoluteDiskPath =
		// servletContext.getRealPath(relativeWebPath);
		//
		// File f = new File(absoluteDiskPath);

	

		LOG.info("ExportController create");
	}

	public DTO getDto() {
		return dto;
	}

	public void setDto(DTO dto) {
		this.dto = dto;
	}

	public void send() {
		LOG.info("send");
		this.exportService.export(dto);
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
