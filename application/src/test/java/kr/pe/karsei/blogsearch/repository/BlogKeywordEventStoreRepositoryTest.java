package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.entity.BlogKeywordEventStoreJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BlogKeywordEventStoreRepositoryTest {
    @Autowired
    private BlogKeywordEventStoreRepository eventStoreRepository;

    @BeforeEach
    void init() {
        List<BlogKeywordEventStoreJpaEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new BlogKeywordEventStoreJpaEntity(null, "한글날", null));
        }
        eventStoreRepository.saveAll(list);
    }

    @Test
    void testFindingAllByIdGreaterThanOrderByCreatedAtAsc() {
        // given
        long startId = 0L;

        // when
        Stream<BlogKeywordEventStoreJpaEntity> streamList = eventStoreRepository.findAllByIdGreaterThanOrderByCreatedAtAsc(startId);

        // then
        assertThat(streamList).isNotNull();
        assertThat(streamList).hasSize(10);
    }
}