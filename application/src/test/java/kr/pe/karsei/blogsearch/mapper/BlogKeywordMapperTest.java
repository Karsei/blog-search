package kr.pe.karsei.blogsearch.mapper;

import kr.pe.karsei.blogsearch.adapter.out.BlogKeywordCountJpaEntity;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        FetchBlogKeyword result = BlogKeywordMapper.mapKakaoBlogSearchToSearchBlogInfo(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getDocuments()).isNotNull();
        assertThat(result.getDocuments()).hasSize(1);
        assertThat(result.getDocuments().get(0).getBlogName()).isEqualTo(documents.get(0).getBlogName());
        assertThat(result.getDocuments().get(0).getContents()).isEqualTo(documents.get(0).getContents());
        assertThat(result.getDocuments().get(0).getDateTime()).isEqualTo(documents.get(0).getDateTime());
        assertThat(result.getDocuments().get(0).getTitle()).isEqualTo(documents.get(0).getTitle());
        assertThat(result.getDocuments().get(0).getUrl()).isEqualTo(documents.get(0).getUrl());
        assertThat(result.getMeta()).isNotNull();
        assertThat(result.getMeta().getPageableCount()).isEqualTo(meta.getPageableCount());
        assertThat(result.getMeta().getTotalCount()).isEqualTo(meta.getTotalCount());
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
}