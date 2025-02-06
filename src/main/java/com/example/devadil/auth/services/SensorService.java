package com.example.devadil.auth.services;

import com.example.devadil.auth.dto.SensorDto;
import com.example.devadil.auth.entities.Sensor;
import com.example.devadil.auth.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SensorService {

    @Autowired
    SensorRepository sensorRepository;

    public Sensor getSensorByName(String sensorName) {
        return sensorRepository.findByName(sensorName);
    }

    public SensorDto mapSensorEntityToDto(Sensor sensor) {
        SensorDto sensorDto = new SensorDto();
        sensorDto.setName(sensor.getName());
        return sensorDto;
    }

}
