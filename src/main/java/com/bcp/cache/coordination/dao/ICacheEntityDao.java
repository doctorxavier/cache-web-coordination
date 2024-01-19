package com.bcp.cache.coordination.dao;

import java.util.List;

import com.bcp.cache.coordination.dto.CacheDto;


public interface ICacheEntityDao {
	
	CacheDto getById(Integer id);
	
	void remove(CacheDto cacheDto);
	
	CacheDto update(CacheDto cacheDto);
	
	CacheDto create(CacheDto cacheDto);
	
	List<CacheDto> getAll();

}
