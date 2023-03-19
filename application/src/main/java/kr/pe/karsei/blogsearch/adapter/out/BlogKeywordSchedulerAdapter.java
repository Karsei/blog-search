package kr.pe.karsei.blogsearch.adapter.out;

import jakarta.persistence.EntityManager;
import kr.pe.karsei.blogsearch.repository.BlogKeywordCountRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventSnapshotRepository;
import kr.pe.karsei.blogsearch.repository.BlogKeywordEventStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BlogKeywordSchedulerAdapter {
    private final EntityManager entityManager;
    private final BlogKeywordCountRepository collectRepository;
    private final BlogKeywordEventStoreRepository eventStoreRepository;
    private final BlogKeywordEventSnapshotRepository eventSnapshotRepository;

    @Scheduled(cron = "*/10 * * * * *")
    @Transactional
    void eventListen() {
        // 마지막 스냅샷 확인
        BlogKeywordEventSnapshotJpaEntity id = eventSnapshotRepository.findFirstBy()
                .orElseGet(() -> new BlogKeywordEventSnapshotJpaEntity(null, 0L));

        // 이벤트 조회
        try (Stream<BlogKeywordEventStoreJpaEntity> eventStream = eventStoreRepository.findAllByIdGreaterThanOrderByCreatedAtAsc(id.getLastId())) {
            Iterator<BlogKeywordEventStoreJpaEntity> iterator = eventStream.iterator();
            while (iterator.hasNext()) {
                BlogKeywordEventStoreJpaEntity event = iterator.next();

                // 수집된 키워드를 불러온다.
                String keyword = event.getPayload();
                BlogKeywordCountJpaEntity keywordEntity = collectRepository.findByKeyword(keyword)
                        .orElseGet(() -> new BlogKeywordCountJpaEntity(null, keyword, 0, null));

                // 저장
                collectRepository.save(new BlogKeywordCountJpaEntity(
                        keywordEntity.getId(),
                        keywordEntity.getKeyword(),
                        keywordEntity.getHit() + 1,
                        keywordEntity.getCreatedAt()
                ));

                // 마지막 이벤트 번호 저장
                if (!iterator.hasNext()) {
                    eventSnapshotRepository.save(new BlogKeywordEventSnapshotJpaEntity(id.getId(), event.getId()));
                }

                // 영속성 컨텍스트에서 해제
                entityManager.detach(event);
            }
        }
    }
}
