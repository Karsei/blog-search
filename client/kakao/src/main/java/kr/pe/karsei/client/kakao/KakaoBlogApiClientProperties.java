package kr.pe.karsei.client.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.kakao")
@Getter
@Setter
public class KakaoBlogApiClientProperties {
    /**
     * API 주소
     */
    private String url;

    /**
     * API KEY
     */
    private String apiKey;
}