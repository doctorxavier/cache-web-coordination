package com.bcp.cache.coordination.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.cache.coordination.manager.RequestManager;
import com.bcp.cache.coordination.model.RequestData;

@RestController
@RequestMapping(value = "/app/request")
public class RequestController {
	
	@Autowired
	private RequestManager requestManager;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public RequestData createRequestData() {
		return requestManager.createRequestData(new RequestData());
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public RequestData readRequestData(@RequestParam("uuid") String uuid) {
		RequestData requestData = new RequestData();
		requestData.setUuid(uuid);
		return requestManager.readRequestData(requestData);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public RequestData updateRequestData(@RequestParam("uuid") String uuid, @RequestParam("name") String name, @RequestParam("info") String info) {
		RequestData requestData = new RequestData();
		requestData.setUuid(uuid);
		requestData.setName(name);
		requestData.setInfo(info);
		
		return requestManager.updateRequestData(requestData);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void deleteRequestData(@RequestParam("uuid") String uuid) {
		RequestData requestData = new RequestData();
		requestData.setUuid(uuid);
		requestManager.deleteRequestData(requestData);
	}
	
	@RequestMapping(value = "/time", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public DateTime printDateTime() {
		return new DateTime();
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public List<RequestData> getAll() {
		return requestManager.getAll();
	}
	
}
