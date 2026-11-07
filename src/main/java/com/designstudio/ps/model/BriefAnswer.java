package com.designstudio.ps.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brief_answers")
@Data
@NoArgsConstructor
public class BriefAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brief_id", nullable = false)
    private Brief brief;

    @Column(nullable = false)
    private String questionCode;

    @Column(columnDefinition = "TEXT")
    private String answerText;
}