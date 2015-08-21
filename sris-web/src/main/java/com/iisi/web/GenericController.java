package com.iisi.web;

public class GenericController<DTO> {
	private DTO dto;

	public GenericController(DTO dto) {
		super();
		this.dto = dto;
	}

}
