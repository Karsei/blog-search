package kr.pe.karsei.blogsearch.service;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordCommandUseCase;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordQueryUseCase;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordApiLoadPort;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordCollectLoadPort;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordCollectSavePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogKeywordService implements BlogKeywordQueryUseCase, BlogKeywordCommandUseCase {
    private final ApplicationEventPublisher eventPublisher;
    private final BlogKeywordApiLoadPort apiLoadPort;
    private final BlogKeywordCollectLoadPort collectLoadPort;
    private final BlogKeywordCollectSavePort collectSavePort;

    @Override
    public FetchBlogKeyword.Info findBlog(final FetchBlogKeyword.Param param) {
        // 조회
        FetchBlogKeyword.Info info = apiLoadPort.searchBlog(param);
        // 검색 키워드 이벤트 발행
        eventPublisher.publishEvent(new BlogKeywordCollectEvent(param.getQuery()));
        return info;
    }

    @Override
    public List<FetchBlogKeywordTop> findTopBlogKeywords(final int size) {
        return collectLoadPort.findTopBlogKeywords(size);
    }

    @Override
    @Transactional
    public void increase(final String keyword) {
        collectSavePort.increaseKeywordHit(keyword);
    }
}
