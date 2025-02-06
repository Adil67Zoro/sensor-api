package com.example.devadil;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Random;

public class SensorClient {
    private static final String BASE_URL = "http://localhost:8080";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Random random = new Random();

    public static void main(String[] args) {
        String sensorName = "ClientSensor";
        String password = "password";

        registerSensor(sensorName);

        String token = loginAndGetToken(sensorName, password);

        sendMeasurements(sensorName, 1000, token);

        getAllMeasurements(token);
    }


    private static String loginAndGetToken(String sensorName, String password) {
        String url = BASE_URL + "/sensors/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"name\": \"%s\", \"password\": \"%s\"}", sensorName, password);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String token = response.getBody().replace("\"", "");
            System.out.println("✅ JWT Token received: " + token);
            return token;
        } else {
            throw new RuntimeException("Error in logging in: " + response.getStatusCode());
        }
    }


    private static void registerSensor(String sensorName) {
        String url = BASE_URL + "/sensors/registration";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format(
                "{\"name\": \"%s\", \"password\": \"password\"}", sensorName
        );

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Sensor '" + sensorName + "' registered successfully!");
        } else {
            System.out.println("Error in registering sensor: " + response.getStatusCode());
        }
    }


    private static void sendMeasurements(String sensorName, int count, String token) {
        String url = BASE_URL + "/measurements/add";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        for (int i = 1; i <= count; i++) {
            double temperature = -10 + (random.nextDouble() * 50);
            boolean raining = random.nextBoolean();

            String requestBody = String.format(
                    "{\"value\": %.2f, \"raining\": %b, \"sensor\": {\"name\": \"%s\"}}",
                    temperature, raining, sensorName
            );

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("[" + i + "] Measurement sent: Temperature = " + temperature + "°C, Rain = " + raining);
            } else {
                System.out.println("Error in sending measurement [" + i + "]: " + response.getStatusCode());
            }
        }
    }


    private static void getAllMeasurements(String token) {
        String url = BASE_URL + "/measurements";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("All measurements received:");
            System.out.println(response.getBody());
        } else {
            System.out.println("Error in receiving measurements: " + response.getStatusCode());
        }
    }

}

