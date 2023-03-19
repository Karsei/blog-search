package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.adapter.out.BlogKeywordCountJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogKeywordCountRepository extends JpaRepository<BlogKeywordCountJpaEntity, Long> {
    List<BlogKeywordCountJpaEntity> findAllByOrderByHitDesc(Pageable pageable);
    Optional<BlogKeywordCountJpaEntity> findByKeyword(String keyword);
}
