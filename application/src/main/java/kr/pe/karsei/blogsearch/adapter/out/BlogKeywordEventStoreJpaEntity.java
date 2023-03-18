package kr.pe.karsei.blogsearch.adapter.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "event_store")
@Getter
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlogKeywordEventStoreJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String payload;

    @Column
    private boolean ack;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    public void updateAck(boolean ack) {
        this.ack = ack;
    }
}
