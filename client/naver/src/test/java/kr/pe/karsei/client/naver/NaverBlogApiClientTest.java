package kr.pe.karsei.client.naver;

import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaverBlogApiClientTest {
    @Autowired
    private NaverBlogApiClient naverBlogApiClient;

    @Test
    void testIfClientCanBeCalled() {
        // given
        NaverBlogSearch.Param param = NaverBlogSearch.Param.builder()
                .query("네이버")
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
        assertThat(info.getItems().get(0).getBloggerName()).isNotBlank();
        assertThat(info.getItems().get(0).getDescription()).isNotBlank();
        assertThat(info.getItems().get(0).getLink()).isNotBlank();
        assertThat(info.getItems().get(0).getTitle()).isNotBlank();
        assertThat(info.getItems().get(0).getDateTime()).isNotNull();
        assertThat(info.getTotal()).isGreaterThan(0);
        assertThat(info.getDisplay()).isGreaterThan(0);
    }
}