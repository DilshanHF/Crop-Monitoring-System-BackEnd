package lk.ijse.aad67.backendaadcoursework.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
public class AHealthChecker {
    @GetMapping
    public String healthTest(){
        return "OK";
    }
}
