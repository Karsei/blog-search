package kr.pe.karsei.client.kakao;

import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class KakaoBlogApiClient {
    private final WebClient kakaoApiClient;

    public KakaoBlogApiClient(final WebClient kakaoApiClient) {
        this.kakaoApiClient = kakaoApiClient;
    }

    public Mono<KakaoBlogSearch.Info> search(final KakaoBlogSearch.Param params) {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        if (StringUtils.hasText(params.getQuery())) {
            valueMap.add("query", params.getQuery());
        }
        valueMap.add("sort", params.getSort().toString());
        valueMap.add("page", String.valueOf(params.getPage()));
        valueMap.add("size", String.valueOf(params.getSize()));
        return kakaoApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/search/blog")
                        .queryParams(valueMap)
                        .build()
                )
                .retrieve()
                .bodyToMono(KakaoBlogSearch.Info.class);
    }
}
