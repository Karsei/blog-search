package kr.pe.karsei.blogsearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "keyword_event_snapshot")
@Getter
@AllArgsConstructor @NoArgsConstructor
public class BlogKeywordEventSnapshotJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "last_id")
    private Long lastId;
}
