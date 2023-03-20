package kr.pe.karsei.client.naver.config;

import feign.RequestInterceptor;
import kr.pe.karsei.client.naver.NaverBlogApiClientProperties;
import org.springframework.context.annotation.Bean;

public class NaverClientFeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor(NaverBlogApiClientProperties properties) {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", properties.getApiId());
            requestTemplate.header("X-Naver-Client-Secret", properties.getApiSecret());
        };
    }
}