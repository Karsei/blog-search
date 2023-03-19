package kr.pe.karsei.blogsearch.adapter.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "event_snapshot")
@Getter
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlogKeywordEventSnapshotJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "last_id")
    private Long lastId;
}
