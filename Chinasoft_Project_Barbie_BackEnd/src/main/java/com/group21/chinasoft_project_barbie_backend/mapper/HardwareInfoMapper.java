package com.group21.chinasoft_project_barbie_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HardwareInfoMapper {

    @Insert("insert into temperature_seconds(resident_id,body_temperature) values (2,#{temperature})")
    void insertTemperature(double temperature);

    @Insert("insert into health_data_seconds(resident_id,heart_rate,oxygen_level,time) values (2,#{heartRate},#{bloodOxygen},CURRENT_TIME)")
    void insertHeartAndOxygen(double heartRate , double bloodOxygen);
}
