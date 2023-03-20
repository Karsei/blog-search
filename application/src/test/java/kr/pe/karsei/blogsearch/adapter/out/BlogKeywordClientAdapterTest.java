package kr.pe.karsei.blogsearch.adapter.out;

import feign.FeignException;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.exception.BlogKeywordException;
import kr.pe.karsei.client.kakao.KakaoBlogApiClient;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogKeywordClientAdapterTest {
    @Mock
    private KakaoBlogApiClient kakaoBlogApiClient;
    @InjectMocks
    private BlogKeywordClientAdapter clientAdapter;

    @Test
    void testIfSearchCanBeCalled() {
        // given
        String query = "한글날";
        String sort = "accuracy";
        Pageable pageable = PageRequest.of(1, 10, Sort.by(sort));

        List<KakaoBlogSearch.Info.Document> documents = new ArrayList<>();
        documents.add(KakaoBlogSearch.Info.Document.builder()
                .blogName("너무나도어렵네")
                .contents("<b>한글날</b> 공휴일 추가수당에 대해 알아보도록 하겠습니다. 이 게시물을 전체적으로 읽어주시면 <b>한글날</b> 공휴일 추가수당을 알아두시는 데에 보탬이 될 것입니다. <b>한글날</b> 공휴일 추가수당의 정보가 필요하다면 전체 다 읽어주세요. 이제 밑에서 <b>한글날</b> 공휴일 추가수당을 알려드리겠습니다. <b>한글날</b> 공휴일 추가수당 통풍에...")
                .dateTime(ZonedDateTime.of(2023, 2, 19, 22, 44, 34, 0, ZoneId.of("Asia/Seoul")))
                .title("<b>한글날</b> 공휴일 추가수당 보기쉽게 정리완료했습니다.")
                .url("http://starnews.heetsu.com/636")
                .build());
        KakaoBlogSearch.Info.Meta meta = KakaoBlogSearch.Info.Meta.builder()
                .isEnd(false)
                .pageableCount(800)
                .totalCount(346398)
                .build();
        KakaoBlogSearch.Info info = KakaoBlogSearch.Info.builder()
                .documents(documents)
                .meta(meta)
                .build();
        given(kakaoBlogApiClient.search(any(KakaoBlogSearch.Param.class))).willReturn(info);

        // when
        FetchBlogKeyword result = clientAdapter.search(pageable, query);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getDocuments()).isNotNull();
        assertThat(result.getDocuments()).hasSize(1);
        assertThat(result.getDocuments().get(0).getBlogName()).isEqualTo(documents.get(0).getBlogName());
        assertThat(result.getDocuments().get(0).getContents()).isEqualTo(documents.get(0).getContents());
        assertThat(result.getDocuments().get(0).getDateTime()).isEqualTo(documents.get(0).getDateTime());
        assertThat(result.getDocuments().get(0).getTitle()).isEqualTo(documents.get(0).getTitle());
        assertThat(result.getDocuments().get(0).getUrl()).isEqualTo(documents.get(0).getUrl());
        assertThat(result.getPagination()).isNotNull();
        assertThat(result.getPagination().getPage()).isEqualTo(pageable.getPageNumber());
        assertThat(result.getPagination().getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getPagination().getTotalCount()).isEqualTo(meta.getTotalCount());
    }

    @Test
    void testIfSearchCanCallArgumentException() {
        // given
        String query = "한글날";
        Pageable pageable = PageRequest.of(1, 1000, Sort.by("accuracy"));
        given(kakaoBlogApiClient.search(any(KakaoBlogSearch.Param.class))).willThrow(FeignException.FeignClientException.class);

        // when & then
        assertThatThrownBy(() -> clientAdapter.search(pageable, query))
                .isInstanceOf(BlogKeywordException.class)
                .hasMessageContaining("요청이 잘못되었습니다.");
    }

    @Test
    void testIfSearchCanCallServerException() {
        // given
        String query = "한글날";
        Pageable pageable = PageRequest.of(1, 10, Sort.by("accuracy"));
        given(kakaoBlogApiClient.search(any(KakaoBlogSearch.Param.class))).willThrow(FeignException.FeignServerException.class);

        // when & then
        assertThatThrownBy(() -> clientAdapter.search(pageable, query))
                .isInstanceOf(BlogKeywordException.class)
                .hasMessageContaining("원격 API 서버에서 오류가 발생했습니다.");
    }

    @Test
    void testIfSearchCanCallUnknownException() {
        // given
        String query = "한글날";
        Pageable pageable = PageRequest.of(1, 10, Sort.by("accuracy"));
        given(kakaoBlogApiClient.search(any(KakaoBlogSearch.Param.class))).willThrow(RuntimeException.class);

        // when & then
        assertThatThrownBy(() -> clientAdapter.search(pageable, query))
                .isInstanceOf(BlogKeywordException.class)
                .hasMessageContaining("알 수 없는 오류입니다.");
    }
}