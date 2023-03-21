package kr.pe.karsei.client.naver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration(proxyBeanMethods = false)
@PropertySource(
        value = { "classpath:application-naver.properties" }
)
@EnableFeignClients
public class NaverBlogApiClientConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public NaverBlogApiClientProperties naverBlogApiClientProperties() {
        return new NaverBlogApiClientProperties();
    }
}
