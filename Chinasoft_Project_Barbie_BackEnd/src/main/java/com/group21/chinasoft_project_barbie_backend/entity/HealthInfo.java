package com.group21.chinasoft_project_barbie_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthInfo {
    double heartRate;
    double oxygenLevel;
    double bodyTemperature;
}
