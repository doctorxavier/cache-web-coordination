package com.bcp.cache.coordination.model;

import java.io.Serializable;

import org.joda.time.DateTime;

public class RequestData implements Serializable {

	private static final long	serialVersionUID	= -4138808433562962748L;

	private String				uuid;
	private String				name;
	private String				info;
	private DateTime			date;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

}
