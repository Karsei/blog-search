package kr.pe.karsei.client.kakao.feign;

import feign.Request;
import feign.RequestInterceptor;
import kr.pe.karsei.client.kakao.KakaoBlogApiClientProperties;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class KakaoClientFeignConfiguration {
    private final static int CONNECT_TIMEOUT = 5000;
    private final static int READ_TIMEOUT = 5000;

    @Bean
    public RequestInterceptor requestInterceptor(KakaoBlogApiClientProperties properties) {
        return requestTemplate -> requestTemplate.header("Authorization", "KakaoAK " + properties.getApiKey());
    }

    @Bean
    public static Request.Options requestOptions() {
        return new Request.Options(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS, READ_TIMEOUT, TimeUnit.MILLISECONDS, false);
    }
}
