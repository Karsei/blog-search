package kr.pe.karsei.client.kakao.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class KakaoClientConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        // TODO: url, key 를 property 로 빼내기
        return requestTemplate -> requestTemplate.header("Authorization", "KakaoAK " + "");
    }
}
