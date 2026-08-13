package com.dnd.watertasting;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "tastings")
public class WaterTasting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Adventurer name is required")
    @Column(nullable = false)
    private String adventurerName;

    @NotBlank(message = "Water Sample ID is required")
    @Column(name = "water_sample_id", nullable = false)
    private String waterSampleId;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer clarity;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer color;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer odor;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer carbonation;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer body;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer temperature;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "mineral_intensity", nullable = false)
    private Integer mineralIntensity;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer sweetness;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer bitterness;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer salinity;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "ph_sensation", nullable = false)
    private Integer phSensation;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer duration;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer harmony;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "personal_enjoyment", nullable = false)
    private Integer personalEnjoyment;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdventurerName() {
        return adventurerName;
    }

    public void setAdventurerName(String adventurerName) {
        this.adventurerName = adventurerName;
    }

    public String getWaterSampleId() {
        return waterSampleId;
    }

    public void setWaterSampleId(String waterSampleId) {
        this.waterSampleId = waterSampleId;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getOdor() {
        return odor;
    }

    public void setOdor(Integer odor) {
        this.odor = odor;
    }

    public Integer getCarbonation() {
        return carbonation;
    }

    public void setCarbonation(Integer carbonation) {
        this.carbonation = carbonation;
    }

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getMineralIntensity() {
        return mineralIntensity;
    }

    public void setMineralIntensity(Integer mineralIntensity) {
        this.mineralIntensity = mineralIntensity;
    }

    public Integer getSweetness() {
        return sweetness;
    }

    public void setSweetness(Integer sweetness) {
        this.sweetness = sweetness;
    }

    public Integer getBitterness() {
        return bitterness;
    }

    public void setBitterness(Integer bitterness) {
        this.bitterness = bitterness;
    }

    public Integer getSalinity() {
        return salinity;
    }

    public void setSalinity(Integer salinity) {
        this.salinity = salinity;
    }

    public Integer getPhSensation() {
        return phSensation;
    }

    public void setPhSensation(Integer phSensation) {
        this.phSensation = phSensation;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getHarmony() {
        return harmony;
    }

    public void setHarmony(Integer harmony) {
        this.harmony = harmony;
    }

    public Integer getPersonalEnjoyment() {
        return personalEnjoyment;
    }

    public void setPersonalEnjoyment(Integer personalEnjoyment) {
        this.personalEnjoyment = personalEnjoyment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
