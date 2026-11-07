package com.designstudio.ps.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "briefs")
@Data
@NoArgsConstructor
public class Brief {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String stylePreference;
    private String budgetRange;

    @Enumerated(EnumType.STRING)
    private BriefStatus status = BriefStatus.SCORING;

    private LocalDateTime scoredAt;

    @OneToMany(mappedBy = "brief", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BriefAnswer> answers;

    public enum BriefStatus {
        SCORING, SCORED, FALLBACK_MODE
    }
}