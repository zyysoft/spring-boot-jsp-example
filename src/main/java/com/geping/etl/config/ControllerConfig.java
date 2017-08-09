package com.geping.etl.config;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * 
 * @author chenxuan
 *
 */
@Configuration
@EnableWebMvc
public class ControllerConfig extends WebMvcConfigurerAdapter{
	
	private Logger logger = LoggerFactory.getLogger(ControllerConfig.class);
	
	@Autowired
	Environment env;
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        // Ask the json converter to ignore properties with null values when convert
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// Ask the json converter to ignore extra properties here
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        // Ask the json converter to ignore unknown properties here
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converter.setObjectMapper(objectMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
        changeLogback();
        
//        startUpCheckService.startUpCheck();
    }
	
	@Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
	
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/").addResourceLocations("/resources/css/**");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/**").addResourceLocations("/");
        
        String extResRoot = "file:"+env.getProperty("ext_res_root");
        String extResName = env.getProperty("ext_res");
        registry.addResourceHandler("/"+extResName+"**").addResourceLocations(extResRoot);
    }
	
	private void changeLogback(){
		String logback = env.getProperty("logback_file");
		System.out.println("+++++++++++++++++++ logback +++++++++++++++++++");
		System.out.println("logback:"+ logback);
		if(logback!=null && !logback.isEmpty()){
			File file = new File(logback);
			if(file!=null && file.exists()){
				StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
				//  				    LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();
				LoggerContext context = (LoggerContext) loggerBinder.getLoggerFactory();

				try {
					JoranConfigurator configurator = new JoranConfigurator();
					configurator.setContext(context);
					// Call context.reset() to clear any previous configuration, e.g. default 
					// configuration. For multi-step configuration, omit calling context.reset().
					context.reset(); 
					configurator.doConfigure(file);
				} catch (JoranException je) {
//					je.printStackTrace();
					logger.error("initSystem failed",je);
				} catch (Throwable ex){
					logger.error("initSystem failed",ex);
//					ex.printStackTrace();
				}
			}
		}
	}
}
