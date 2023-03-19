package kr.pe.karsei.client.kakao;

import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KakaoBlogApiClientTest {
    @Autowired
    private KakaoBlogApiClient kakaoBlogApiClient;

    @Test
    void testIfClientCanBeCalled() {
        // given
        KakaoBlogSearch.Param param = KakaoBlogSearch.Param.builder()
                .query("카카오")
                .sort(KakaoBlogSearch.Param.Sort.ACCURACY)
                .page(1)
                .size(10)
                .build();

        // when
        KakaoBlogSearch.Info info = kakaoBlogApiClient.search(param);

        // then
        assertThat(info).isNotNull();
        assertThat(info.getDocuments()).isNotNull();
        assertThat(info.getDocuments()).hasSizeGreaterThan(0);
        assertThat(info.getDocuments().get(0).getBlogName()).isNotBlank();
        assertThat(info.getDocuments().get(0).getContents()).isNotBlank();
        assertThat(info.getDocuments().get(0).getUrl()).isNotBlank();
        assertThat(info.getDocuments().get(0).getTitle()).isNotBlank();
        assertThat(info.getDocuments().get(0).getDateTime()).isNotNull();
        assertThat(info.getMeta()).isNotNull();
        assertThat(info.getMeta().getPageableCount()).isGreaterThan(0);
        assertThat(info.getMeta().getTotalCount()).isGreaterThan(0);
    }
}
