package kr.pe.karsei.client.naver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients
public class NaverBlogApiClientConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public NaverBlogApiClientProperties naverBlogApiClientProperties() {
        return new NaverBlogApiClientProperties();
    }
}
