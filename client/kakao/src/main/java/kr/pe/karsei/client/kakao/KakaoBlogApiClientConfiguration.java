package kr.pe.karsei.client.kakao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration(proxyBeanMethods = false)
@PropertySource(
        value = { "classpath:application-kakao.properties" }
)
@EnableFeignClients
public class KakaoBlogApiClientConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public KakaoBlogApiClientProperties kakaoBlogApiClientProperties() {
        return new KakaoBlogApiClientProperties();
    }
}
