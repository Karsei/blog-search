package kr.pe.karsei.blogsearch.adapter.out;

import kr.pe.karsei.blogsearch.entity.BlogKeywordCountJpaEntity;
import kr.pe.karsei.blogsearch.entity.BlogKeywordEventSnapshotJpaEntity;
import kr.pe.karsei.blogsearch.entity.BlogKeywordEventStoreJpaEntity;
import kr.pe.karsei.blogsearch.repository.BlogKeywordCountRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventSnapshotRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventStoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "scheduler.enabled=false")
class BlogKeywordSchedulerAdapterTest {
    @Autowired
    private BlogKeywordSchedulerAdapter adapter;
    @Autowired
    private BlogKeywordCountRepository countRepository;
    @Autowired
    private BlogKeywordEventStoreRepository eventStoreRepository;
    @Autowired
    private BlogKeywordEventSnapshotRepository eventSnapshotRepository;

    @BeforeEach
    void init() {
        List<BlogKeywordEventStoreJpaEntity> storeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            storeList.add(new BlogKeywordEventStoreJpaEntity(null, "한글날", null));
        }
        for (int i = 0; i < 77; i++) {
            storeList.add(new BlogKeywordEventStoreJpaEntity(null, "세종대왕", null));
        }
        eventStoreRepository.saveAll(storeList);
    }

    @Test
    @DisplayName("데이터가 있고, 이전에 진행한 경우가 없을 경우 개수 감안해서 내림차순으로 출력")
    void testIfSchedulerIsDefault() {
        // given

        // when
        adapter.countScheduler();

        // then
        List<BlogKeywordCountJpaEntity> list = countRepository.findAllByOrderByHitDesc(PageRequest.of(0, 10));
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
        assertThat(list.get(0)).isNotNull();
        assertThat(list.get(0).getKeyword()).isEqualTo("한글날");
        assertThat(list.get(0).getHit()).isEqualTo(100);
        assertThat(list.get(1)).isNotNull();
        assertThat(list.get(1).getKeyword()).isEqualTo("세종대왕");
        assertThat(list.get(1).getHit()).isEqualTo(77);
    }

    @Test
    @DisplayName("데이터가 있고 이전에 진행한 경우가 있을 때 개수 감안해서 내림차순으로 출력")
    void testIfSchedulerIsDefaultWithSnapshot() {
        // given
        BlogKeywordEventSnapshotJpaEntity snapshotEntity = new BlogKeywordEventSnapshotJpaEntity(null, 48L);
        eventSnapshotRepository.save(snapshotEntity);

        // when
        adapter.countScheduler();

        // then
        List<BlogKeywordCountJpaEntity> list = countRepository.findAllByOrderByHitDesc(PageRequest.of(0, 10));
        assertThat(list).isNotNull();
        assertThat(list).hasSize(2);
        assertThat(list.get(0)).isNotNull();
        assertThat(list.get(0).getKeyword()).isEqualTo("세종대왕");
        assertThat(list.get(0).getHit()).isEqualTo(77);
        assertThat(list.get(1)).isNotNull();
        assertThat(list.get(1).getKeyword()).isEqualTo("한글날");
        assertThat(list.get(1).getHit()).isEqualTo(52);
    }
}