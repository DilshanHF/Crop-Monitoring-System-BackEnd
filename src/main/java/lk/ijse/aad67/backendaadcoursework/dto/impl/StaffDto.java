package lk.ijse.aad67.backendaadcoursework.dto.impl;


import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.Gender;
import lk.ijse.aad67.backendaadcoursework.entity.JobDesignation;
import lk.ijse.aad67.backendaadcoursework.entity.JobRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDto implements Status {
    private String staffId;
    private String firstName;
    private String lastName;
    private JobDesignation staffDesignation;
    private Gender gender;
    private String joinedDate;
    private String DOB;
    private String AddressLine01;
    private String AddressLine02;
    private String AddressLine03;
    private String AddressLine04;
    private String AddressLine05;
    private String contact;
    private String email;
    private JobRole jobRole;
    private List<String> fields;
    private List<VehicleDto> vehicles;
    private String image;
    private String logCode;
}
