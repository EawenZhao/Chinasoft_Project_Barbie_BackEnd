package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.mapper.HardwareInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HardwareInfoServiceImpl {
    @Autowired
    HardwareInfoMapper hardwareInfoMapper;

    void insertTemperature(int residentId,double temperature){
        hardwareInfoMapper.insertTemperature(residentId,temperature);
    }


    void insertHeartAndOxygen(int residentId,double bloodOxygen,double heartRate){
        hardwareInfoMapper.insertHeartAndOxygen(residentId,heartRate,bloodOxygen);
    }
}
