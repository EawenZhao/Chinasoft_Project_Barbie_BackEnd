package com.group21.chinasoft_project_barbie_backend.service;

public interface HardwareInfoService {
    void insertTemperature(double temperature);
    void insertHeartAndOxygen(double heartRate,double bloodOxygen);
}
