package com.bcp.cache.coordination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcp.cache.coordination.model.CacheEntity;

public interface CacheEntityRepository extends JpaRepository<CacheEntity, Integer> {

	List<CacheEntity> findByName(String name);
	
}
