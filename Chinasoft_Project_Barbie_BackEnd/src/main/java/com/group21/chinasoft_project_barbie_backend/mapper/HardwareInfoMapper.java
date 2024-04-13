package com.group21.chinasoft_project_barbie_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HardwareInfoMapper {

    @Insert("insert into temperature_seconds(resident_id,body_temperature,time) values (#{residentId},#{temperature},CURRENT_TIME)")
    void insertTemperature(int residentId,double temperature);

    @Insert("insert into health_data_seconds(resident_id,heart_rate,oxygen_level,time) values (#{residentId},#{heartRate},#{bloodOxygen},CURRENT_TIME)")
    void insertHeartAndOxygen(int residentId,double heartRate , double bloodOxygen);
}
