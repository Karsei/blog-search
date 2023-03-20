package kr.pe.karsei.client.naver;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients
public class NaverBlogApiClientConfiguration {
}
