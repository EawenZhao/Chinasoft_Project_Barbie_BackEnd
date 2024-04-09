package com.group21.chinasoft_project_barbie_backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class HardwareDataDTO {
    private String id;
    private Map<String, String> data;
}
