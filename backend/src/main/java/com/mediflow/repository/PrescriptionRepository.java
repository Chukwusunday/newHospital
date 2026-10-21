package com.mediflow.repository;
import com.mediflow.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    List<Prescription> findByVisitId(Long visitId);
    List<Prescription> findByDispensedFalseOrderByIdDesc();
}
