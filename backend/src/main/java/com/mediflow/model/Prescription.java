package com.mediflow.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Entity
public class Prescription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Visit visit;
    @NotBlank private String medicineName;
    @NotBlank private String dosage;
    @NotBlank private String frequency;
    @NotBlank private String duration;
    private String instructions;
    private boolean dispensed;
    private LocalDateTime dispensedAt;

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Visit getVisit(){return visit;} public void setVisit(Visit v){visit=v;}
    public String getMedicineName(){return medicineName;} public void setMedicineName(String v){medicineName=v;}
    public String getDosage(){return dosage;} public void setDosage(String v){dosage=v;}
    public String getFrequency(){return frequency;} public void setFrequency(String v){frequency=v;}
    public String getDuration(){return duration;} public void setDuration(String v){duration=v;}
    public String getInstructions(){return instructions;} public void setInstructions(String v){instructions=v;}
    public boolean isDispensed(){return dispensed;} public void setDispensed(boolean v){dispensed=v;}
    public LocalDateTime getDispensedAt(){return dispensedAt;} public void setDispensedAt(LocalDateTime v){dispensedAt=v;}
}
