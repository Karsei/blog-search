package kr.pe.karsei.client.kakao;

import kr.pe.karsei.client.kakao.config.KakaoClientConfiguration;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "kakaoBlogApiClient", url = "https://dapi.kakao.com", primary = false, configuration = KakaoClientConfiguration.class)
public interface BlogApiClient {
    /**
     * 카카오의 블로그를 검색합니다.
     * @param param 파라미터
     * @return 결과
     */
    @GetMapping("/v2/search/blog")
    KakaoBlogSearch.Info searchBlog(@SpringQueryMap KakaoBlogSearch.Param param);
}
