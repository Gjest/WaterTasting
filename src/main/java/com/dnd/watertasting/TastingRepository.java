package com.dnd.watertasting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TastingRepository extends JpaRepository<WaterTasting, Long> {

    List<WaterTasting> findAllByOrderByCreatedAtDesc();
}
