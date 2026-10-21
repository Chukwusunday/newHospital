package com.mediflow.repository;
import com.mediflow.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findTop30ByHospitalNumberContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrderByCreatedAtDesc(String a,String b,String c,String d);
}
