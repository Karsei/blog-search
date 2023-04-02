package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.client.kakao.KakaoBlogApiClient;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import kr.pe.karsei.client.naver.NaverBlogApiClient;
import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled // 인증 정보 기입 후 필요 시 진행
@SpringBootTest
class BlogKeywordClientAdapterTest {
    @Autowired
    private KakaoBlogApiClient kakaoBlogApiClient;
    @Autowired
    private NaverBlogApiClient naverBlogApiClient;

    @Test
    void testIfSearchingWithKakaoCanBeRetrievedByAccuracy() {
        // given
        KakaoBlogSearch.Param param = KakaoBlogSearch.Param.builder()
                .query("한글날")
                .sort(KakaoBlogSearch.Param.Sort.ACCURACY)
                .page(1)
                .size(10)
                .build();

        // when
        KakaoBlogSearch.Info info = kakaoBlogApiClient.search(param)
                .block();

        // then
        assertThat(info).isNotNull();
        assertThat(info.getDocuments()).isNotNull();
        assertThat(info.getDocuments()).hasSizeGreaterThan(0);
        assertThat(info.getMeta()).isNotNull();
    }

    @Test
    void testIfSearchingWithKakaoCanBeRetrievedByRecency() {
        // given
        KakaoBlogSearch.Param param = KakaoBlogSearch.Param.builder()
                .query("한글날")
                .sort(KakaoBlogSearch.Param.Sort.RECENCY)
                .page(1)
                .size(10)
                .build();

        // when
        KakaoBlogSearch.Info info = kakaoBlogApiClient.search(param)
                .block();

        // then
        assertThat(info).isNotNull();
        assertThat(info.getDocuments()).isNotNull();
        assertThat(info.getDocuments()).hasSizeGreaterThan(0);
        assertThat(info.getMeta()).isNotNull();
    }

    @Test
    void testIfSearchingWithNaverCanBeRetrievedByAccuracy() {
        // given
        NaverBlogSearch.Param param = NaverBlogSearch.Param.builder()
                .query("한글날")
                .sort(NaverBlogSearch.Param.Sort.SIM)
                .start(1)
                .display(10)
                .build();

        // when
        NaverBlogSearch.Info info = naverBlogApiClient.search(param);

        // then
        assertThat(info).isNotNull();
        assertThat(info.getItems()).isNotNull();
        assertThat(info.getItems()).hasSizeGreaterThan(0);
        assertThat(info.getTotal()).isGreaterThan(0);
    }

    @Test
    void testIfSearchingWithNaverCanBeRetrievedByRecency() {
        // given
        NaverBlogSearch.Param param = NaverBlogSearch.Param.builder()
                .query("한글날")
                .sort(NaverBlogSearch.Param.Sort.SIM)
                .start(1)
                .display(10)
                .build();

        // when
        NaverBlogSearch.Info info = naverBlogApiClient.search(param);

        // then
        assertThat(info).isNotNull();
        assertThat(info.getItems()).isNotNull();
        assertThat(info.getItems()).hasSizeGreaterThan(0);
        assertThat(info.getTotal()).isGreaterThan(0);
    }
}