package lk.ijse.aad67.backendaadcoursework.controllers;


import lk.ijse.aad67.backendaadcoursework.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(
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
        try {
            cropService.saveCrop(assignValue(cropCode,cropCommonName,cropScientificName,cropCategory,cropImage,cropSeason,fieldCode,logCode));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
