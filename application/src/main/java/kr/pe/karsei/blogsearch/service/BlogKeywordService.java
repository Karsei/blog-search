package kr.pe.karsei.blogsearch.service;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordQueryUseCase;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordApiLoadPort;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordCountLoadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogKeywordService implements BlogKeywordQueryUseCase {
    private final ApplicationEventPublisher eventPublisher;
    private final BlogKeywordApiLoadPort apiLoadPort;
    private final BlogKeywordCountLoadPort countLoadPort;

    @Override
    public FetchBlogKeyword findBlog(final Pageable pageable, final String query) {
        // 조회
        FetchBlogKeyword info = apiLoadPort.search(pageable, query);
        // 검색 키워드 이벤트 발행
        eventPublisher.publishEvent(new BlogKeywordFetchEvent(query));
        return info;
    }

    @Override
    public List<FetchBlogKeywordTop> findTopBlogKeywords(final int size) {
        return countLoadPort.findTopBlogKeywords(size);
    }
}
