package com.example.devadil.auth.repositories;

import com.example.devadil.auth.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, UUID> {
    Sensor findByName(String name);
}
