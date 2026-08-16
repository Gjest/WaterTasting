package com.dnd.watertasting;

import java.time.Instant;

public record TastingDto(
        Long id,
        String adventurerName,
        String waterSampleId,
        Integer clarity,
        Integer color,
        Integer odor,
        Integer carbonation,
        Integer body,
        Integer temperature,
        Integer mineralIntensity,
        Integer sweetness,
        Integer bitterness,
        Integer salinity,
        Integer phSensation,
        Integer duration,
        Integer harmony,
        Integer personalEnjoyment,
        String notes,
        Instant createdAt) {

    static TastingDto from(WaterTasting t) {
        return new TastingDto(
                t.getId(),
                t.getAdventurerName(),
                t.getWaterSampleId(),
                t.getClarity(),
                t.getColor(),
                t.getOdor(),
                t.getCarbonation(),
                t.getBody(),
                t.getTemperature(),
                t.getMineralIntensity(),
                t.getSweetness(),
                t.getBitterness(),
                t.getSalinity(),
                t.getPhSensation(),
                t.getDuration(),
                t.getHarmony(),
                t.getPersonalEnjoyment(),
                t.getNotes(),
                t.getCreatedAt());
    }
}
