package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.entity.BlogKeywordCountJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlogKeywordCountRepository extends JpaRepository<BlogKeywordCountJpaEntity, Long> {
    List<BlogKeywordCountJpaEntity> findAllByCreatedAtGreaterThanEqualOrderByHitDesc(Pageable pageable, LocalDateTime createdAt);
    Optional<BlogKeywordCountJpaEntity> findByCreatedAtAndKeyword(LocalDateTime createdAt, String keyword);
}
