package kr.pe.karsei.client.kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.*;

@Slf4j
@Configuration(proxyBeanMethods = false)
@PropertySource(
        value = { "classpath:application-kakao.properties" }
)
public class KakaoBlogApiClientConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public KakaoBlogApiClientProperties kakaoBlogApiClientProperties() {
        return new KakaoBlogApiClientProperties();
    }

    @Bean
    public WebClient kakaoApiClient(final KakaoBlogApiClientProperties properties) {
        if (!StringUtils.hasText(properties.getApiKey())) {
            throw new IllegalArgumentException("Kakao API Key 가 존재하지 않습니다.");
        }

        return WebClient.builder()
                .baseUrl(properties.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + properties.getApiKey())
                .build();
    }

    @Bean
    public KakaoBlogApiClient kakaoBlogApiClient(final WebClient kakaoApiClient) {
        return new KakaoBlogApiClient(kakaoApiClient);
    }
}
