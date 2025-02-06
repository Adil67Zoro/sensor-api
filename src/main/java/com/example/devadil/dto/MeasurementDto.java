package com.example.devadil.dto;

import com.example.devadil.auth.dto.SensorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementDto {
    private float value;
    private boolean raining;
    private SensorDto sensor;
}
