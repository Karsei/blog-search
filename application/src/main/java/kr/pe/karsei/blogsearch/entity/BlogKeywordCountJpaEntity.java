package kr.pe.karsei.blogsearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "keyword_count",
        indexes = { @Index(name = "date_index", columnList = "created_at")}
)
@Getter
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlogKeywordCountJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String keyword;

    @Column
    private Integer hit;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
