package com.mediflow.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Entity
public class Visit {
    public enum Status { WAITING_FOR_VITALS, WAITING_FOR_DOCTOR, SENT_TO_PHARMACY, COMPLETED }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Patient patient;
    private LocalDateTime visitDate = LocalDateTime.now();
    @NotBlank private String reason;
    @Enumerated(EnumType.STRING) private Status status = Status.WAITING_FOR_VITALS;
    private Double temperature;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private Integer pulse;
    private Double weight;
    private Double height;
    private Integer oxygenSaturation;
    @Column(length=3000) private String clinicalNotes;
    @Column(length=1500) private String diagnosis;
    private String doctorName;

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Patient getPatient(){return patient;} public void setPatient(Patient v){patient=v;}
    public LocalDateTime getVisitDate(){return visitDate;} public void setVisitDate(LocalDateTime v){visitDate=v;}
    public String getReason(){return reason;} public void setReason(String v){reason=v;}
    public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
    public Double getTemperature(){return temperature;} public void setTemperature(Double v){temperature=v;}
    public Integer getSystolicPressure(){return systolicPressure;} public void setSystolicPressure(Integer v){systolicPressure=v;}
    public Integer getDiastolicPressure(){return diastolicPressure;} public void setDiastolicPressure(Integer v){diastolicPressure=v;}
    public Integer getPulse(){return pulse;} public void setPulse(Integer v){pulse=v;}
    public Double getWeight(){return weight;} public void setWeight(Double v){weight=v;}
    public Double getHeight(){return height;} public void setHeight(Double v){height=v;}
    public Integer getOxygenSaturation(){return oxygenSaturation;} public void setOxygenSaturation(Integer v){oxygenSaturation=v;}
    public String getClinicalNotes(){return clinicalNotes;} public void setClinicalNotes(String v){clinicalNotes=v;}
    public String getDiagnosis(){return diagnosis;} public void setDiagnosis(String v){diagnosis=v;}
    public String getDoctorName(){return doctorName;} public void setDoctorName(String v){doctorName=v;}
}
