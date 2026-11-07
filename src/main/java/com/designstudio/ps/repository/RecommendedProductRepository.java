package com.designstudio.ps.repository;

import com.designstudio.ps.model.RecommendedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedProductRepository extends JpaRepository<RecommendedProduct, Long> {
}