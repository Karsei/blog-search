package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.blogsearch.config.annotation.Adapter;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.mapper.BlogKeywordMapper;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordApiLoadPort;
import kr.pe.karsei.client.kakao.BlogApiClient;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class BlogKeywordClientAdapter implements BlogKeywordApiLoadPort {
    private final BlogApiClient blogApiClient;

    @Override
    public FetchBlogKeyword.Info searchBlog(final FetchBlogKeyword.Param params) {
        KakaoBlogSearch.Info kakaoBlogSearch = searchBlogWithKakao(params);
        return BlogKeywordMapper.mapKakaoBlogSearchToSearchBlogInfo(kakaoBlogSearch);
    }

    private KakaoBlogSearch.Info searchBlogWithKakao(final FetchBlogKeyword.Param params) {
        return blogApiClient.searchBlog(BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(params));
    }
}
