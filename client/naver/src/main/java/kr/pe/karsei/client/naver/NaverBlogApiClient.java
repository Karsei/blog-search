package kr.pe.karsei.client.naver;

import kr.pe.karsei.client.naver.config.NaverClientFeignConfiguration;
import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "naverBlogApiClient", url = "${client.naver.url}", primary = false, configuration = NaverClientFeignConfiguration.class)
public interface NaverBlogApiClient {
    /**
     * 네이버의 블로그를 검색합니다.
     * @param param 파라미터
     * @return 결과
     */
    @GetMapping("/v1/search/blog.json")
    NaverBlogSearch.Info search(@SpringQueryMap NaverBlogSearch.Param param);
}
