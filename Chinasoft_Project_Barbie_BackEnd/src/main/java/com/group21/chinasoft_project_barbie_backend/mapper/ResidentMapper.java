package com.group21.chinasoft_project_barbie_backend.mapper;

import com.group21.chinasoft_project_barbie_backend.entity.ExceptionInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResidentMapper {
    @Select("SELECT first_name,last_name,family_members_mobile.username,residents.date_of_birth FROM residents JOIN family_members_mobile ON residents.resident_id = family_members_mobile.resident_id where residents.resident_id=#{residentId}")
    ResidentInfo findResidentInfoById(int ResidentId);

    @Insert("INSERT into resident_exception_history(resident_id,exception_start_time,exception_info,exception_end_time) values(#{residentId},#{exceptionStartTime},#{exceptionInfo},#{exceptionEndTime})")
    void insertException(int residentId, String exceptionStartTime, String exceptionInfo, String exceptionEndTime);

    @Select("SELECT exception_start_time,exception_info,exception_end_time FROM resident_exception_history where resident_id=#{residentId}")
    List<ExceptionInfo> findAllExceptions(int residentId);
}
