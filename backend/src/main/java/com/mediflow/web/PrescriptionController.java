package com.mediflow.web;
import com.mediflow.model.*;
import com.mediflow.repository.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
@RestController @RequestMapping("/api/prescriptions") @CrossOrigin
public class PrescriptionController {
    private final PrescriptionRepository prescriptions; private final VisitRepository visits;
    public PrescriptionController(PrescriptionRepository p,VisitRepository v){prescriptions=p;visits=v;}
    @GetMapping public List<Prescription> list(@RequestParam(required=false) Long visitId,@RequestParam(defaultValue="false") boolean pending){return visitId!=null?prescriptions.findByVisitId(visitId):pending?prescriptions.findByDispensedFalseOrderByIdDesc():prescriptions.findAll();}
    @PostMapping public Prescription create(@RequestParam Long visitId,@Valid @RequestBody Prescription p){Visit visit=visits.findById(visitId).orElseThrow(()->new NoSuchElementException("Visit not found"));if(visit.getStatus()!=Visit.Status.SENT_TO_PHARMACY)throw new IllegalStateException("Complete the consultation before prescribing medicine");p.setVisit(visit);return prescriptions.save(p);}
    @Transactional @PutMapping("/{id}/dispense") public Prescription dispense(@PathVariable Long id){Prescription p=prescriptions.findById(id).orElseThrow(()->new NoSuchElementException("Prescription not found"));if(p.isDispensed())throw new IllegalStateException("This medicine has already been dispensed");p.setDispensed(true);p.setDispensedAt(LocalDateTime.now());Prescription saved=prescriptions.save(p);List<Prescription> items=prescriptions.findByVisitId(p.getVisit().getId());if(items.stream().allMatch(Prescription::isDispensed)){Visit v=p.getVisit();v.setStatus(Visit.Status.COMPLETED);visits.save(v);}return saved;}
}
