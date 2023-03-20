package kr.pe.karsei.blogsearch.adapter.out;

import feign.FeignException;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.exception.BlogKeywordException;
import kr.pe.karsei.blogsearch.exception.BlogKeywordErrorCode;
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
        try {
            KakaoBlogSearch.Info kakaoBlogSearch = searchBlogWithKakao(pageable, query);
            return BlogKeywordMapper.mapKakaoBlogSearchToSearchBlogInfo(kakaoBlogSearch);
        }
        catch (FeignException.FeignClientException e) {
            throw new BlogKeywordException(BlogKeywordErrorCode.BAD_REQUEST_API_REQUEST, e);
        }
        catch (FeignException.FeignServerException e) {
            throw new BlogKeywordException(BlogKeywordErrorCode.INTERNAL_SERVER_ERROR_API_REQUEST, e);
        }
        catch (Exception e) {
            throw new BlogKeywordException(BlogKeywordErrorCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    private KakaoBlogSearch.Info searchBlogWithKakao(final Pageable pageable, final String query) {
        return kakaoBlogApiClient.search(BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(pageable, query));
    }
}
