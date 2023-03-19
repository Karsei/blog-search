package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.mapper.BlogKeywordMapper;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordCollectLoadPort;
import kr.pe.karsei.blogsearch.port.out.EventSavePort;
import kr.pe.karsei.blogsearch.repository.BlogKeywordCollectRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogKeywordJpaAdapter implements BlogKeywordCollectLoadPort, EventSavePort {
    private final BlogKeywordCollectRepository collectRepository;
    private final BlogKeywordEventStoreRepository eventStoreRepository;

    @Override
    public List<FetchBlogKeywordTop> findTopBlogKeywords(final int size) {
        List<BlogKeywordCollectJpaEntity> list = collectRepository.findAllByOrderByHitDesc(PageRequest.of(0, size));
        return BlogKeywordMapper.mapSearchEntityListToDto(list);
    }

    @Override
    public void create(final String keyword) {
        eventStoreRepository.save(new BlogKeywordEventStoreJpaEntity(null, keyword, null));
    }
}
