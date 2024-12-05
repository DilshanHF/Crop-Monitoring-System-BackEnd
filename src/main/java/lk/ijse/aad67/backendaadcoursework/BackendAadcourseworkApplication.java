package lk.ijse.aad67.backendaadcoursework;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendAadcourseworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendAadcourseworkApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
