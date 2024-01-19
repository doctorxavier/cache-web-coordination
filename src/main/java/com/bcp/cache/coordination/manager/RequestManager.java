package com.bcp.cache.coordination.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bcp.cache.coordination.model.RequestData;

@Service
public class RequestManager {
	
	@Autowired
	private CacheManager cacheManager;

	@CachePut(cacheNames="requestData", key="#requestData.uuid")
	public RequestData createRequestData(RequestData requestData) {
		
		requestData.setDate(new DateTime());
		requestData.setName("");
		requestData.setUuid(UUID.randomUUID().toString());
		requestData.setInfo(requestData.getDate().toString() + "," + requestData.getUuid());
		
		return requestData;
	}
	
	@Cacheable(cacheNames="requestData", key="#requestData.uuid")
	public RequestData readRequestData(RequestData requestData) {
		return null;
	}
	
	@CachePut(cacheNames="requestData", key="#requestData.uuid")
	public RequestData updateRequestData(RequestData requestData) {
		return requestData;
	}
	
	@CacheEvict(cacheNames="requestData", key="#requestData.uuid")
	public void deleteRequestData(RequestData requestData) {
		
	}
	
	public List<RequestData> getAll() {
		List<RequestData> requestsData = new ArrayList<RequestData>(0);

		Object cache = cacheManager.getCache("requestData").getNativeCache();
		if (cache instanceof Ehcache) {
			Ehcache ehCache = (Ehcache) cache;
			for (Object key : ehCache.getKeys()) {
				Element element = ehCache.get(key);
				Object object = element.getObjectValue();
				if (object instanceof RequestData) {
					requestsData.add((RequestData) object);
				}
			}
		}
		
		return requestsData;
	}
	
}
