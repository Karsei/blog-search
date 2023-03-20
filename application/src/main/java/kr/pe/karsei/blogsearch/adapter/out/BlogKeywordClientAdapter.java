package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.mapper.BlogKeywordMapper;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordApiLoadPort;
import kr.pe.karsei.client.kakao.KakaoBlogApiClient;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogKeywordClientAdapter implements BlogKeywordApiLoadPort {
    private final KakaoBlogApiClient kakaoBlogApiClient;

    @Override
    public FetchBlogKeyword searchBlog(final Pageable pageable, final String query) {
        KakaoBlogSearch.Info kakaoBlogSearch = searchBlogWithKakao(pageable, query);
        return BlogKeywordMapper.mapKakaoBlogSearchToSearchBlogInfo(kakaoBlogSearch);
    }

    private KakaoBlogSearch.Info searchBlogWithKakao(final Pageable pageable, final String query) {
        return kakaoBlogApiClient.search(BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(pageable, query));
    }
}
