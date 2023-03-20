package kr.pe.karsei.blogsearch.mapper;

import kr.pe.karsei.blogsearch.entity.BlogKeywordCountJpaEntity;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BlogKeywordMapperTest {
    @Test
    void testMappingSearchParamToKakaoBlogSearchParamWithNoSort() {
        // given
        Pageable pageable = PageRequest.of(1, 10);
        String query = "한글날";

        // when
        KakaoBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(KakaoBlogSearch.Param.Sort.ACCURACY);
        assertThat(param.getPage()).isEqualTo(1);
        assertThat(param.getSize()).isEqualTo(10);
    }

    @Test
    void testMappingSearchParamToKakaoBlogSearchParamWithAccuracySort() {
        // given
        Pageable pageable = PageRequest.of(1, 10, Sort.by("accuracy"));
        String query = "한글날";

        // when
        KakaoBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(KakaoBlogSearch.Param.Sort.ACCURACY);
        assertThat(param.getPage()).isEqualTo(1);
        assertThat(param.getSize()).isEqualTo(10);
    }

    @Test
    void testMappingSearchParamToKakaoBlogSearchParamWithRecencySort() {
        // given
        Pageable pageable = PageRequest.of(1, 10, Sort.by("recency"));
        String query = "한글날";

        // when
        KakaoBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(KakaoBlogSearch.Param.Sort.RECENCY);
        assertThat(param.getPage()).isEqualTo(1);
        assertThat(param.getSize()).isEqualTo(10);
    }

    @Test
    void testMappingSearchParamToKakaoBlogSearchParamWithInvalidSort() {
        // given
        Pageable pageable = PageRequest.of(1, 10, Sort.by("asdf"));
        String query = "한글날";

        // when
        KakaoBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToKakaoBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(KakaoBlogSearch.Param.Sort.ACCURACY);
        assertThat(param.getPage()).isEqualTo(1);
        assertThat(param.getSize()).isEqualTo(10);
    }

    @Test
    void testMappingKakaoBlogSearchToSearchBlogInfo() {
        // given
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

        // when
        FetchBlogKeyword result = BlogKeywordMapper.mapKakaoBlogSearchToSearchBlogInfo(pageable, info);

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
    void testMappingCountEntityListToDto() {
        // given
        List<BlogKeywordCountJpaEntity> entities = new ArrayList<>();
        entities.add(new BlogKeywordCountJpaEntity(1L, "한글날", 10, new Date()));
        entities.add(new BlogKeywordCountJpaEntity(2L, "세종대왕", 26, new Date()));

        // when
        List<FetchBlogKeywordTop> result = BlogKeywordMapper.mapCountEntityListToDto(entities);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getKeyword()).isEqualTo(entities.get(0).getKeyword());
        assertThat(result.get(0).getHit()).isEqualTo(entities.get(0).getHit());
        assertThat(result.get(1).getKeyword()).isEqualTo(entities.get(1).getKeyword());
        assertThat(result.get(1).getHit()).isEqualTo(entities.get(1).getHit());
    }

    @Test
    void testMappingSearchParamToNaverBlogSearchParamWithNoSort() {
        // given
        Pageable pageable = PageRequest.of(1, 10);
        String query = "한글날";

        // when
        NaverBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToNaverBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(NaverBlogSearch.Param.Sort.SIM);
        assertThat(param.getStart()).isEqualTo(1);
        assertThat(param.getDisplay()).isEqualTo(10);
    }

    @Test
    void testMappingSearchParamToNaverBlogSearchParamWithAccuracySort() {
        // given
        Pageable pageable = PageRequest.of(1, 10, Sort.by("accuracy"));
        String query = "한글날";

        // when
        NaverBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToNaverBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(NaverBlogSearch.Param.Sort.SIM);
        assertThat(param.getStart()).isEqualTo(1);
        assertThat(param.getDisplay()).isEqualTo(10);
    }

    @Test
    void testMappingSearchParamToNaverBlogSearchParamWithRecencySort() {
        // given
        Pageable pageable = PageRequest.of(1, 10, Sort.by("recency"));
        String query = "한글날";

        // when
        NaverBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToNaverBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(NaverBlogSearch.Param.Sort.DATE);
        assertThat(param.getStart()).isEqualTo(1);
        assertThat(param.getDisplay()).isEqualTo(10);
    }

    @Test
    void testMappingSearchParamToNaverBlogSearchParamWithInvalidSort() {
        // given
        Pageable pageable = PageRequest.of(1, 10, Sort.by("asdf"));
        String query = "한글날";

        // when
        NaverBlogSearch.Param param = BlogKeywordMapper.mapSearchParamToNaverBlogSearchParam(pageable, query);

        // then
        assertThat(param).isNotNull();
        assertThat(param.getQuery()).isNotBlank();
        assertThat(param.getSort()).isNotNull();
        assertThat(param.getSort()).isEqualTo(NaverBlogSearch.Param.Sort.SIM);
        assertThat(param.getStart()).isEqualTo(1);
        assertThat(param.getDisplay()).isEqualTo(10);
    }

    @Test
    void testMappingNaverBlogSearchToSearchBlogInfo() {
        // given
        String sort = "accuracy";
        Pageable pageable = PageRequest.of(1, 10, Sort.by(sort));

        List<NaverBlogSearch.Info.Item> documents = new ArrayList<>();
        documents.add(NaverBlogSearch.Info.Item.builder()
                .bloggerName("주말엔아빠여행사")
                .description("<b>한글날</b>에 한글박물관을 가보는게 나름 의미있었습니다. 물론 아직 다 이해하고 얻어갈 수준의 아이들은 아니지만 저는 언제나 그렇듯 “나 예전에 여기 와본 적 있는데!” 정도만 기억해주는 것도 충분하다는... ")
                .dateTime(LocalDate.of(2022, 11, 7))
                .title("서울 용산 / 2022년 <b>한글날</b> 국립한글박물관 방문")
                .link("https://blog.naver.com/nadongyup/222922559695")
                .build());
        NaverBlogSearch.Info info = NaverBlogSearch.Info.builder()
                .items(documents)
                .total(333470)
                .display(10)
                .build();

        // when
        FetchBlogKeyword result = BlogKeywordMapper.mapNaverBlogSearchToSearchBlogInfo(pageable, info);

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
}