package com.example.devadil.controllers;

import com.example.devadil.auth.dto.SensorDto;
import com.example.devadil.auth.entities.Sensor;
import com.example.devadil.dto.MeasurementDto;
import com.example.devadil.entities.Measurement;
import com.example.devadil.services.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MeasurementControllerTest {

    @InjectMocks
    private MeasurementController measurementController;

    @Mock
    private MeasurementService measurementService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testAddMeasurement() {
        when(authentication.getName()).thenReturn("sensorTest1");

        SensorDto sensorDto = new SensorDto("sensorTest1");

        MeasurementDto measurementDto = MeasurementDto.builder()
                .value(21.5f)
                .raining(true)
                .sensor(sensorDto)
                .build();

        Measurement measurement = Measurement.builder()
                .value(21.5f)
                .raining(true)
                .build();

        when(measurementService.addMeasurement(any())).thenReturn(measurement);

        ResponseEntity<Measurement> response = measurementController.addMeasurements(measurementDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(21.5f, response.getBody().getValue());
        assertTrue(response.getBody().isRaining());
    }

    @Test
    void testGetAllMeasurements() {
        when(authentication.getName()).thenReturn("sensorTest1");

        SensorDto sensorDto = new SensorDto("sensorTest1");

        List<MeasurementDto> measurements = Arrays.asList(
                MeasurementDto.builder().value(21.0f).raining(true).sensor(sensorDto).build(),
                MeasurementDto.builder().value(16.5f).raining(false).sensor(sensorDto).build(),
                MeasurementDto.builder().value(18.5f).raining(false).sensor(sensorDto).build()
        );

        when(measurementService.getAllMeasurementsBySensorName()).thenReturn(measurements);

        ResponseEntity<List<MeasurementDto>> response = measurementController.getAllMeasurements();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        assertEquals(21.0f, response.getBody().get(0).getValue());
        assertEquals("sensorTest1", response.getBody().get(0).getSensor().getName());
    }

    @Test
    void testGetRainyDaysCount() {
        when(authentication.getName()).thenReturn("sensorTest1");

        when(measurementService.getRainyDaysCount()).thenReturn(7);

        ResponseEntity<Integer> response = measurementController.getRainyDaysCount();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(7, response.getBody());
    }
}
