package com.geping.etl.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
/**
 * 
 * @author chenxuan
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.geping.etl.repository")
public class DatasourceConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(DatasourceConfig.class);

	private static final String MYSQL_PREFIX = "mysql.";
	private static final String DRUID_PREFIX = "druid.";

	@Configuration
	@Profile(AppProfile.INTG)
	@org.springframework.context.annotation.PropertySource("classpath:/intg/jdbc.properties")
	static class MysqlIntgConfiguration {
	}

	@Configuration
	@Profile(AppProfile.PROD)
	@org.springframework.context.annotation.PropertySource("classpath:/prod/jdbc.properties")
	static class MysqlProdConfiguration {
	}

	@Autowired
	private Environment env;
	
	@Bean(name="dataSource")
	public DataSource dataSource() {
		LOGGER.info("----begin init datasource----");
		Properties dbProperties = new Properties();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext();) {
			PropertySource<?> propertySource = it.next();
			getPropertiesFromSource(propertySource, map);
		}
		dbProperties.putAll(map);

		DruidDataSource dataSource = null;
		try {
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(dbProperties);
			if(null != dataSource) {
				dataSource.setFilters("wall,stat");
				//        		dataSource.setTimeBetweenLogStatsMillis(5000);
				dataSource.init();
			}
		} catch (Exception e) {
			throw new RuntimeException("load datasource error, dbProperties is :" + dbProperties, e);
		}
		LOGGER.info("----end init datasource----");
		return dataSource;
	}

	private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {

		if (propertySource instanceof MapPropertySource) {
			for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
				if (key.startsWith(MYSQL_PREFIX)) {
					map.put(key.replaceFirst(MYSQL_PREFIX, ""), propertySource.getProperty(key));
				} else if (key.startsWith(DRUID_PREFIX)) {
					map.put(key.replaceFirst(DRUID_PREFIX, ""), propertySource.getProperty(key));
				}
			}
		}

		if (propertySource instanceof CompositePropertySource) {
			for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
				getPropertiesFromSource(s, map);
			}
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

}