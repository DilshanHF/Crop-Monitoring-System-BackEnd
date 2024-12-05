package lk.ijse.aad67.backendaadcoursework.controllers;


import lk.ijse.aad67.backendaadcoursework.dto.impl.StaffDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.Gender;
import lk.ijse.aad67.backendaadcoursework.entity.JobDesignation;
import lk.ijse.aad67.backendaadcoursework.entity.JobRole;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.StaffService;
import lk.ijse.aad67.backendaadcoursework.utill.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveStaff(
            @RequestPart("staffId") String staffId,
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("staffDesignation") String staffDesignation,
            @RequestPart("gender") String gender,
            @RequestPart("joinedDate") String joinedDate,
            @RequestPart("DOB") String DOB,
            @RequestPart("AddressLine01") String AddressLine01,
            @RequestPart("AddressLine02") String AddressLine02,
            @RequestPart("AddressLine03") String AddressLine03,
            @RequestPart("AddressLine04") String AddressLine04,
            @RequestPart("AddressLine05") String AddressLine05,
            @RequestPart("contact") String contact,
            @RequestPart("email") String email,
            @RequestPart("jobRole") String jobRole,
            @RequestPart("image") MultipartFile image,
            @RequestPart("logCode") String logCode,
            @RequestPart("fieldIds") String staffIdsString
    ){
        List<String> staffIds = Arrays.asList(staffIdsString.split(","));
        if ("NoAssign".equals(staffIds.get(0))) {
            staffIds = new ArrayList<>();
        }
        StaffDto staffDto = null;

        try {
            staffDto = assignValue(
                    staffId, firstName, lastName, staffDesignation, gender, joinedDate, DOB,
                    AddressLine01, AddressLine02, AddressLine03, AddressLine04, AddressLine05,
                    contact, email, jobRole, image, logCode, staffIds
            );

            staffService.saveStaff(staffDto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Staff saved successfully." + "fields Ids:" + staffDto.getFields());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (DataPersistException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to save staff data.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred." + staffDto.getFields());
        }


    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDto> getStaffList(){
        return staffService.getStaffList();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    @PutMapping(value = "/{staffCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Status> updateStaff(
            @PathVariable("staffCode") String staffId,
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("staffDesignation") String staffDesignation,
            @RequestPart("gender") String gender,
            @RequestPart("joinedDate") String joinedDate,
            @RequestPart("DOB") String DOB,
            @RequestPart("AddressLine01") String AddressLine01,
            @RequestPart("AddressLine02") String AddressLine02,
            @RequestPart("AddressLine03") String AddressLine03,
            @RequestPart("AddressLine04") String AddressLine04,
            @RequestPart("AddressLine05") String AddressLine05,
            @RequestPart("contact") String contact,
            @RequestPart("email") String email,
            @RequestPart("jobRole") String jobRole,
            @RequestPart("image") MultipartFile image,
            @RequestPart("logCode") String logCode,
            @RequestPart("fieldIds") String staffIdsString

    ){
        List<String> staffIds = Arrays.asList(staffIdsString.split(","));
        if ("NoAssign".equals(staffIds.get(0))) {
            staffIds = new ArrayList<>();
        }
        try {
            staffService.updateStaff(staffId,assignValue(staffId, firstName, lastName, staffDesignation, gender, joinedDate,
                    DOB, AddressLine01, AddressLine02, AddressLine03, AddressLine04, AddressLine05, contact, email, jobRole, image, logCode, staffIds
            ));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{staffCode}")
    public Status getStaffById(@PathVariable("staffCode") String staffCode){
        return staffService.getStaffById(staffCode);
    }

    @DeleteMapping(value = "/{staffCode}")
    public ResponseEntity<Object> deleteStaff(@PathVariable("staffCode") String staffCode){
        try {
            staffService.deleteStaff(staffCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genStaffID")
    public String generateStaffId(){
        return staffService.generateStaffID();
    }


    private StaffDto assignValue(String staffId, String firstName, String lastName,
                                 String staffDesignation, String gender, String joinedDate,
                                 String dob, String addressLine01, String addressLine02,
                                 String addressLine03, String addressLine04, String addressLine05,
                                 String contact, String email, String jobRole,
                                 MultipartFile image, String logCode,List<String> fieldIds) throws IOException {

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(staffId);
        staffDto.setFirstName(firstName);
        staffDto.setLastName(lastName);


        staffDto.setStaffDesignation(JobDesignation.valueOf(staffDesignation));
        staffDto.setGender(Gender.valueOf(gender.toUpperCase()));
        staffDto.setJobRole(JobRole.valueOf(jobRole.toUpperCase()));


        staffDto.setJoinedDate(joinedDate);
        staffDto.setDOB(dob);

        staffDto.setAddressLine01(addressLine01);
        staffDto.setAddressLine02(addressLine02);
        staffDto.setAddressLine03(addressLine03);
        staffDto.setAddressLine04(addressLine04);
        staffDto.setAddressLine05(addressLine05);


        staffDto.setContact(contact);
        staffDto.setEmail(email);


        if (image != null && !image.isEmpty()) {
            staffDto.setImage(AppUtil.convertImage(image));
        }

        staffDto.setFields(fieldIds);
        staffDto.setLogCode(logCode);


        return staffDto;
    }
}
