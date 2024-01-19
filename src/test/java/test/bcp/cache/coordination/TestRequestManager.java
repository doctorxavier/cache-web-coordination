package test.bcp.cache.coordination;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcp.cache.coordination.manager.RequestManager;
import com.bcp.cache.coordination.model.RequestData;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration(classes = {TestRequestManager.class})
})
@Configuration
@EnableCaching
@ComponentScan(basePackages = {
		"com.bcp.cache.coordination.manager"}, 
		useDefaultFilters = false, 
		includeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RequestManager.class)
		})
public class TestRequestManager {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestRequestManager.class);

	private static final Gson	GSON	= Converters.registerDateTime(new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).create();

	@Autowired
	private RequestManager		requestManager;
	
	@Bean
	@Lazy(false)
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
		cacheManager.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManager.setShared(true);
		return cacheManager;
	}

	@Test
	public void test() {
		RequestData requestData = requestManager.createRequestData(new RequestData());
		LOGGER.info(GSON.toJson(requestData));
		
		requestData = requestManager.createRequestData(new RequestData());
		LOGGER.info(GSON.toJson(requestData));
		
		requestData = requestManager.readRequestData(requestData);
		LOGGER.info(GSON.toJson(requestData));
		
		requestData.setName("update1");
		requestData = requestManager.updateRequestData(requestData);
		LOGGER.info(GSON.toJson(requestData));
		
		List<RequestData> requestsData = requestManager.getAll();
		LOGGER.info(GSON.toJson(requestsData));
		
		requestManager.deleteRequestData(requestData);
		
		requestData = requestManager.readRequestData(requestData);
		LOGGER.info(GSON.toJson(requestData));
		
		requestsData = requestManager.getAll();
		LOGGER.info(GSON.toJson(requestsData));
		
	}

}
