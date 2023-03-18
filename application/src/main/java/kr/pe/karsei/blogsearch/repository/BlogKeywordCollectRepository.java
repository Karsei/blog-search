package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.adapter.out.BlogKeywordCollectJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogKeywordCollectRepository extends JpaRepository<BlogKeywordCollectJpaEntity, Long> {
    List<BlogKeywordCollectJpaEntity> findAllByOrderByHitDesc(Pageable pageable);
    Optional<BlogKeywordCollectJpaEntity> findByKeyword(String keyword);
}
