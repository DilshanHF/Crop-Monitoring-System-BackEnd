package lk.ijse.aad67.backendaadcoursework.controllers;

import lk.ijse.aad67.backendaadcoursework.dto.impl.VehicleDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDto vehicleDto){
        System.out.println(vehicleDto);
        try {
            vehicleService.saveVehicle(vehicleDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Status getSelectedVehicle(@PathVariable ("vehicleId") String vehicleById){
        return vehicleService.getVehicleById(vehicleById);
    }

    @PutMapping(value = "/{vehicleId}")
    public ResponseEntity<Void> updateVehicle(@PathVariable ("vehicleId") String vehicleId, @RequestBody VehicleDto vehicleDto){
        System.out.println(vehicleDto);
        try {
            vehicleService.updateVehicle(vehicleId, vehicleDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDto> getAllVehicle(){
        return vehicleService.getVehicleList();
    }

    @DeleteMapping(value = "/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable ("vehicleId") String vehicleId){
        try {
            vehicleService.deleteVehicle(vehicleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genVehicleID")
    public String generateVehicleId(){
        return vehicleService.generateVehicleID();
    }


}
