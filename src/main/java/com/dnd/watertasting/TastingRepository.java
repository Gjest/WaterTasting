package com.dnd.watertasting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface TastingRepository extends JpaRepository<WaterTasting, Long> {

    List<WaterTasting> findAllByOrderByCreatedAtDesc();

    @Query("select t.waterSampleId, count(t) from WaterTasting t group by t.waterSampleId order by lower(t.waterSampleId)")
    List<Object[]> countByWaterSampleId();

    @Query("select t.adventurerName, count(t) from WaterTasting t group by t.adventurerName order by lower(t.adventurerName)")
    List<Object[]> countByAdventurerName();

    @Modifying
    @Transactional
    @Query("update WaterTasting t set t.waterSampleId = :target where t.waterSampleId in :sources")
    int mergeWaterSampleIds(@Param("target") String target, @Param("sources") Collection<String> sources);

    @Modifying
    @Transactional
    @Query("update WaterTasting t set t.adventurerName = :target where t.adventurerName in :sources")
    int mergeAdventurerNames(@Param("target") String target, @Param("sources") Collection<String> sources);
}
