package kr.pe.karsei.client.naver.config;

import feign.Request;
import feign.RequestInterceptor;
import kr.pe.karsei.client.naver.NaverBlogApiClientProperties;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class NaverClientFeignConfiguration {
    private final static int CONNECT_TIMEOUT = 5000;
    private final static int READ_TIMEOUT = 5000;

    @Bean
    public RequestInterceptor requestInterceptor(NaverBlogApiClientProperties properties) {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", properties.getApiId());
            requestTemplate.header("X-Naver-Client-Secret", properties.getApiSecret());
        };
    }

    @Bean
    public static Request.Options requestOptions() {
        return new Request.Options(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS, READ_TIMEOUT, TimeUnit.MILLISECONDS, false);
    }
}