package cn.ibdsr.web;

import cn.ibdsr.web.config.properties.WebProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(scanBasePackages = {"cn.ibdsr.core","cn.ibdsr.web", "cn.ibdsr.fastdfs"})
public class WebApplication extends WebMvcConfigurerAdapter{

    protected final static Logger logger = LoggerFactory.getLogger(WebApplication.class);

    @Autowired
    WebProperties webProperties;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        logger.info("WebApplication is success!");
    }

    /**
     * 增加swagger的支持
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(webProperties.getSwaggerOpen()){
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
}
