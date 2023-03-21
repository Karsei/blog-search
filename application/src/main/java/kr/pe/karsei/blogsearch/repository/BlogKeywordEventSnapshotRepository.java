package kr.pe.karsei.blogsearch.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import kr.pe.karsei.blogsearch.entity.BlogKeywordEventSnapshotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface BlogKeywordEventSnapshotRepository extends JpaRepository<BlogKeywordEventSnapshotJpaEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")})
    Optional<BlogKeywordEventSnapshotJpaEntity> findFirstBy();
}
