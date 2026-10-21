package com.mediflow.web;
import com.mediflow.model.Patient;
import com.mediflow.repository.PatientRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.Year;
import java.util.*;
@RestController @RequestMapping("/api/patients") @CrossOrigin
public class PatientController {
    private final PatientRepository patients;
    public PatientController(PatientRepository patients){this.patients=patients;}
    @GetMapping public List<Patient> list(@RequestParam(defaultValue="") String search){return patients.findTop30ByHospitalNumberContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrderByCreatedAtDesc(search,search,search,search);}
    @GetMapping("/{id}") public Patient one(@PathVariable Long id){return patients.findById(id).orElseThrow(()->new NoSuchElementException("Patient not found"));}
    @PostMapping public ResponseEntity<Patient> create(@Valid @RequestBody Patient patient){patient.setHospitalNumber("MHF-"+Year.now().getValue()+"-"+String.format("%05d",patients.count()+1));return ResponseEntity.status(201).body(patients.save(patient));}
    @PutMapping("/{id}") public Patient update(@PathVariable Long id,@Valid @RequestBody Patient input){Patient p=one(id); input.setId(id);input.setHospitalNumber(p.getHospitalNumber());input.setCreatedAt(p.getCreatedAt());return patients.save(input);}
}
