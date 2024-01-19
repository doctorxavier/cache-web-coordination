package com.bcp.cache.coordination.controller;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.cache.coordination.dto.CacheDto;
import com.bcp.cache.coordination.manager.CacheEntityManager;

@RestController
@RequestMapping("/app/cache/")
public class CacheEntityController {
	
	private static final int CODE_LENGTH = 10;
	private static final int NAME_LENGTH = 45;
	
	@Autowired
	private CacheEntityManager cacheEntityManager;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CacheDto createRequestData() {
		CacheDto cacheDto = new CacheDto();
		cacheDto.setCode(RandomStringUtils.randomAscii(CODE_LENGTH));
		cacheDto.setName(RandomStringUtils.randomAscii(NAME_LENGTH));
		
		return cacheEntityManager.createCacheEntity(cacheDto);
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CacheDto readRequestData(@RequestParam("id") Integer id) {
		CacheDto cacheDto = new CacheDto();
		cacheDto.setId(id);
		return cacheEntityManager.readCacheEntity(cacheDto);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CacheDto updateRequestData(@RequestParam("id") Integer id, @RequestParam("code") String code, @RequestParam("name") String name) {
		CacheDto cacheDto = new CacheDto();
		cacheDto.setId(id);
		cacheDto.setCode(code);
		cacheDto.setName(name);
		
		return cacheEntityManager.updateCacheEntity(cacheDto);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void deleteRequestData(@RequestParam("id") Integer id) {
		CacheDto cacheDto = new CacheDto();
		cacheDto.setId(id);
		cacheEntityManager.deleteCacheEntity(cacheDto);
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
	public List<CacheDto> getAll() {
		return cacheEntityManager.getAll();
	}
	
}
