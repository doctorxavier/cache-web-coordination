package com.bcp.cache.coordination.config;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.management.MBeanServer;

import net.sf.ehcache.management.ManagementService;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ehcache.InstrumentedEhcache;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Configuration
@EnableCaching
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
		"com.bcp.cache.coordination.dao",
		"com.bcp.cache.coordination.controller",
		"com.bcp.cache.coordination.manager"
	})
//@EnableSpringConfigured
public class ContextConfig extends WebMvcConfigurerAdapter {
	
	final MetricRegistry metricRegistry = new MetricRegistry();
	
	final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
	
	@PreDestroy
    public void destroy() {
		reporter.stop();
    }

	@Bean
	public CacheManager cacheManager() {
		
		EhCacheCacheManager cacheManager = new EhCacheCacheManager(ehCacheCacheManager().getObject());
		net.sf.ehcache.CacheManager ehCacheManager = cacheManager.getCacheManager();
		
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ManagementService.registerMBeans(cacheManager.getCacheManager(), mBeanServer, false, false, false, true);
		
		for(String cacheName : ehCacheManager.getCacheNames()) {
			net.sf.ehcache.Cache ehcache = ehCacheManager.getCache(cacheName);	
			net.sf.ehcache.Ehcache decoratedCache = InstrumentedEhcache.instrument(metricRegistry, ehCacheManager.getEhcache(cacheName));
			ehCacheManager.replaceCacheWithDecoratedCache(ehcache, decoratedCache);
		}
		
		reporter.start();
		
		return cacheManager;
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
		cacheManager.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManager.setShared(true);
		return cacheManager;
	}
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JodaModule());
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }
	
}
