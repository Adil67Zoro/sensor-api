package com.example.devadil.auth.services;

import com.example.devadil.auth.entities.Sensor;
import com.example.devadil.auth.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomSensorDetailService implements UserDetailsService {

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Sensor sensor = sensorRepository.findByName(username);
        if (sensor == null) {
            throw new UsernameNotFoundException("Sensor with name " + username + " not found");
        }
        return sensor;
    }
}
