package com.example.devadil.auth.services;


import com.example.devadil.auth.dto.RegistrationRequest;
import com.example.devadil.auth.dto.RegistrationResponse;
import com.example.devadil.auth.entities.Sensor;
import com.example.devadil.auth.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class RegistrationService {

    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistrationResponse createSensor(RegistrationRequest request) {

        Sensor existingSensor = sensorRepository.findByName(request.getName());
        if(existingSensor != null) {
            return  RegistrationResponse.builder()
                    .code(400)
                    .message("Sensor with such name already exists!")
                    .build();
        }

        try{
            Sensor sensor = new Sensor();
            sensor.setName(request.getName());
            sensor.setPassword(passwordEncoder.encode(request.getPassword()));
            sensorRepository.save(sensor);
            return RegistrationResponse.builder()
                    .code(200)
                    .message("Sensor Registered!")
                    .build();


        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage(),e.getCause());
        }
    }
}
