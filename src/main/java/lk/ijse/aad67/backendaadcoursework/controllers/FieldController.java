package lk.ijse.aad67.backendaadcoursework.controllers;

import lk.ijse.aad67.backendaadcoursework.dto.impl.FieldDto;
import lk.ijse.aad67.backendaadcoursework.service.FieldService;
import lk.ijse.aad67.backendaadcoursework.utill.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart ("fieldName") String fieldName,
            @RequestPart ("fieldLocation") String fieldLocation,
            @RequestPart ("fieldSize") String fieldSize,
            @RequestPart ("image1") MultipartFile image1,
            @RequestPart ("image2") MultipartFile image2,
            @RequestPart ("logCode") String logCode,
            @RequestPart("staffIds") String staffIdsString
    ){

        List<String> staffIds = Arrays.asList(staffIdsString.split(","));
        if ("NoAssign".equals(staffIds.get(0))) {
            staffIds = new ArrayList<>();
        }
        try {

            fieldService.saveField(assignValue(fieldCode,fieldName,fieldLocation,fieldSize,image1,image2,logCode,staffIds));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



    private FieldDto assignValue(String fieldCode, String fieldName, String fieldLocation, String fieldSize, MultipartFile image1, MultipartFile image2, String logCode, List<String> staffIds) throws IOException {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setFieldCode(fieldCode);
        fieldDto.setFieldName(fieldName);
        fieldDto.setFieldLocation(Integer.parseInt(fieldLocation));
        fieldDto.setFieldSize(Double.valueOf(fieldSize));
        fieldDto.setImage1(AppUtil.convertImage(image1));
        fieldDto.setImage2(AppUtil.convertImage(image2));
        fieldDto.setLogCode(logCode);

        fieldDto.setStaffField(staffIds);
        return fieldDto;
    }

}
