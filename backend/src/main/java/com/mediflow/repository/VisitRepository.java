package com.mediflow.repository;
import com.mediflow.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VisitRepository extends JpaRepository<Visit,Long> {
    List<Visit> findAllByOrderByVisitDateDesc();
    List<Visit> findByPatientIdOrderByVisitDateDesc(Long patientId);
}
