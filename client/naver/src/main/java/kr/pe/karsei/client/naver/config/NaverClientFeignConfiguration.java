package kr.pe.karsei.client.naver.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class NaverClientFeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", "");
            requestTemplate.header("X-Naver-Client-Secret", "");
        };
    }
}
