package kr.pe.karsei.blogsearch.repository;

import kr.pe.karsei.blogsearch.adapter.out.BlogKeywordEventStoreJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogKeywordEventStoreRepository extends JpaRepository<BlogKeywordEventStoreJpaEntity, Long> {
    List<BlogKeywordEventStoreJpaEntity> findAllByAck(boolean ack);
}
