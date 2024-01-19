package test.bcp.cache.coordination.dao;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import test.bcp.cache.coordination.TestCache;

import com.bcp.cache.coordination.config.ContextConfig;
import com.bcp.cache.coordination.dao.ICacheEntityDao;
import com.bcp.cache.coordination.dao.impl.CacheEntityDao;
import com.bcp.cache.coordination.dto.CacheDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration({"classpath:cache-web-coordination.xml"}), @ContextConfiguration(classes = TestCacheEntityDao.class)})
@ComponentScan(basePackages = {
		"test.bcp.cache.coordination.config", 
		"com.bcp.cache.coordination.dao"},
		useDefaultFilters = false,
		includeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ContextConfig.class), 
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CacheEntityDao.class)
    }
)
@TestExecutionListeners(listeners = {
		DependencyInjectionTestExecutionListener.class, 
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, 
		TestCacheEntityDao.class
	})
public class TestCacheEntityDao extends AbstractTestExecutionListener {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(TestCache.class);
	private static final Gson	GSON	= new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

	private static final int CODE_LENGTH = 10;
	private static final int NAME_LENGTH = 45;
	
	private static Integer		id;
	
	@Resource
	private ICacheEntityDao		cacheEntityDao;

	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		cacheEntityDao = testContext.getApplicationContext().getBean(CacheEntityDao.class);
		
		CacheDto cacheDto = new CacheDto();
		cacheDto.setCode(RandomStringUtils.randomAscii(CODE_LENGTH));
		cacheDto.setName(RandomStringUtils.randomAscii(NAME_LENGTH));
		
		cacheDto = cacheEntityDao.create(cacheDto);
		id = cacheDto.getId();
		
		LOGGER.info(GSON.toJson(cacheDto));
	}
	
	@Test
	public void testUpdate() {
		CacheDto cacheDto = cacheEntityDao.getById(id);
		
		LOGGER.info(GSON.toJson(cacheDto));
		
		cacheDto.setCode(RandomStringUtils.randomAscii(CODE_LENGTH));
		cacheDto.setName(RandomStringUtils.randomAscii(NAME_LENGTH));
		
		cacheDto = cacheEntityDao.update(cacheDto);
		
		LOGGER.info(GSON.toJson(cacheDto));
	}
	
	@Test
	public void testGetAll() {
		LOGGER.info(GSON.toJson(cacheEntityDao.getAll()));
	}
	
	@Override
	public void afterTestClass(TestContext testContext) {
		CacheDto cacheDto = cacheEntityDao.getById(id);
		
		cacheEntityDao.remove(cacheDto);
		
		cacheDto = cacheEntityDao.getById(id);
		
		Assert.assertNull(cacheDto);
	}

}
