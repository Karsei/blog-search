package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.entity.BlogKeywordEventStoreJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface BlogKeywordEventStoreRepository extends JpaRepository<BlogKeywordEventStoreJpaEntity, Long> {
    Stream<BlogKeywordEventStoreJpaEntity> findAllByIdGreaterThanOrderByIdAsc(Long id);
}