package kr.pe.karsei.blogsearch.service;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.exception.BlogKeywordException;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordQueryUseCase;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordApiLoadPort;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordCountLoadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static kr.pe.karsei.blogsearch.exception.BlogKeywordErrorCode.ERROR_ON_API;

@Service
@RequiredArgsConstructor
public class BlogKeywordService implements BlogKeywordQueryUseCase {
    private final ApplicationEventPublisher eventPublisher;
    private final BlogKeywordApiLoadPort apiLoadPort;
    private final BlogKeywordCountLoadPort countLoadPort;

    @Override
    public Mono<FetchBlogKeyword> findBlog(final Pageable pageable, final String query) {
        // 조회
        return apiLoadPort.searchWithKakao(pageable, query)
                .map(it -> {
                    // 검색 키워드 이벤트 발행
                    eventPublisher.publishEvent(new BlogKeywordFetchEvent(query));
                    return it;
                });
    }

    @Override
    public List<FetchBlogKeywordTop> findTopBlogKeywords(final int size) {
        return countLoadPort.findTopBlogKeywords(size);
    }
}
