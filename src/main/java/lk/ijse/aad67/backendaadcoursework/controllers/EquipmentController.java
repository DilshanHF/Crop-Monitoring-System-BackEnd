package lk.ijse.aad67.backendaadcoursework.controllers;

import lk.ijse.aad67.backendaadcoursework.dto.impl.EquipmentDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDto equipmentDto) {
        System.out.println(equipmentDto);
        System.out.println("print equ id:"+equipmentDto.getEquipmentId());
        System.out.println(equipmentDto.getEquipmentName());
        System.out.println(equipmentDto.getEquipmentId());
        try {
            equipmentService.saveEquipment(equipmentDto);
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
    public List<EquipmentDto> getAllEquipment() {
        return equipmentService.getEquipmentList();
    }

    @PutMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> updateEquipment(@PathVariable ("equipmentId") String equipmentId,
                                           @RequestBody EquipmentDto equipmentDto){

        try {

            equipmentService.updateEquipment(equipmentId, equipmentDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Status getSelectedEquipment(@PathVariable ("equipmentId") String equipmentId){
        return equipmentService.getEquipmentById(equipmentId);
    }

    @DeleteMapping(value = "/{equipmentCode}")
    public ResponseEntity<Object> deleteEquipment(@PathVariable("equipmentCode") String equipmentCode){
        try {
            equipmentService.deleteEquipment(equipmentCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genEquipmentID")
    public String generateIdEquipment(){
        return equipmentService.generateEquipmentID();
    }


//        @GetMapping()
//        public String healthTest(){
//            return "OK";
//        }


}
