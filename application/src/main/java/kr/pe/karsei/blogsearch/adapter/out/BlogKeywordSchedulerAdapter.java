package kr.pe.karsei.blogsearch.adapter.out;

import jakarta.persistence.EntityManager;
import kr.pe.karsei.blogsearch.entity.BlogKeywordCountJpaEntity;
import kr.pe.karsei.blogsearch.entity.BlogKeywordEventSnapshotJpaEntity;
import kr.pe.karsei.blogsearch.entity.BlogKeywordEventStoreJpaEntity;
import kr.pe.karsei.blogsearch.repository.BlogKeywordCountRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventSnapshotRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BlogKeywordSchedulerAdapter {
    private final EntityManager entityManager;
    private final BlogKeywordCountRepository countRepository;
    private final BlogKeywordEventStoreRepository eventStoreRepository;
    private final BlogKeywordEventSnapshotRepository eventSnapshotRepository;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void countScheduler() {
        // 마지막으로 조회했던 이벤트 번호 확인
        BlogKeywordEventSnapshotJpaEntity lastEntity = eventSnapshotRepository.findFirstBy()
                .orElseGet(() -> new BlogKeywordEventSnapshotJpaEntity(null, 0L));

        // 이벤트 조회
        try (Stream<BlogKeywordEventStoreJpaEntity> eventStream = eventStoreRepository.findAllByIdGreaterThanOrderByIdAsc(lastEntity.getLastId())) {
            Iterator<BlogKeywordEventStoreJpaEntity> iterator = eventStream.iterator();
            while (iterator.hasNext()) {
                BlogKeywordEventStoreJpaEntity eventEntity = iterator.next();

                // 키워드 조회 후 카운트 저장
                findKeywordAndSaveCount(eventEntity);

                // 영속성 컨텍스트에서 해제
                entityManager.detach(eventEntity);

                // 마지막으로 조회한 이벤트 번호 저장
                if (!iterator.hasNext()) {
                    eventSnapshotRepository.save(new BlogKeywordEventSnapshotJpaEntity(lastEntity.getId(), eventEntity.getId()));
                }
            }
        }
    }

    private void findKeywordAndSaveCount(final BlogKeywordEventStoreJpaEntity eventEntity) {
        // 수집된 키워드를 불러온다.
        String keyword = eventEntity.getPayload();

        // 저장
        countRepository.save(countRepository.findByCreatedAtAndKeyword(LocalDate.now().atStartOfDay(), keyword)
                .map(blogKeywordCountJpaEntity -> new BlogKeywordCountJpaEntity(
                        blogKeywordCountJpaEntity.getId(),
                        blogKeywordCountJpaEntity.getKeyword(),
                        blogKeywordCountJpaEntity.getHit() + 1,
                        blogKeywordCountJpaEntity.getCreatedAt()))
                .orElseGet(() -> new BlogKeywordCountJpaEntity(null, keyword, 1, null))
        );
    }
}
