package com.designstudio.ps.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommended_products")
@Data
@NoArgsConstructor
public class RecommendedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id", nullable = false)
    private Recommendation recommendation;

    @Column(nullable = false)
    private String productName;

    private Double price;

    private Float relevanceScore;

    @Column(columnDefinition = "TEXT")
    private String reason;
}