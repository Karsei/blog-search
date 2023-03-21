package kr.pe.karsei.client.naver;

import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled // 인증 정보 기입 후 필요 시 테스트
@SpringBootTest
@TestPropertySource(
        value = { "classpath:application-naver.properties" }
)
@SpringBootApplication
public class NaverBlogApiClientTest {
    @Autowired
    private NaverBlogApiClient client;

    @Test
    void testIfClientCanBeCalled() {
        // given
        NaverBlogSearch.Param param = NaverBlogSearch.Param.builder()
                .query("한글날")
                .sort(NaverBlogSearch.Param.Sort.SIM)
                .start(1)
                .display(10)
                .build();

        // when
        NaverBlogSearch.Info info = client.search(param);

        // then
        assertThat(info).isNotNull();
        assertThat(info.getItems()).isNotNull();
        assertThat(info.getDisplay()).isGreaterThanOrEqualTo(0);
        assertThat(info.getTotal()).isGreaterThanOrEqualTo(0);
    }
}