package com.bcp.cache.coordination.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bcp.cache.coordination.dao.ICacheEntityDao;
import com.bcp.cache.coordination.dto.CacheDto;
import com.bcp.cache.coordination.model.CacheEntity;
import com.bcp.cache.coordination.repository.CacheEntityRepository;
import com.googlecode.jmapper.JMapper;

@Component
public class CacheEntityDao implements ICacheEntityDao {

	@Resource
	private JMapper<CacheDto, CacheEntity>	cacheEntityToDtoMapper;
	
	@Resource
	private JMapper<CacheEntity, CacheDto>	cacheDtoToEntityMapper;
	
	@Resource
	private CacheEntityRepository			cacheEntityRepository;
	
	public CacheDto getById(Integer id) {
		return parseCacheEntityToDto(this.cacheEntityRepository.findOne(id));
	}
	
	public void remove(CacheDto cacheDto) {
		this.cacheEntityRepository.delete(cacheDto.getId());
	}
	
	public List<CacheDto> getAll() {
		return parseCacheEntityToDto(this.cacheEntityRepository.findAll());
	}

	public CacheDto update(CacheDto cacheDto) {
		return parseCacheEntityToDto(this.cacheEntityRepository.save(parseCacheDtoToEntity(cacheDto)));
	}

	public CacheDto create(CacheDto cacheDto) {
		return parseCacheEntityToDto(this.cacheEntityRepository.save(parseCacheDtoToEntity(cacheDto)));
	}
	
	private CacheDto parseCacheEntityToDto(CacheEntity cacheEntity) {
		if (cacheEntity != null) {
			return this.cacheEntityToDtoMapper.getDestination(cacheEntity);
		}
		return null;
	}
	
	private CacheEntity parseCacheDtoToEntity(CacheDto cacheDto) {
		if (cacheDto != null) {
			return this.cacheDtoToEntityMapper.getDestination(cacheDto);
		}
		return null;
	}
	
	private List<CacheDto> parseCacheEntityToDto(Iterable<CacheEntity> cacheEntities) {
		if (cacheEntities != null) {
			List<CacheDto> cacheDtos = new ArrayList<CacheDto>(0);
			for (CacheEntity cacheEntity : cacheEntities) {
				cacheDtos.add(parseCacheEntityToDto(cacheEntity));
			}
			return cacheDtos;
		}
		return null;
	}
	
}
