package com.mediflow.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Entity
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique = true, nullable = false) private String hospitalNumber;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotNull @Past private LocalDate dateOfBirth;
    @NotBlank private String gender;
    @NotBlank @Pattern(regexp="^[+0-9() -]{7,20}$") @Column(unique = true) private String phone;
    @Email private String email;
    private String address;
    private String emergencyContact;
    private String bloodGroup;
    private String allergies;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getHospitalNumber(){return hospitalNumber;} public void setHospitalNumber(String v){hospitalNumber=v;}
    public String getFirstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String getLastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public LocalDate getDateOfBirth(){return dateOfBirth;} public void setDateOfBirth(LocalDate v){dateOfBirth=v;}
    public String getGender(){return gender;} public void setGender(String v){gender=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getAddress(){return address;} public void setAddress(String v){address=v;}
    public String getEmergencyContact(){return emergencyContact;} public void setEmergencyContact(String v){emergencyContact=v;}
    public String getBloodGroup(){return bloodGroup;} public void setBloodGroup(String v){bloodGroup=v;}
    public String getAllergies(){return allergies;} public void setAllergies(String v){allergies=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
