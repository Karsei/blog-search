package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.entity.BlogKeywordEventSnapshotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogKeywordEventSnapshotRepository extends JpaRepository<BlogKeywordEventSnapshotJpaEntity, Long> {
    Optional<BlogKeywordEventSnapshotJpaEntity> findFirstBy();
}
