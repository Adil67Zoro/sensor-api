package com.example.devadil.controllers;

import com.example.devadil.auth.entities.Sensor;
import com.example.devadil.dto.MeasurementDto;
import com.example.devadil.entities.Measurement;
import com.example.devadil.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/measurements")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping("/add")
    public ResponseEntity<Measurement> addMeasurements(@RequestBody MeasurementDto measurementDto) {
        Measurement measurement = measurementService.addMeasurement(measurementDto);
        return new ResponseEntity<>(measurement, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<MeasurementDto>> getAllMeasurements(){
        List<MeasurementDto> measurementDtos = measurementService.getAllMeasurementsBySensorName();
        return new ResponseEntity<>(measurementDtos, HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> getRainyDaysCount(){
        Integer rainyDaysCount = measurementService.getRainyDaysCount();
        return new ResponseEntity<>(rainyDaysCount, HttpStatus.OK);
    }

}
