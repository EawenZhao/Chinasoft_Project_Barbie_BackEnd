package com.group21.chinasoft_project_barbie_backend.mapper;

import com.group21.chinasoft_project_barbie_backend.entity.ExceptionInfo;
import com.group21.chinasoft_project_barbie_backend.entity.HealthInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResidentMapper {
    @Select("SELECT first_name,last_name,family_members_mobile.username,residents.date_of_birth FROM residents JOIN family_members_mobile ON residents.resident_id = family_members_mobile.resident_id where residents.resident_id=#{residentId}")
    ResidentInfo findResidentInfoById(int ResidentId);

    @Insert("INSERT into resident_exception_history(resident_id,exception_start_time,exception_info,exception_end_time) values(#{residentId},#{exceptionStartTime},#{exceptionInfo},#{exceptionEndTime})")
    void insertException(int residentId, String exceptionStartTime, String exceptionInfo, String exceptionEndTime);

    @Select("SELECT\n" +
            "    reh.exception_start_time,\n" +
            "    reh.exception_info,\n" +
            "    reh.exception_end_time,\n" +
            "    r.phone\n" +
            "FROM\n" +
            "    residents r\n" +
            "JOIN\n" +
            "    resident_exception_history reh ON r.resident_id = reh.resident_id\n" +
            "WHERE\n" +
            "    r.resident_id = #{residentId};")
    List<ExceptionInfo> findAllExceptions(int residentId);

    @Select("SELECT\n" +
            "    ho.heart_rate,\n" +
            "    ho.oxygen_level,\n" +
            "    t.body_temperature\n" +
            "FROM\n" +
            "    (SELECT heart_rate, oxygen_level\n" +
            "     FROM health_data_seconds\n" +
            "     WHERE id = (SELECT MAX(id) FROM health_data_seconds WHERE resident_id = #{residentId})) AS ho,\n" +
            "    (SELECT body_temperature\n" +
            "     FROM temperature_seconds\n" +
            "     WHERE id = (SELECT MAX(id) FROM temperature_seconds WHERE resident_id = #{residentId})) AS t;")
    HealthInfo getNewestInfoById(int residentId);

    @Update("UPDATE resident_exception_history AS reh\n" +
            "JOIN (\n" +
            "    SELECT MAX(id) AS maxId\n" +
            "    FROM resident_exception_history\n" +
            "    WHERE resident_id = #{residentId}\n" +
            ") AS maxReh ON reh.id = maxReh.maxId\n" +
            "SET reh.exception_end_time = NOW()\n" +
            "WHERE reh.resident_id = #{residentId};")
    void updateExceptionEndtimeById(int residentId);

    @Select("select resident_id from family_members_mobile where member_id = #{memberId}")
    int getResidentIdByMemberId(int memberId);

    @Select("select resident_id from device_resident_map where device_id = #{deviceId}")
    int getResidentIdByDeviceId(int deviceId);
}
