package com.mediflow.web;
import com.mediflow.model.*;
import com.mediflow.repository.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
@RestController @RequestMapping("/api/visits") @CrossOrigin
public class VisitController {
    private final VisitRepository visits; private final PatientRepository patients;
    public VisitController(VisitRepository v,PatientRepository p){visits=v;patients=p;}
    @GetMapping public List<Visit> list(@RequestParam(required=false) Long patientId){return patientId==null?visits.findAllByOrderByVisitDateDesc():visits.findByPatientIdOrderByVisitDateDesc(patientId);}
    @GetMapping("/{id}") public Visit one(@PathVariable Long id){return visits.findById(id).orElseThrow(()->new NoSuchElementException("Visit not found"));}
    @PostMapping public Visit create(@RequestParam Long patientId,@Valid @RequestBody Visit visit){visit.setPatient(patients.findById(patientId).orElseThrow(()->new NoSuchElementException("Patient not found")));visit.setStatus(Visit.Status.WAITING_FOR_VITALS);return visits.save(visit);}
    @PutMapping("/{id}/vitals") public Visit vitals(@PathVariable Long id,@RequestBody Visit input){Visit v=one(id);if(v.getStatus()!=Visit.Status.WAITING_FOR_VITALS)throw new IllegalStateException("Vitals have already been recorded for this visit");if(input.getTemperature()==null||input.getTemperature()<30||input.getTemperature()>45)throw new IllegalArgumentException("Temperature must be between 30 and 45 C");if(input.getSystolicPressure()==null||input.getDiastolicPressure()==null||input.getPulse()==null||input.getOxygenSaturation()==null)throw new IllegalArgumentException("Blood pressure, pulse and oxygen saturation are required");if(input.getOxygenSaturation()<50||input.getOxygenSaturation()>100)throw new IllegalArgumentException("Oxygen saturation must be between 50 and 100");v.setTemperature(input.getTemperature());v.setSystolicPressure(input.getSystolicPressure());v.setDiastolicPressure(input.getDiastolicPressure());v.setPulse(input.getPulse());v.setWeight(input.getWeight());v.setHeight(input.getHeight());v.setOxygenSaturation(input.getOxygenSaturation());v.setStatus(Visit.Status.WAITING_FOR_DOCTOR);return visits.save(v);}
    @PutMapping("/{id}/consultation") public Visit consultation(@PathVariable Long id,@RequestBody Visit input){Visit v=one(id);if(v.getStatus()!=Visit.Status.WAITING_FOR_DOCTOR)throw new IllegalStateException("This visit is not ready for consultation");if(input.getDoctorName()==null||input.getDoctorName().trim().isEmpty()||input.getDiagnosis()==null||input.getDiagnosis().trim().isEmpty())throw new IllegalArgumentException("Doctor name and diagnosis are required");v.setClinicalNotes(input.getClinicalNotes());v.setDiagnosis(input.getDiagnosis());v.setDoctorName(input.getDoctorName());v.setStatus(Visit.Status.SENT_TO_PHARMACY);return visits.save(v);}
}
