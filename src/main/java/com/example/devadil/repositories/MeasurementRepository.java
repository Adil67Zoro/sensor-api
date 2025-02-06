package com.example.devadil.repositories;

import com.example.devadil.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {
    List<Measurement> findMeasurementsBySensorName(String sensorName);
}
