package kr.pe.karsei.client.kakao;

import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
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
        value = { "classpath:application-kakao.properties" }
)
@SpringBootApplication
public class KakaoBlogApiClientTest {
    @Autowired
    private KakaoBlogApiClient client;

    @Test
    void testIfClientCanBeCalled() {
        // given
        KakaoBlogSearch.Param param = KakaoBlogSearch.Param.builder()
                .query("한글날")
                .sort(KakaoBlogSearch.Param.Sort.ACCURACY)
                .page(1)
                .size(10)
                .build();

        // when
        KakaoBlogSearch.Info info = client.search(param);

        // then
        assertThat(info).isNotNull();
        assertThat(info.getDocuments()).isNotNull();
        assertThat(info.getMeta()).isNotNull();
        assertThat(info.getMeta().getTotalCount()).isGreaterThanOrEqualTo(0);
        assertThat(info.getMeta().getPageableCount()).isGreaterThanOrEqualTo(0);
    }
}