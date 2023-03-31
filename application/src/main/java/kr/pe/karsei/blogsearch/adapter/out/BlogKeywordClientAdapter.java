package kr.pe.karsei.blogsearch.adapter.out;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.mapper.BlogKeywordMapper;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordApiLoadPort;
import kr.pe.karsei.client.kakao.KakaoBlogApiClient;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import kr.pe.karsei.client.naver.NaverBlogApiClient;
import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogKeywordClientAdapter implements BlogKeywordApiLoadPort {
    private final KakaoBlogApiClient kakaoBlogApiClient;
    private final NaverBlogApiClient naverBlogApiClient;

    @CircuitBreaker(name = "blogSearch", fallbackMethod = "searchWithNaverForFallback")
    @Override
    public FetchBlogKeyword searchWithKakao(final Pageable pageable, final String query) {
        KakaoBlogSearch.Info info = kakaoBlogApiClient.search(BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(pageable, query));
        return BlogKeywordMapper.mapKakaoBlogSearchToSearchBlogInfo(pageable, info);
    }

    @Override
    public FetchBlogKeyword searchWithNaver(final Pageable pageable, final String query) {
        NaverBlogSearch.Info info = naverBlogApiClient.search(BlogKeywordMapper.mapSearchParamToNaverBlogSearchParam(pageable, query));
        return BlogKeywordMapper.mapNaverBlogSearchToSearchBlogInfo(pageable, info);
    }

    private FetchBlogKeyword searchWithNaverForFallback(final Pageable pageable, final String query, final Throwable ex) {
        return searchWithNaver(pageable, query);
    }
}
