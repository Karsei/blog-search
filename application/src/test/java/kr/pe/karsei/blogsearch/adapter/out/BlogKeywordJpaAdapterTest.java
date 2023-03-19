package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.repository.BlogKeywordCountRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventStoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogKeywordJpaAdapterTest {
    @Mock
    private BlogKeywordCountRepository countRepository;
    @Mock
    private BlogKeywordEventStoreRepository eventStoreRepository;
    @InjectMocks
    private BlogKeywordJpaAdapter adapter;

    @Test
    void testIfFindingTopBlogKeywordsCanBeRetrieved() {
        // given
        int size = 10;
        List<BlogKeywordCountJpaEntity> list = new ArrayList<>();
        list.add(new BlogKeywordCountJpaEntity(1L, "한글날", 100, new Date()));
        given(countRepository.findAllByOrderByHitDesc(any(PageRequest.class))).willReturn(list);

        // when
        List<FetchBlogKeywordTop> result = adapter.findTopBlogKeywords(size);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getKeyword()).isEqualTo(list.get(0).getKeyword());
        assertThat(result.get(0).getHit()).isEqualTo(list.get(0).getHit());
    }

    @Test
    void testIfEventCanBeCreated() {
        // given
        String keyword = "한글날";

        // when & then
        assertDoesNotThrow(() -> adapter.create(keyword));
    }
}