package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.mapper.HardwareInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HardwareInfoServiceImpl {
    @Autowired
    HardwareInfoMapper hardwareInfoMapper;

    void insertTemperature(double temperature){
        hardwareInfoMapper.insertTemperature(temperature);
    }


    void insertHeartAndOxygen(double bloodOxygen,double heartRate){
        hardwareInfoMapper.insertHeartAndOxygen(heartRate,bloodOxygen);
    }
}
