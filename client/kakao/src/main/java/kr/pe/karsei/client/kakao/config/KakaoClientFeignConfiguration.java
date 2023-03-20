package kr.pe.karsei.client.kakao.config;

import feign.RequestInterceptor;
import kr.pe.karsei.client.kakao.KakaoBlogApiClientProperties;
import org.springframework.context.annotation.Bean;

public class KakaoClientFeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor(KakaoBlogApiClientProperties properties) {
        return requestTemplate -> requestTemplate.header("Authorization", "KakaoAK " + properties.getApiKey());
    }
}
