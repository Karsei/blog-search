package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.client.kakao.KakaoBlogApiClient;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import kr.pe.karsei.client.naver.NaverBlogApiClient;
import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogKeywordClientAdapterMockTest {
    @Mock
    private KakaoBlogApiClient kakaoBlogApiClient;
    @Mock
    private NaverBlogApiClient naverBlogApiClient;
    @InjectMocks
    private BlogKeywordClientAdapter clientAdapter;

    @Test
    void testIfKakaoSearchCanBeCalled() {
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
        Mono<KakaoBlogSearch.Info> info = Mono.just(KakaoBlogSearch.Info.builder()
                .documents(documents)
                .meta(meta)
                .build());
        given(kakaoBlogApiClient.search(any(KakaoBlogSearch.Param.class))).willReturn(info);

        // when
        FetchBlogKeyword result = clientAdapter.searchWithKakao(pageable, query);

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
    void testIfKakaoSearchCanCallRuntimeException() {
        // given
        String query = "한글날";
        Pageable pageable = PageRequest.of(1, 1000, Sort.by("accuracy"));
        given(kakaoBlogApiClient.search(any(KakaoBlogSearch.Param.class))).willThrow(RuntimeException.class);

        // when & then
        assertThatThrownBy(() -> clientAdapter.searchWithKakao(pageable, query))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testIfNaverSearchCanBeCalled() {
        // given
        String query = "한글날";
        String sort = "accuracy";
        Pageable pageable = PageRequest.of(1, 10, Sort.by(sort));

        List<NaverBlogSearch.Info.Item> documents = new ArrayList<>();
        documents.add(NaverBlogSearch.Info.Item.builder()
                .bloggerName("주말엔아빠여행사")
                .description("<b>한글날</b>에 한글박물관을 가보는게 나름 의미있었습니다. 물론 아직 다 이해하고 얻어갈 수준의 아이들은 아니지만 저는 언제나 그렇듯 “나 예전에 여기 와본 적 있는데!” 정도만 기억해주는 것도 충분하다는... ")
                .dateTime(LocalDate.of(2022, 10, 8))
                .title("서울 용산 / 2022년 <b>한글날</b> 국립한글박물관 방문")
                .link("https://blog.naver.com/nadongyup/222922559695")
                .build());
        NaverBlogSearch.Info info = NaverBlogSearch.Info.builder()
                .items(documents)
                .display(10)
                .total(333470)
                .build();
        given(naverBlogApiClient.search(any(NaverBlogSearch.Param.class))).willReturn(info);

        // when
        FetchBlogKeyword result = clientAdapter.searchWithNaver(pageable, query);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getDocuments()).isNotNull();
        assertThat(result.getDocuments()).hasSize(1);
        assertThat(result.getDocuments().get(0).getBlogName()).isEqualTo(documents.get(0).getBloggerName());
        assertThat(result.getDocuments().get(0).getContents()).isEqualTo(documents.get(0).getDescription());
        assertThat(result.getDocuments().get(0).getDateTime()).isEqualTo(documents.get(0).getDateTime().atStartOfDay().atZone(ZoneId.of("Asia/Seoul")));
        assertThat(result.getDocuments().get(0).getTitle()).isEqualTo(documents.get(0).getTitle());
        assertThat(result.getDocuments().get(0).getUrl()).isEqualTo(documents.get(0).getLink());
        assertThat(result.getPagination()).isNotNull();
        assertThat(result.getPagination().getPage()).isEqualTo(pageable.getPageNumber());
        assertThat(result.getPagination().getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getPagination().getTotalCount()).isEqualTo(info.getTotal());
    }

    @Test
    void testIfNaverSearchCanCallRuntimeException() {
        // given
        String query = "한글날";
        Pageable pageable = PageRequest.of(1, 1000, Sort.by("accuracy"));
        given(naverBlogApiClient.search(any(NaverBlogSearch.Param.class))).willThrow(RuntimeException.class);

        // when & then
        assertThatThrownBy(() -> clientAdapter.searchWithNaver(pageable, query))
                .isInstanceOf(RuntimeException.class);
    }
}