package com.example.devadil.entities;

import com.example.devadil.auth.entities.Sensor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.util.UUID;

@Entity
@Table(name = "measurement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Measurement {

    @Id
    @GeneratedValue
    private UUID id;

    private float value;
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name", nullable = false)
    @JsonIgnore
    private Sensor sensor;
}
