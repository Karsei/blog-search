package kr.pe.karsei.client.kakao;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients
public class KakaoBlogApiClientConfiguration {
}
