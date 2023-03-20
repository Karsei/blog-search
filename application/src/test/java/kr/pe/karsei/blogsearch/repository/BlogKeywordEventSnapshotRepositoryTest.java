package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.entity.BlogKeywordEventSnapshotJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BlogKeywordEventSnapshotRepositoryTest {
    @Autowired
    private BlogKeywordEventSnapshotRepository eventSnapshotRepository;

    @BeforeEach
    void init() {
        eventSnapshotRepository.save(new BlogKeywordEventSnapshotJpaEntity(null, 10L));
    }

    @Test
    void testFindingFirstBy() {
        // given

        // when
        Optional<BlogKeywordEventSnapshotJpaEntity> row = eventSnapshotRepository.findFirstBy();

        // then
        assertThat(row).isPresent();
        assertThat(row.get().getId()).isEqualTo(1L);
        assertThat(row.get().getLastId()).isEqualTo(10L);
    }

}