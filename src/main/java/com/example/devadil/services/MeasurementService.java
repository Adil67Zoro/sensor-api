package com.example.devadil.services;

import com.example.devadil.auth.entities.Sensor;
import com.example.devadil.auth.repositories.SensorRepository;
import com.example.devadil.auth.services.SensorService;
import com.example.devadil.dto.MeasurementDto;
import com.example.devadil.entities.Measurement;
import com.example.devadil.repositories.MeasurementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    SensorService sensorService;

    @Autowired
    SensorRepository sensorRepository;


    public Measurement addMeasurement(MeasurementDto measurementDto) {

        String loggedInSensorName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!loggedInSensorName.equals(measurementDto.getSensor().getName())) {
            throw new AccessDeniedException("You cannot add a measurement to this sensor.");
        }

        Sensor sensor = sensorRepository.findByName(measurementDto.getSensor().getName());
        if (sensor == null) {
            throw new EntityNotFoundException("Sensor with name " + measurementDto.getSensor().getName() + " is not found");
        }

        Measurement measurement = mapMeasurementDtoToEntity(measurementDto);
        measurement.setSensor(sensor);

        if (measurementDto.isRaining()) {
            sensor.setRainyDays(sensor.getRainyDays() + 1);
            sensorRepository.save(sensor);
        }

        return measurementRepository.save(measurement);
    }

    private Measurement mapMeasurementDtoToEntity(MeasurementDto measurementDto) {
        Measurement measurement = new Measurement();
        measurement.setValue(measurementDto.getValue());
        measurement.setRaining(measurementDto.isRaining());

        Sensor sensor = sensorService.getSensorByName(measurementDto.getSensor().getName());
        measurement.setSensor(sensor);

        return measurement;
    }

    public List<MeasurementDto> getAllMeasurementsBySensorName() {
        String loggedInSensorName = SecurityContextHolder.getContext().getAuthentication().getName();

        Sensor sensor = sensorService.getSensorByName(loggedInSensorName);
        if (sensor == null) {
            throw new EntityNotFoundException("Sensor with name " + loggedInSensorName + " is not found");
        }

        List<Measurement> measurements = measurementRepository.findMeasurementsBySensorName(sensor.getName());

        return measurements.stream()
                .map(measurement -> MeasurementDto.builder()
                        .value(measurement.getValue())
                        .raining(measurement.isRaining())
                        .sensor(sensorService.mapSensorEntityToDto(measurement.getSensor()))
                        .build())
                .collect(Collectors.toList());
    }

    public Integer getRainyDaysCount() {
        String loggedInSensorName = SecurityContextHolder.getContext().getAuthentication().getName();

        Sensor sensor = sensorService.getSensorByName(loggedInSensorName);
        if (sensor == null) {
            throw new EntityNotFoundException("Sensor with name " + loggedInSensorName + " is not found");
        }
        return sensor.getRainyDays();
    }
}
