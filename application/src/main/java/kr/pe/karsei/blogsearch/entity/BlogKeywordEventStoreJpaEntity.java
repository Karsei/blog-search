package kr.pe.karsei.blogsearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "keyword_event_store")
@Getter
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlogKeywordEventStoreJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String payload;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
