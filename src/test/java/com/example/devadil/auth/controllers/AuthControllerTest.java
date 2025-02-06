package com.example.devadil.auth.controllers;

import com.example.devadil.auth.config.JWTTokenHelper;
import com.example.devadil.auth.dto.LoginRequest;
import com.example.devadil.auth.dto.RegistrationRequest;
import com.example.devadil.auth.dto.RegistrationResponse;
import com.example.devadil.auth.dto.UserToken;
import com.example.devadil.auth.entities.Sensor;
import com.example.devadil.auth.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTTokenHelper jwtTokenHelper;

    @Mock
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest("sensorTest", "password");

        // ✅ Step 1: Create a mock Sensor object (replace with your actual Sensor class)
        Sensor mockSensor = new Sensor();
        mockSensor.setName("sensorTest");

        // ✅ Step 2: Create an Authentication object with the Sensor as the principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(mockSensor, "password", List.of());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenHelper.generateToken(anyString())).thenReturn("mockedToken");

        ResponseEntity<UserToken> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mockedToken", response.getBody().getToken());
    }

    @Test
    void testRegisterSensorSuccess() {
        RegistrationRequest request = new RegistrationRequest("sensorTest", "password");
        RegistrationResponse registrationResponse = new RegistrationResponse(200, "Sensor successfully registered!");

        when(registrationService.createSensor(any())).thenReturn(registrationResponse);

        ResponseEntity<RegistrationResponse> response = authController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sensor successfully registered!", response.getBody().getMessage());
    }
}
