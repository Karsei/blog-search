package kr.pe.karsei.blogsearch.adapter.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "keyword_count")
@Getter
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlogKeywordCountJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String keyword;

    @Column
    private Integer hit;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
