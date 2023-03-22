package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.entity.BlogKeywordCountJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BlogKeywordCountRepositoryTest {
    @Autowired
    private BlogKeywordCountRepository countRepository;

    @BeforeEach
    void init() {
        List<BlogKeywordCountJpaEntity> list = new ArrayList<>();
        list.add(new BlogKeywordCountJpaEntity(null, "한글날", 100, null));
        list.add(new BlogKeywordCountJpaEntity(null, "해달", 400, null));
        list.add(new BlogKeywordCountJpaEntity(null, "토끼", 500, null));
        list.add(new BlogKeywordCountJpaEntity(null, "세종대왕", 200, null));
        list.add(new BlogKeywordCountJpaEntity(null, "사자", 600, null));
        list.add(new BlogKeywordCountJpaEntity(null, "호랑이", 300, null));
        countRepository.saveAll(list);
    }

    @Test
    void testFindingAllByOrderByHitDesc() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<BlogKeywordCountJpaEntity> list = countRepository.findAllByCreatedAtGreaterThanEqualOrderByHitDesc(pageable, LocalDate.now().atStartOfDay());

        // then
        assertThat(list).isNotNull();
        assertThat(list).hasSize(6);
        assertThat(list.get(0).getKeyword()).isEqualTo("사자");
        assertThat(list.get(1).getKeyword()).isEqualTo("토끼");
        assertThat(list.get(2).getKeyword()).isEqualTo("해달");
        assertThat(list.get(3).getKeyword()).isEqualTo("호랑이");
        assertThat(list.get(4).getKeyword()).isEqualTo("세종대왕");
        assertThat(list.get(5).getKeyword()).isEqualTo("한글날");
    }
}