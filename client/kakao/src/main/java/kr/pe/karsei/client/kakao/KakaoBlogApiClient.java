package kr.pe.karsei.client.kakao;

import kr.pe.karsei.client.kakao.feign.KakaoClientFeignConfiguration;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "kakaoBlogApiClient", url = "${client.kakao.url}", configuration = KakaoClientFeignConfiguration.class)
public interface KakaoBlogApiClient {
    /**
     * 카카오의 블로그를 검색합니다.
     * @param param 파라미터
     * @return 결과
     */
    @GetMapping("/v2/search/blog")
    KakaoBlogSearch.Info search(@SpringQueryMap KakaoBlogSearch.Param param);
}
