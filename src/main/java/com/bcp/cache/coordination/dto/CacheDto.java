package com.bcp.cache.coordination.dto;

import java.io.Serializable;

import com.googlecode.jmapper.annotations.JMap;

public class CacheDto implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@JMap
	private int					id;
	
	@JMap
	private String				code;
	
	@JMap
	private String				name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
