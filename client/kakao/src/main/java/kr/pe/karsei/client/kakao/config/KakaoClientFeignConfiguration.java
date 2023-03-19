package kr.pe.karsei.client.kakao.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class KakaoClientFeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", "KakaoAK " + "");
    }
}
