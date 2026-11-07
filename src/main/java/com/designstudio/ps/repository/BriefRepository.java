package com.designstudio.ps.repository;

import com.designstudio.ps.model.Brief;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BriefRepository extends JpaRepository<Brief, Long> {
    List<Brief> findByUserId(Long userId);
    List<Brief> findByStatus(Brief.BriefStatus status);
}