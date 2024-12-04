package lk.ijse.aad67.backendaadcoursework.controllers;

import lk.ijse.aad67.backendaadcoursework.dto.impl.CropDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.CropService;
import lk.ijse.aad67.backendaadcoursework.utill.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/crop")
public class CropController {
    @Autowired
    private CropService cropService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Void> saveCrop(
            @RequestPart("cropCode") String cropCode,
            @RequestPart ("cropCommonName") String cropCommonName,
            @RequestPart ("cropScientificName") String cropScientificName,
            @RequestPart ("cropCategory") String cropCategory,
            @RequestPart ("cropImage") MultipartFile cropImage,
            @RequestPart ("cropSeason") String cropSeason,
            @RequestPart ("fieldCode") String fieldCode,
            @RequestPart ("logCode") String logCode
    ){

        try {
            cropService.saveCrop(assignValue(cropCode,cropCommonName,cropScientificName,cropCategory,cropImage,cropSeason,fieldCode,logCode));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{cropCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<Void> updateCrop(
            @PathVariable("cropCode") String cropCode,
            @RequestPart ("cropCommonName") String cropCommonName,
            @RequestPart ("cropScientificName") String cropScientificName,
            @RequestPart ("cropCategory") String cropCategory,
            @RequestPart ("cropImage") MultipartFile cropImage,
            @RequestPart ("cropSeason") String cropSeason,
            @RequestPart ("fieldCode") String fieldCode,
            @RequestPart ("logCode") String logCode
    ){
        try {
            cropService.updateCrop(cropCode,assignValue(cropCode,cropCommonName,cropScientificName,cropCategory,cropImage,cropSeason,fieldCode,logCode));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)

    public List<CropDto> getCropList(){
        return cropService.getCropList();
    }

    @GetMapping(value = "/{cropCode}")

    public Status getCropById(@PathVariable("cropCode") String cropCode){
        return cropService.getCropById(cropCode);
    }

    @DeleteMapping(value = "/{cropCode}")

    public ResponseEntity<Object> deleteCrop(@PathVariable("cropCode") String cropCode){
        try {
            cropService.deleteCrop(cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CropDto assignValue(String cropCode, String cropCommonName, String cropScientificName, String cropCategory, MultipartFile cropImage, String cropSeason, String fieldCode, String logCode) throws IOException {
        CropDto cropDto = new CropDto();
        cropDto.setCropCode(cropCode);
        cropDto.setCropCommonName(cropCommonName);
        cropDto.setCropScientificName(cropScientificName);
        cropDto.setCropImage(AppUtil.convertImage(cropImage));
        cropDto.setCropCategory(cropCategory);
        cropDto.setCropSeason(cropSeason);
        cropDto.setFieldCode(fieldCode);
        cropDto.setLogCode(logCode);


        return cropDto;
    }

    @GetMapping("/genCropID")
    public String generateCustomerId(){
        return cropService.generateCropID();
    }

}
