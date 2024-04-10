package com.group21.chinasoft_project_barbie_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class HardwareDataDTO {
    private String id;
    private HealthDTO data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class HealthDTO {
        private String spo2;
        private String heart;
        private String temperature;
    }
}
