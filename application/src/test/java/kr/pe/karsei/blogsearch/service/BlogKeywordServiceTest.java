package kr.pe.karsei.blogsearch.service;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordApiLoadPort;
import kr.pe.karsei.blogsearch.port.out.BlogKeywordCountLoadPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogKeywordServiceTest {
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private BlogKeywordApiLoadPort apiLoadPort;
    @Mock
    private BlogKeywordCountLoadPort countLoadPort;
    @InjectMocks
    private BlogKeywordService blogKeywordService;

    @Test
    void testIfFindingBlogCanBeCalled() {
        // given
        String query = "한글날";
        String sort = "accuracy";
        Pageable pageable = PageRequest.of(1, 10, Sort.by(sort));

        List<FetchBlogKeyword.Document> documents = new ArrayList<>();
        documents.add(FetchBlogKeyword.Document.builder()
                .blogName("너무나도어렵네")
                .contents("<b>한글날</b> 공휴일 추가수당에 대해 알아보도록 하겠습니다. 이 게시물을 전체적으로 읽어주시면 <b>한글날</b> 공휴일 추가수당을 알아두시는 데에 보탬이 될 것입니다. <b>한글날</b> 공휴일 추가수당의 정보가 필요하다면 전체 다 읽어주세요. 이제 밑에서 <b>한글날</b> 공휴일 추가수당을 알려드리겠습니다. <b>한글날</b> 공휴일 추가수당 통풍에...")
                .dateTime(ZonedDateTime.of(2023, 2, 19, 22, 4, 34, 0, ZoneId.of("Asia/Seoul")))
                .build());
        FetchBlogKeyword.Pagination pagination = FetchBlogKeyword.Pagination.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalCount(346401)
                .build();
        FetchBlogKeyword info = FetchBlogKeyword.builder()
                .documents(documents)
                .pagination(pagination)
                .build();
        given(apiLoadPort.search(any(Pageable.class), anyString())).willReturn(info);

        // when
        FetchBlogKeyword result = blogKeywordService.findBlog(pageable, query);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getDocuments()).isNotNull();
        assertThat(result.getDocuments()).hasSize(1);
        assertThat(result.getDocuments().get(0).getBlogName()).isEqualTo(documents.get(0).getBlogName());
        assertThat(result.getDocuments().get(0).getTitle()).isEqualTo(documents.get(0).getTitle());
        assertThat(result.getDocuments().get(0).getUrl()).isEqualTo(documents.get(0).getUrl());
        assertThat(result.getDocuments().get(0).getDateTime()).isEqualTo(documents.get(0).getDateTime());
        assertThat(result.getPagination()).isNotNull();
        assertThat(result.getPagination().getPage()).isEqualTo(pagination.getPage());
        assertThat(result.getPagination().getSize()).isEqualTo(pagination.getSize());
        assertThat(result.getPagination().getTotalCount()).isEqualTo(pagination.getTotalCount());
    }

    @Test
    void testIfFindingTopBlogKeywordsCanBeCalled() {
        // given
        int size = 10;

        List<FetchBlogKeywordTop> list = new ArrayList<>();
        list.add(FetchBlogKeywordTop.builder()
                .keyword("한글날")
                .hit(23)
                .build());
        given(countLoadPort.findTopBlogKeywords(anyInt())).willReturn(list);

        // when
        List<FetchBlogKeywordTop> result = blogKeywordService.findTopBlogKeywords(size);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getKeyword()).isEqualTo(list.get(0).getKeyword());
        assertThat(result.get(0).getHit()).isEqualTo(list.get(0).getHit());
    }
}