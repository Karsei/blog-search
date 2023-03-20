package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.mapper.BlogKeywordMapper;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordCountLoadPort;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordEventSavePort;
import kr.pe.karsei.blogsearch.repository.BlogKeywordCountRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogKeywordJpaAdapter implements BlogKeywordCountLoadPort, BlogKeywordEventSavePort {
    private final BlogKeywordCountRepository countRepository;
    private final BlogKeywordEventStoreRepository eventStoreRepository;

    @Override
    public List<FetchBlogKeywordTop> findTopBlogKeywords(final int size) {
        List<BlogKeywordCountJpaEntity> list = countRepository.findAllByOrderByHitDesc(PageRequest.of(0, size));
        return BlogKeywordMapper.mapCountEntityListToDto(list);
    }

    @Override
    public void create(final String keyword) {
        eventStoreRepository.save(new BlogKeywordEventStoreJpaEntity(null, keyword, null));
    }
}
