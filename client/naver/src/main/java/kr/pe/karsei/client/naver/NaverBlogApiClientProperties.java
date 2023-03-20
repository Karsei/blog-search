package kr.pe.karsei.client.naver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.naver")
@Getter
@Setter
public class NaverBlogApiClientProperties {
    /**
     * API 주소
     */
    private String url;

    /**
     * API ID
     */
    private String apiId;

    /**
     * API Secret
     */
    private String apiSecret;
}
