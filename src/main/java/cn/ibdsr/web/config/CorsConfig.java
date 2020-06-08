package cn.ibdsr.web.config;

import cn.ibdsr.web.core.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CorsConfig
 *
 * @author xjc
 * @Date 2018/11/23 15:21
 */
@Configuration
public class CorsConfig {
    /**
     * 跨域问题
     */
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }
}
