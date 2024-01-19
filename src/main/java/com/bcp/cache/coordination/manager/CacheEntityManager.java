package com.bcp.cache.coordination.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcp.cache.coordination.dao.ICacheEntityDao;
import com.bcp.cache.coordination.dto.CacheDto;

@Service
public class CacheEntityManager {

	@Autowired
	private ICacheEntityDao cacheEntityDao;

	public CacheDto createCacheEntity(CacheDto cacheDto) {
		return cacheEntityDao.create(cacheDto);
	}
	
	public CacheDto readCacheEntity(CacheDto cacheDto) {
		return cacheEntityDao.getById(cacheDto.getId());
	}
	
	public CacheDto updateCacheEntity(CacheDto cacheDto) {
		return cacheEntityDao.update(cacheDto);
	}
	
	public void deleteCacheEntity(CacheDto cacheDto) {
		cacheEntityDao.remove(cacheDto);
	}
	
	public List<CacheDto> getAll() {
		return cacheEntityDao.getAll();
	}
	
}
